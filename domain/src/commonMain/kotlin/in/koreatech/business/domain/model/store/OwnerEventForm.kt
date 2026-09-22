package `in`.koreatech.business.domain.model.store

data class OwnerEventForm(
    val title: String,
    val content: String,
    val imageUrls: List<String>,
    val startDate: String,
    val endDate: String
)
