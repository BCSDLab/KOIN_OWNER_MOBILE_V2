package `in`.koreatech.business.data.network

import `in`.koreatech.business.data.mapper.toApiException
import `in`.koreatech.business.data.response.ErrorResponse
import `in`.koreatech.business.data.source.local.TokenLocalDataSource
import `in`.koreatech.business.domain.error.ApiException
import `in`.koreatech.business.domain.error.NetworkException
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

internal expect val koinHttpLogger: Logger

private val networkJson =
    Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

private val baseUrl = Url(BuildKonfig.BASE_URL)

internal fun createKoinHttpClient(tokenLocalDataSource: TokenLocalDataSource) =
    HttpClient {
        expectSuccess = true

        HttpResponseValidator {
            handleResponseExceptionWithRequest { cause, _ ->
                val responseException = cause as? ResponseException
                if (responseException == null) {
                    if (cause is IOException) throw NetworkException(cause)
                    return@handleResponseExceptionWithRequest
                }
                if (responseException.response.status == HttpStatusCode.Unauthorized) {
                    tokenLocalDataSource.clearTokens()
                }
                val errorResponse =
                    runCatching {
                        networkJson.decodeFromString<ErrorResponse>(responseException.response.bodyAsText())
                    }.getOrNull()
                throw errorResponse?.toApiException(
                    statusCode = responseException.response.status.value,
                    fallbackMessage = responseException.response.status.description
                ) ?: ApiException(
                    statusCode = responseException.response.status.value,
                    code = null,
                    message = responseException.response.status.description,
                    errorTraceId = null,
                    fieldErrors = emptyList()
                )
            }
        }

        install(ContentNegotiation) {
            json(
                networkJson
            )
        }

        install(Auth) {
            bearer {
                cacheTokens = false
                loadTokens {
                    tokenLocalDataSource.getAccessToken()?.let { accessToken ->
                        BearerTokens(
                            accessToken = accessToken,
                            refreshToken = tokenLocalDataSource.getRefreshToken().orEmpty()
                        )
                    }
                }
                sendWithoutRequest { request ->
                    request.url.host == baseUrl.host &&
                        request.headers[HttpHeaders.Authorization] == null
                }
            }
        }

        install(Logging) {
            logger = koinHttpLogger
            level = LogLevel.ALL
            sanitizeHeader { header ->
                header == HttpHeaders.Authorization ||
                    header == HttpHeaders.Cookie ||
                    header == HttpHeaders.SetCookie
            }
        }

        install(DefaultRequest) {
            url(BuildKonfig.BASE_URL)
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
