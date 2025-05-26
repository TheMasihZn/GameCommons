package serverCommons;

import gameCommons.Player;

public class OnInvalidMoveMadePacket extends Packet {
    private final Player player;
    public OnInvalidMoveMadePacket(Player player) {
        super(Type.ON_INVALID_MOVE_MADE);
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
}
