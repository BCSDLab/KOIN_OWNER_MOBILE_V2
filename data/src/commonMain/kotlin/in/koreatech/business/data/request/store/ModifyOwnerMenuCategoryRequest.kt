package `in`.koreatech.business.data.request.store

import kotlinx.serialization.Serializable

@Serializable
data class ModifyOwnerMenuCategoryRequest(
    val id: Int,
    val name: String
)
