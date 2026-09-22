package `in`.koreatech.business.data.request.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckCompanyNumberRequest(
    @SerialName("company_number") val companyNumber: String
)
