package `in`.koreatech.business.domain.model.store

data class StoreUrl(
    val uri: String,
    val resultUrl: String,
    val fileName: String,
    val mediaType: String,
    val preSignedUrl: String,
    val fileSize: Long
)
