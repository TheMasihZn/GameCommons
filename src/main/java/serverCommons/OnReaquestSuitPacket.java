package serverCommons;

import gameCommons.GameFrame;
import gameCommons.Player;

public class OnReaquestSuitPacket extends Packet {
    private final Player player;
    private final GameFrame frame;
    public OnReaquestSuitPacket(Player player, GameFrame frame) {
        super(Type.ON_REQUEST_SUIT);
        this.player = player;
        this.frame = frame;
    }
    public Player getPlayer() {
        return player;
    }
    public GameFrame getFrame() {
        return frame;
    }
}
