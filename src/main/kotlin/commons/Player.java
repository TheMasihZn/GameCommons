
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private long id;
    private String name;
    private int icon;
    private List<Card> hand;
    public long timeout;

    public Player(long id, String name, int icon) {
        this(id, name, icon, new ArrayList<>(), 0);
    }

    public Player(long id, String name, int icon, List<Card> hand) {
        this(id, name, icon, hand, 0);
    }

    public Player(long id, String name, int icon, List<Card> hand, long timeout) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.hand = hand;
        this.timeout = timeout;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
        return name + " : " + hand.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
