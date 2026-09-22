package `in`.koreatech.business.domain.error

class ApiException(
    val statusCode: Int,
    val code: String?,
    override val message: String,
    val errorTraceId: String?,
    val fieldErrors: List<ApiFieldError>
) : Exception(message)

data class ApiFieldError(
    val field: String,
    val message: String,
    val constraint: String?
)
