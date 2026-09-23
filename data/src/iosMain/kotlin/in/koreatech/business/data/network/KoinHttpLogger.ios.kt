package `in`.koreatech.business.data.network

import io.ktor.client.plugins.logging.Logger

internal actual val koinHttpLogger: Logger = object : Logger {
    override fun log(message: String) {
        println(message)
    }
}
