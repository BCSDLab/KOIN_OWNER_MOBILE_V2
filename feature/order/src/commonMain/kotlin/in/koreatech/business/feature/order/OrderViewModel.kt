package `in`.koreatech.business.feature.order

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.error.ApiException
import `in`.koreatech.business.domain.model.order.OrderServiceApplication
import `in`.koreatech.business.domain.model.order.OwnerOrderCategory
import `in`.koreatech.business.domain.model.store.OwnerShop
import `in`.koreatech.business.domain.model.upload.PreSignedUrlDomain
import `in`.koreatech.business.domain.usecase.order.GetOwnerOrderableShopsUseCase
import `in`.koreatech.business.domain.usecase.order.GetOwnerOrdersUseCase
import `in`.koreatech.business.domain.usecase.order.RequestOrderServiceUseCase
import `in`.koreatech.business.domain.usecase.presignedurl.UploadImageUseCase
import `in`.koreatech.business.domain.usecase.store.GetOwnerMenusUseCase
import `in`.koreatech.business.domain.usecase.store.ObserveSelectedShopUseCase
import `in`.koreatech.business.feature.order.model.toOrderServiceMenuUiModel
import `in`.koreatech.business.feature.order.model.toOrderUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class OrderViewModel(
    private val observeSelectedShopUseCase: ObserveSelectedShopUseCase,
    private val getOrderableShopsUseCase: GetOwnerOrderableShopsUseCase,
    private val getOrdersUseCase: GetOwnerOrdersUseCase,
    private val getOwnerMenusUseCase: GetOwnerMenusUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val requestOrderServiceUseCase: RequestOrderServiceUseCase
) : ViewModel(), OrbitContainerHost<OrderState, OrderState, Nothing> {
    private val submittedShopIds = mutableSetOf<Int>()
    private val submittingShopIds = mutableSetOf<Int>()
    private var shopGeneration = 0

    override val container = orbitContainer<OrderState, Nothing>(OrderState()) {
        observeShop()
    }

    private suspend fun observeShop() = subIntent {
        observeSelectedShopUseCase().collectLatest { loadShop(it) }
    }

    private suspend fun loadShop(result: Result<OwnerShop?>) = subIntent {
        val generation = if (result.isFailure || result.getOrNull()?.id != state.shopId) ++shopGeneration else shopGeneration
        result.onFailure {
            reduce { OrderState(isLoading = false, hasError = true) }
            return@subIntent
        }
        val shop = result.getOrNull()
        reduce {
            if (state.shopId == shop?.id) {
                state.copy(isLoading = true, hasError = false)
            } else {
                OrderState(shopId = shop?.id, shopName = shop?.name)
            }
        }
        val orderableShopId = getOrderableShopsUseCase().getOrElse {
            if (generation == shopGeneration) reduce { state.copy(isLoading = false, hasError = true) }
            return@subIntent
        }.firstOrNull { it.shopId == shop?.id }?.id
        if (generation != shopGeneration) return@subIntent
        reduce {
            state.copy(
                shopName = shop?.name,
                orderableShopId = orderableShopId,
                service = state.service.copy(
                    isSubmitted = shop?.id in submittedShopIds,
                    isSubmitting = shop?.id in submittingShopIds
                )
            )
        }
        loadOrders(orderableShopId, state.category)
    }

    fun selectCategory(category: OwnerOrderCategory) = intent {
        if (category == state.category) return@intent
        reduce { state.copy(category = category) }
        loadOrders(state.orderableShopId, category)
    }

    fun retry() = intent { loadShop(observeSelectedShopUseCase().first()) }

    fun startOrderService() = intent {
        if (state.shopId == null || state.orderableShopId != null || state.service.isSubmitted) return@intent
        loadServiceMenus()
    }

    fun retryServiceMenus() = intent { loadServiceMenus() }

    private suspend fun loadServiceMenus() = subIntent {
        val shopId = state.shopId ?: return@subIntent
        val generation = shopGeneration
        reduce { state.copy(service = state.service.copy(isLoadingMenus = true, hasMenuError = false)) }
        val result = getOwnerMenusUseCase(shopId).first()
        if (generation != shopGeneration) return@subIntent
        result.onSuccess { categories ->
            val menus = categories.asSequence()
                .flatMap { it.menus }
                .filterNot { it.isHidden }
                .distinctBy { it.id }
                .map { it.toOrderServiceMenuUiModel() }
                .toImmutableList()
            reduce {
                state.copy(
                    service = state.service.copy(
                        menus = menus,
                        selectedMenuIds = state.service.selectedMenuIds.filter { id -> menus.any { it.id == id } }.toImmutableSet(),
                        isLoadingMenus = false
                    )
                )
            }
        }.onFailure {
            reduce { state.copy(service = state.service.copy(isLoadingMenus = false, hasMenuError = true)) }
        }
    }

    fun updateServiceField(field: OrderServiceField, value: String) = intent {
        if (state.service.isSubmitting) return@intent
        val numeric = field == OrderServiceField.MINIMUM_ORDER_AMOUNT ||
            field == OrderServiceField.CAMPUS_DELIVERY_TIP || field == OrderServiceField.OFF_CAMPUS_DELIVERY_TIP
        if (numeric && (value.any { it !in '0'..'9' } || (value.isNotEmpty() && value.toIntOrNull() == null))) return@intent
        val service = state.service
        val updated = when (field) {
            OrderServiceField.MINIMUM_ORDER_AMOUNT -> service.copy(minimumOrderAmount = value)
            OrderServiceField.CAMPUS_DELIVERY_TIP -> service.copy(campusDeliveryTip = value)
            OrderServiceField.OFF_CAMPUS_DELIVERY_TIP -> service.copy(offCampusDeliveryTip = value)
            OrderServiceField.BANK -> service.copy(bank = value.take(10))
            OrderServiceField.ACCOUNT_NUMBER -> service.copy(accountNumber = value.filter(Char::isDigit).take(20))
            OrderServiceField.ACCOUNT_HOLDER -> service.copy(accountHolder = value.take(50))
        }
        reduce { state.copy(service = updated.copy(hasSubmitError = false, submitErrorMessage = null)) }
    }

    fun updateDeliveryOption(value: String) = intent {
        if (!state.service.isSubmitting && (value == "CAMPUS" || value == "OFF_CAMPUS" || value == "BOTH")) {
            reduce { state.copy(service = state.service.copy(deliveryOption = value)) }
        }
    }

    fun updateTakeout(value: Boolean) = intent {
        if (!state.service.isSubmitting) reduce { state.copy(service = state.service.copy(isTakeout = value)) }
    }

    fun toggleServiceMenu(menuId: Int) = intent {
        if (state.service.isSubmitting || state.service.menus.none { it.id == menuId }) return@intent
        val ids = state.service.selectedMenuIds
        reduce {
            state.copy(service = state.service.copy(selectedMenuIds = (if (menuId in ids) ids - menuId else ids + menuId).toImmutableSet()))
        }
    }

    fun fileSelectionFailed() = intent {
        reduce { state.copy(service = state.service.copy(hasUploadError = true)) }
    }

    fun uploadServiceDocument(document: OrderServiceDocument, name: String, contentType: String, bytes: ByteArray) = intent {
        val shopId = state.shopId ?: return@intent
        if (state.service.isSubmitting || document in state.service.uploadingDocuments) return@intent
        val generation = shopGeneration
        reduce {
            state.copy(service = state.service.copy(uploadingDocuments = (state.service.uploadingDocuments + document).toImmutableSet(), hasUploadError = false))
        }
        val result = uploadImageUseCase(PreSignedUrlDomain.OWNERS, bytes.size.toLong(), contentType, name, bytes)
        if (generation != shopGeneration || state.shopId != shopId) return@intent
        reduce {
            val service = state.service.copy(
                uploadingDocuments = (state.service.uploadingDocuments - document).toImmutableSet(),
                hasUploadError = result.isFailure
            )
            state.copy(
                service = result.fold(
                    onSuccess = { url ->
                        when (document) {
                            OrderServiceDocument.BUSINESS_LICENSE -> service.copy(businessLicenseUrl = url)
                            OrderServiceDocument.BUSINESS_CERTIFICATE -> service.copy(businessCertificateUrl = url)
                            OrderServiceDocument.BANK_COPY -> service.copy(bankCopyUrl = url)
                        }
                    },
                    onFailure = { service }
                )
            )
        }
    }

    fun submitOrderService() = intent {
        val shopId = state.shopId ?: return@intent
        val service = state.service
        if (!service.canSubmit || state.orderableShopId != null || shopId in submittingShopIds) return@intent
        submittingShopIds.add(shopId)
        reduce { state.copy(service = service.copy(isSubmitting = true, hasSubmitError = false, submitErrorMessage = null)) }
        val application = OrderServiceApplication(
            minimumOrderAmount = service.minimumOrderAmount.toInt(),
            isTakeout = service.isTakeout,
            deliveryOption = service.deliveryOption,
            campusDeliveryTip = service.campusDeliveryTip.toInt(),
            offCampusDeliveryTip = service.offCampusDeliveryTip.toInt(),
            businessLicenseUrl = requireNotNull(service.businessLicenseUrl),
            businessCertificateUrl = requireNotNull(service.businessCertificateUrl),
            bankCopyUrl = requireNotNull(service.bankCopyUrl),
            bank = service.bank.trim(),
            accountNumber = service.accountNumber.trim(),
            accountHolder = service.accountHolder.trim(),
            menuIds = service.selectedMenuIds.toList()
        )
        val result = try {
            requestOrderServiceUseCase(shopId, application)
        } finally {
            submittingShopIds.remove(shopId)
        }
        if (result.isSuccess) submittedShopIds.add(shopId)
        if (state.shopId != shopId) return@intent
        reduce {
            state.copy(
                service = state.service.copy(
                    isSubmitting = false,
                    isSubmitted = result.isSuccess,
                    hasSubmitError = result.isFailure,
                    submitErrorMessage = (result.exceptionOrNull() as? ApiException)?.message
                )
            )
        }
    }

    private suspend fun loadOrders(orderableShopId: Int?, category: OwnerOrderCategory) = subIntent {
        if (orderableShopId == null) {
            reduce { state.copy(orders = persistentListOf(), isLoading = false, hasError = false) }
            return@subIntent
        }
        val generation = shopGeneration
        reduce { state.copy(isLoading = true, hasError = false) }
        val result = getOrdersUseCase(orderableShopId, category)
        if (generation != shopGeneration || category != state.category) return@subIntent
        result.onSuccess {
            reduce { state.copy(orders = it.map { order -> order.toOrderUiModel() }.toImmutableList(), isLoading = false) }
        }.onFailure { reduce { state.copy(isLoading = false, hasError = true) } }
    }
}
