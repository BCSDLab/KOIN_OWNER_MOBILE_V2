package `in`.koreatech.business.feature.order.model

import `in`.koreatech.business.domain.model.order.OwnerOrder
import `in`.koreatech.business.domain.model.order.OwnerOrderDetail
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class OrderUiModel(
    val id: Int,
    val number: String,
    val type: String,
    val status: String,
    val orderedAt: String,
    val totalPrice: Int
)

data class OrderDetailUiModel(
    val id: Int,
    val number: String,
    val type: String,
    val status: String,
    val orderedAt: String,
    val menus: ImmutableList<OrderMenuUiModel>,
    val receiver: OrderReceiverUiModel,
    val payment: OrderPaymentUiModel
)

data class OrderMenuUiModel(
    val id: Int,
    val name: String,
    val priceName: String?,
    val price: Int,
    val quantity: Int,
    val options: ImmutableList<OrderMenuOptionUiModel>
)

data class OrderMenuOptionUiModel(
    val groupName: String,
    val name: String,
    val quantity: Int
)

data class OrderReceiverUiModel(
    val name: String,
    val phoneNumber: String,
    val address: String?,
    val addressDetail: String?,
    val toOwner: String?,
    val toRider: String?
)

data class OrderPaymentUiModel(
    val totalProductPrice: Int,
    val deliveryTip: Int,
    val discountAmount: Int,
    val totalPrice: Int
)

internal fun OwnerOrder.toOrderUiModel() = OrderUiModel(
    id = id,
    number = number,
    type = type,
    status = status,
    orderedAt = orderedAt,
    totalPrice = totalPrice
)

internal fun OwnerOrderDetail.toOrderDetailUiModel() = OrderDetailUiModel(
    id = id,
    number = number,
    type = type,
    status = status,
    orderedAt = orderedAt,
    menus = menus.map { menu ->
        OrderMenuUiModel(
            id = menu.id,
            name = menu.name,
            priceName = menu.priceName,
            price = menu.price,
            quantity = menu.quantity,
            options = menu.options.map { option ->
                OrderMenuOptionUiModel(
                    groupName = option.groupName,
                    name = option.name,
                    quantity = option.quantity
                )
            }.toImmutableList()
        )
    }.toImmutableList(),
    receiver = OrderReceiverUiModel(
        name = receiver.name,
        phoneNumber = receiver.phoneNumber,
        address = receiver.address,
        addressDetail = receiver.addressDetail,
        toOwner = receiver.toOwner,
        toRider = receiver.toRider
    ),
    payment = OrderPaymentUiModel(
        totalProductPrice = payment.totalProductPrice,
        deliveryTip = payment.deliveryTip,
        discountAmount = payment.discountAmount,
        totalPrice = payment.totalPrice
    )
)
