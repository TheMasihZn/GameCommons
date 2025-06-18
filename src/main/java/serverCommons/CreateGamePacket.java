package serverCommons;

import gameCommons.GameConfig;
import gameCommons.Player;

public class CreateGamePacket extends Packet {
    private final GameConfig gameConfig;
    private final int gameMode;
    public CreateGamePacket(GameConfig gameConfig, Player sender, int gameMode) {
        super(Type.ON_START, sender);
        this.gameConfig = gameConfig;
        this.gameMode = gameMode;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public int getGameMode() {
        return gameMode;
    }
}
