package dirty7server.masih.zn


import AckPacket
import Lobby
import LobbyListPacket
import FunctionPacket
import LobbyInfoPacket
import SerializerTypeAdapter
import PlayerNetworkWrapper
import com.google.gson.GsonBuilder
import gameCommons.GameConfig
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import serverCommons.GameConfigPacket
import serverCommons.Packet
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

fun main() {

    val lobbies = mutableMapOf<Long, Lobby>(
        0L to Lobby.MainLobby(),
        1L to Lobby(1L, 7, GameConfig(2, 2, 7, "test1")),
        2L to Lobby(2L, 11, GameConfig(2, 2, 7, "test2")),
        3L to Lobby(3L, 9, GameConfig(2, 2, 7, "test3")),
    )
    val mainLobby = lobbies[0]!!
    val channels = mutableMapOf<Long, PlayerNetworkWrapper>()

    embeddedServer(Netty, port = 8089, host = "192.168.4.125") {

        install(WebSockets) {
            maxFrameSize = Long.MAX_VALUE
            contentConverter = GsonWebsocketContentConverter(
                GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeHierarchyAdapter(Packet::class.java, SerializerTypeAdapter<Packet>())
                    .create()
            )
        }

        routing {
            webSocket("/lobby") {
                while (true) {
                    val packet =
                        receiveDeserialized<Packet>()
                    val channel = channels[packet.sender.id]
                        ?: PlayerNetworkWrapper(packet.sender, this, mainLobby.id)
                            .also {
                                channels[packet.sender.id] = it
                                mainLobby.players.add(packet.sender)
                            }

                    when (packet) {
                        is AckPacket ->{
                            channel.lastPacket = packet.hash
                            null
                        }

                        is LobbyInfoPacket -> {
                            when (val lobby = lobbies[packet.lobby.id]) {
                                null -> FunctionPacket(FunctionPacket.Function.LobbyNotFound)
                                else -> {
                                    lobby.players.add(packet.sender)
                                    LobbyInfoPacket(lobby)
                                }
                            }
                        }

                        is GameConfigPacket -> {
                            val lobby = Lobby(packet.sender.id, packet.sender.icon, packet.gameConfig)
                            lobbies[packet.sender.id]?.let { previousLobby ->
                                lobby.players.addAll(previousLobby.players)
                            }
                            lobby.players.add(packet.sender)
                            lobbies[packet.sender.id] = lobby

                            lobby.players.map { channels[it.id] }.forEach {
                                it?.lobby = lobby.id
                                it?.send(LobbyInfoPacket(lobby))
                            }
                            mainLobby.players.forEach { channels[it.id]?.send(LobbyListPacket(lobbies.values)) }

                            FunctionPacket(FunctionPacket.Function.LobbyCreated)
                        }

                        else -> {
                            when (val id = channel.lobby) {
                                null -> FunctionPacket(FunctionPacket.Function.LobbyNotFound)
                                mainLobby.id -> LobbyListPacket(lobbies.values.toList() - mainLobby)
                                else -> LobbyInfoPacket(lobbies[id]!!)
                            }
                        }

                    }?.let { p->
                        if (p.hashCode() != channel.lastPacket)
                            sendSerialized(p).also { channel.lastPacket = p.hashCode() }
                    }
                }
            }
        }

    }.start(wait = true)

}