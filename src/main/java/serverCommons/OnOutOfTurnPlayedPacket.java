package serverCommons;

import gameCommons.Player;

public class OnOutOfTurnPlayedPacket extends Packet {
    private final Player player;
    public OnOutOfTurnPlayedPacket(Player player) {
        super(Type.ON_OUT_OF_TURN_PLAYED);
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
}
