package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.store.ShopCategoryItemResponse
import `in`.koreatech.business.domain.model.store.ShopCategory

internal fun ShopCategoryItemResponse.toShopCategory(): ShopCategory = ShopCategory(
    id = id,
    name = name,
    imageUrl = imageUrl
)
