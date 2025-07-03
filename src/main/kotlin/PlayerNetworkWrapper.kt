import gameCommons.Player
import io.ktor.server.websocket.*
import serverCommons.Packet

class PlayerNetworkWrapper(
    val player: Player,
    private var senderFunction: DefaultWebSocketServerSession,
    var lobby: Long? = null,
    var lastPacket: Int? = null
) {
    suspend fun send(packet: Packet) {
        senderFunction.sendSerialized(packet)
    }
}