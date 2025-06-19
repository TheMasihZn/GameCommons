import serverCommons.Packet

data class RequestJoinLobbyPacket(val lobby: Lobby) : Packet(Type.server)