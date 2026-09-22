package `in`.koreatech.business.domain.model.store

data class OwnerMenuForm(
    val categoryIds: List<Int>,
    val name: String,
    val description: String,
    val prices: List<OwnerMenuPrice>,
    val imageUrls: List<String>
)
