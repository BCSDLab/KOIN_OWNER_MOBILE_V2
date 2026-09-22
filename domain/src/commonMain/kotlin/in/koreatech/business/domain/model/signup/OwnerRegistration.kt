package `in`.koreatech.business.domain.model.signup

data class OwnerRegistration(
    val attachmentUrls: List<String>,
    val companyNumber: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val shopNumber: String,
    val shopId: Int?,
    val shopName: String
)
