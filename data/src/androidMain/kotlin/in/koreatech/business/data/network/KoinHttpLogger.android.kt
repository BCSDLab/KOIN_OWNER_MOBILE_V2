package `in`.koreatech.business.data.network

import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger

internal actual val koinHttpLogger: Logger = Logger.ANDROID
