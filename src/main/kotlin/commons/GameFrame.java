
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GameFrame implements Serializable {
    private Card floor;
    private List<Player> players;
    private long turnId;
    private Direction direction;
    private long timestamp;

    public GameFrame(Card floor, List<Player> players, long turnId, Direction direction) {
        this(floor, players, turnId, direction, System.currentTimeMillis());
    }

    public GameFrame(Card floor, List<Player> players, long turnId, Direction direction, long timestamp) {
        this.floor = floor;
        this.players = players;
        this.turnId = turnId;
        this.direction = direction;
        this.timestamp = timestamp;
    }

    public Card getFloor() {
        return floor;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public long getTurnId() {
        return turnId;
    }

    public Direction getDirection() {
        return direction;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameFrame)) return false;
        GameFrame that = (GameFrame) o;
        return turnId == that.turnId &&
               timestamp == that.timestamp &&
               Objects.equals(floor, that.floor) &&
               Objects.equals(players, that.players) &&
               direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, players, turnId, direction, timestamp);
    }

    @Override
    public String toString() {
        return "GameFrame{" +
               "floor=" + floor +
               ", players=" + players +
               ", turnId=" + turnId +
               ", direction=" + direction +
               ", timestamp=" + timestamp +
               '}';
    }
}
