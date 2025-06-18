package dirty7server.masih.zn


import PlayerChannel
import gameCommons.GameBase
import gameCommons.GameConfig
import gameCommons.Player
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import serverCommons.CreateGamePacket
import serverCommons.Packet
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

fun main() {

    data class Lobby(
        val id: Long,
        val gameConfig: GameConfig,
        val game: GameBase? = null,
        val players: MutableList<Player> = mutableListOf()
    )

    data class LobbyListPacket(val lobbies: List<Lobby>) : Packet(Type.server) {
        constructor(lobby: Lobby) : this(listOf(lobby))
        constructor(lobbies: Set<Lobby>) : this(lobbies.toList())
    }


    val lobbies = hashSetOf<Lobby>()
    val channels = mutableMapOf<Player, PlayerChannel>()

    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {

        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        install(WebSockets) {
            pingPeriod = 15.seconds.toJavaDuration()
            timeout = 15.seconds.toJavaDuration()
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }
        routing {
            webSocket("/lobby") {
                val packet = receiveDeserialized<Packet>()
                channels[packet.sender] = PlayerChannel(::sendSerialized)
                channels[packet.sender]?.send(LobbyListPacket(lobbies))
            }
            webSocket("/host") {
                val packet = receiveDeserialized<CreateGamePacket>()
                lobbies.add(Lobby(packet.sender.id, packet.gameConfig))
                channels.values.filter { it.lobbyId != null }
                    .forEach { it.send(LobbyListPacket(lobbies)) }
            }
            webSocket("/lobby/{lobby_id}") {
                val id = call.parameters["lobby_id"]!!.toLong()
                lobbies.forEach { lobby ->
                    if (lobby.id != id) return@forEach
                    val sender = receiveDeserialized<Packet>().sender
                    channels[sender]!!.lobbyId = id
                    lobby.players.add(sender)

                    lobby.players.forEach { p ->
                        channels[p]?.send(LobbyListPacket(lobby))
                    }
                }
            }
        }
    }.start(wait = true)

}