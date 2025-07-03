import serverCommons.Packet
import java.io.Serializable

data class FunctionPacket(val function: Function) : Packet(Type.server), Serializable {
    enum class Function  {
        LobbyNotFound,
        JoinAllowed,
        JoinDenied,
        LobbyCreated
    }
}