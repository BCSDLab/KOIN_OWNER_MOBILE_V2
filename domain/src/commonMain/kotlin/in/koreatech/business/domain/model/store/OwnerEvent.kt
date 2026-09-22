package `in`.koreatech.business.domain.model.store

data class OwnerEvent(
    val id: Int,
    val shopId: Int,
    val shopName: String,
    val title: String,
    val content: String,
    val imageUrls: List<String>,
    val startDate: String,
    val endDate: String
)
