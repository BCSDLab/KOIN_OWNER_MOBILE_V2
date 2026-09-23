package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.ErrorResponse
import `in`.koreatech.business.domain.error.ApiException
import `in`.koreatech.business.domain.error.ApiFieldError

fun ErrorResponse.toApiException(
    statusCode: Int,
    fallbackMessage: String
): ApiException = ApiException(
    statusCode = statusCode,
    code = code,
    message = message ?: fallbackMessage,
    errorTraceId = errorTraceId,
    fieldErrors = fieldErrors.map {
        ApiFieldError(
            field = it.field,
            message = it.message,
            constraint = it.constraint
        )
    }
)
