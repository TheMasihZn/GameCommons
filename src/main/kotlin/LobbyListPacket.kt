import serverCommons.Packet

data class LobbyListPacket(val lobbies: List<Lobby>) : Packet(Type.server) {
        constructor(lobby: Lobby) : this(listOf(lobby))
        constructor(lobbies: Set<Lobby>) : this(lobbies.toList())
    }