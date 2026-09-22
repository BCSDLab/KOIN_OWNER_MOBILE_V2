package `in`.koreatech.business.domain.model.store

data class OwnerMenuDetail(
    val id: Int,
    val shopId: Int,
    val name: String,
    val isHidden: Boolean,
    val categoryIds: List<Int>,
    val description: String,
    val prices: List<OwnerMenuPrice>,
    val imageUrls: List<String>
)
