import serverCommons.Packet
import java.io.Serializable

data class LobbyListPacket(val lobbies: List<Lobby>) : Packet(Type.server) {
    constructor(lobbies: Collection<Lobby>) : this(lobbies.toList())
}