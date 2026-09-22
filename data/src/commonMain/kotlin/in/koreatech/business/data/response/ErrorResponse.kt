package `in`.koreatech.business.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String? = null,
    val message: String? = null,
    val errorTraceId: String? = null,
    val fieldErrors: List<FieldErrorResponse> = emptyList()
)

@Serializable
data class FieldErrorResponse(
    val field: String = "",
    val message: String = "",
    val constraint: String? = null
)
