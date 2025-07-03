import gameCommons.Player
import serverCommons.Packet
import java.io.Serializable

class AckPacket(val hash: Int, sender: Player) : Packet(Type.ACK, sender)