package serverCommons;

import gameCommons.GameFrame;

public class OnStartPacket extends Packet {
    private final GameFrame gameFrame;
    public OnStartPacket(GameFrame gameFrame) {
        super(Type.ON_START);
        this.gameFrame = gameFrame;
    }
    public GameFrame getGameFrame() {
        return gameFrame;
    }
}
