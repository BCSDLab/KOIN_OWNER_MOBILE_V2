package `in`.koreatech.business.data.response.address

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressSearchResponse(
    val addresses: List<AddressResponse> = emptyList(),
    @SerialName("count_per_page") val countPerPage: Int = 0,
    @SerialName("current_page") val currentPage: Int = 0,
    @SerialName("total_count") val totalCount: String = "0"
)

@Serializable
data class AddressResponse(
    @SerialName("bd_nm") val buildingName: String = "",
    @SerialName("emd_nm") val eupMyeonDongName: String = "",
    @SerialName("eng_address") val englishAddress: String = "",
    @SerialName("jibun_address") val jibunAddress: String = "",
    @SerialName("li_nm") val riName: String = "",
    @SerialName("rn") val roadName: String = "",
    @SerialName("road_address") val roadAddress: String = "",
    @SerialName("sgg_nm") val cityCountyDistrictName: String = "",
    @SerialName("si_nm") val cityProvinceName: String = "",
    @SerialName("zip_no") val zipCode: String = ""
)
