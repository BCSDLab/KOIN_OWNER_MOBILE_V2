package `in`.koreatech.business.data.response.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerMenuCategoriesResponse(
    val count: Long = 0,
    @SerialName("menu_categories") val menuCategories: List<OwnerMenuCategoryOptionResponse> = emptyList()
)

@Serializable
data class OwnerMenuCategoryOptionResponse(
    val id: Int,
    val name: String
)
