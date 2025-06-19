package gameCommons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player extends DataPack {
    private List<Card> hand;
    public long timeout;

    public Player(long id, String name, int icon) {
        this(id, name, icon, new ArrayList<>(), 0);
    }

    public Player(long id, String name, int icon, List<Card> hand) {
        this(id, name, icon, hand, 0);
    }

    public Player(long id, String name, int icon, List<Card> hand, long timeout) {
        super(id, name, icon);
        this.hand = hand;
        this.timeout = timeout;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public long getTimeout() {
        return timeout;
    }

    @Override
    public String toString() {
        return super.getName() + " : " + hand.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
