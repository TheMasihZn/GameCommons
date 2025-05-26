package serverCommons;

import gameCommons.Card;
import gameCommons.GameFrame;
import gameCommons.Player;

public class OnCardDrawnPacket extends Packet {
    private final Player player;
    private final Card card;
    private final GameFrame frame;
    public OnCardDrawnPacket(Player player, Card card, GameFrame frame) {
        super(Type.ON_CARD_DRAWN);
        this.player = player;
        this.card = card;
        this.frame = frame;
    }
    public Player getPlayer() {
        return player;
    }
    public Card getCard() {
        return card;
    }
    public GameFrame getFrame() {
        return frame;
    }
}
