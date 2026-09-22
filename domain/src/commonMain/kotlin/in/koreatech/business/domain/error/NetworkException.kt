package `in`.koreatech.business.domain.error

class NetworkException(
    cause: Throwable
) : Exception(cause.message, cause)
