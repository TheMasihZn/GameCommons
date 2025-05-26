package serverCommons;

import gameCommons.GameFrame;
import gameCommons.Player;

public class OnNextTurnPacket extends Packet {
    public Player oldPlayer;
    public Player newPlayer;
    public GameFrame frame;

    public OnNextTurnPacket(Player oldPlayer, Player newPlayer, GameFrame frame) {
        super(Type.ON_NEXT_TURN);
        this.oldPlayer = oldPlayer;
        this.newPlayer = newPlayer;
        this.frame = frame;
    }
}