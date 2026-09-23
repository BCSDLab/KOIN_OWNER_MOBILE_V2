package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.request.store.OwnerMenuPriceRequest
import `in`.koreatech.business.data.request.store.OwnerMenuRequest
import `in`.koreatech.business.domain.model.store.OwnerMenuForm

internal fun OwnerMenuForm.toOwnerMenuRequest(): OwnerMenuRequest {
    val isSingle = prices.singleOrNull()?.option == null
    return OwnerMenuRequest(
        categoryIds = categoryIds,
        description = description,
        imageUrls = imageUrls,
        isSingle = isSingle,
        name = name,
        optionPrices = prices.takeUnless { isSingle }?.map {
            OwnerMenuPriceRequest(option = it.option.orEmpty(), price = it.price)
        },
        singlePrice = prices.singleOrNull()?.price?.takeIf { isSingle }
    )
}
