package dirty7server.masih.zn

import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.websocket.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        install(WebSockets) {
            pingPeriod = 1.seconds.toJavaDuration()
            timeout = 5.seconds.toJavaDuration()
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }
        configureSockets()
    }.start(wait = true)
}