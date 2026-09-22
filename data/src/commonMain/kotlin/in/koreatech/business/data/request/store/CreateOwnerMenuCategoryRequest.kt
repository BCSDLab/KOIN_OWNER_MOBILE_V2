package `in`.koreatech.business.data.request.store

import kotlinx.serialization.Serializable

@Serializable
data class CreateOwnerMenuCategoryRequest(
    val name: String
)
