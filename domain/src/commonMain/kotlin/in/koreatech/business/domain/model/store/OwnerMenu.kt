package `in`.koreatech.business.domain.model.store

data class OwnerMenu(
    val id: Int,
    val name: String,
    val isHidden: Boolean,
    val description: String?,
    val prices: List<OwnerMenuPrice>,
    val imageUrls: List<String>
)
