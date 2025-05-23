package dirty7server.masih.zn

import gameCommons.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import serverCommons.Packet
import serverCommons.PacketUtils
import serverCommons.RequestSuitPacket
import java.time.Duration
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets() {
    val players = mutableListOf<Player>()
    val games = mutableListOf<GameBase>()
    routing {
        webSocket("/") { // websocketSession
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    outgoing.send(Frame.Text("YOU SAID: $text"))
                    if (text.equals("bye", ignoreCase = true)) {
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                    }
                }
            }
            while (true) {
                val packet = PacketUtils.deserialize(receiveDeserialized<Packet>())
            }
        }

        webSocket("/create_game") { // websocketSession
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    outgoing.send(Frame.Text("YOU SAID: $text"))
//                    get game config from the host player
                    games += DirtyNineGame(
//                        todo
                         ,object: GameCallback{
                            override fun onRequestSuit(player: Player?, frame: GameFrame?) {
                                players.intersect(game.players.toSet()).forEach {
                                    send(RequestSuitPacket())
                                }
                            }

                            override fun onGameFinished(frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onNextTurn(oldPlayer: Player?, newPlayer: Player?, frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onCardPlayed(player: Player?, card: Card?, frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onCardDrawn(player: Player?, card: Card?, frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onInvalidMoveMade(player: Player?) {
                                TODO("Not yet implemented")
                            }

                            override fun onOutOfTurnPlayed(player: Player?) {
                                TODO("Not yet implemented")
                            }

                            override fun onStart(frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onCardTransferred(from: Player?, to: Player?, card: Card?, frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onPenaltyChanged(frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onRequestGift(from: Player?, to: Player?, card: Card?, frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onReverseDirection(frame: GameFrame?) {
                                TODO("Not yet implemented")
                            }

                            override fun onMoveHandByDirection(
                                frame: GameFrame?,
                                from: Player?,
                                to: Player?,
                                card: Card?
                            ) {
                                TODO("Not yet implemented")
                            }
                        },
                        //todo
                    )

                    if (text.equals("bye", ignoreCase = true)) {
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                    }
                }
            }
        }
        webSocket("/join%game_id") { // websocketSession
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    outgoing.send(Frame.Text("YOU SAID: $text"))
//                    get game config from the host player

                    if (text.equals("bye", ignoreCase = true)) {
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                    }
                }
            }
        }
    }
}
