package dirty7server.masih.zn

import gameCommons.Game
import gameCommons.GameFrame
import serverCommons.Packet
import serverCommons.PacketUtils
import gameCommons.Player
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

fun main() {
    val gf : GameFrame? = null
    val players = mutableSetOf<Player>()
    val games = mutableListOf<Game>()
    val lobbyHosts = mutableSetOf<Player>()
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