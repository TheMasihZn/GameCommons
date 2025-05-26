package serverCommons;

import gameCommons.GameFrame;

public class OnGameFinishedPacket extends Packet {
    private final GameFrame frame;
    public OnGameFinishedPacket(GameFrame frame) {
        super(Type.ON_GAME_FINISHED);
        this.frame = frame;
    }
    public GameFrame getFrame() {
        return frame;
    }
}
