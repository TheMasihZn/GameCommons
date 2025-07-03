import serverCommons.Packet
import java.io.Serializable

data class LobbyInfoPacket(val lobby:Lobby) : Packet(Type.server)