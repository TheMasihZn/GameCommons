package serverCommons;

import gameCommons.Card;
import gameCommons.GameFrame;
import gameCommons.Player;

public class OnCardTransferredPacket extends Packet {
    private final Player from;
    private final Player to;
    private final Card card;
    private final GameFrame frame;

    public OnCardTransferredPacket(Player from, Player to, Card card, GameFrame frame) {
        super(Type.ON_CARD_TRANSFERRED);
        this.from = from;
        this.to = to;
        this.card = card;
        this.frame = frame;
    }
    public Player getFrom() {
        return from;
    }
    public Player getTo() {
        return to;
    }
    public Card getCard() {
        return card;
    }
    public GameFrame getFrame() {
        return frame;
    }
}
