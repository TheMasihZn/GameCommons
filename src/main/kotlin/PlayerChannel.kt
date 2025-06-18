import serverCommons.Packet
import kotlin.reflect.KSuspendFunction1

class PlayerChannel(private val sender: KSuspendFunction1<Any, Unit>) {
    var lobbyId: Long? = null
    fun send(packet: Packet) {
        sender.call(packet)
    }
}