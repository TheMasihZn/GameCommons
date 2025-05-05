import java.util.List;

public class Game extends GameFrame {

    private Deck deck = new Deck();

    public Game(GameFrame initialFrame) {
        super(
                initialFrame.getFloor(),
                initialFrame.getPlayers(),
                initialFrame.getTurnId(),
                initialFrame.getDirection()
        );
    }

    public Player getCurrentPlayer() {
        return players.stream()
                .filter(p -> p.getId() == turnId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No current player"));
    }

    public boolean isValidMove(Card playedCard) {
        return playedCard.tops(floor) || playedCard.getValue() == Card.Values.J;
    }

    public void playCard(Player player, Card card) {
        if (!player.equals(getCurrentPlayer())) {
            throw new IllegalStateException("It's not your turn");
        }
        if (!isValidMove(card)) {
            throw new IllegalStateException("Invalid move" + card);
        }
        player.getHand().remove(card);

        Direction newDirection = direction;
        long nextTurn = nextTurnId();

        if (card.getValue() == Card.Values.X) {
            newDirection = newDirection.reverse();
            nextTurn = nextTurnId(turnId, newDirection);
        } else if (card.getValue() == Card.Values.A) {
            long skipped = nextTurnId(turnId, newDirection);
            nextTurn = nextTurnId(skipped, newDirection);

        }
        updateTurn();
    }

    public void drawCard(Player player) {
        player.getHand().add(deck.take());
    }
    public void updateTurn(){
        turnId = nextTurnId();
    }

    private long nextTurnId() {
        return nextTurnId(turnId, direction);
    }


    private long nextTurnId(long fromId, Direction dir) {
        int i = players.stream().map(Player::getId).toList().indexOf(fromId);
        i = (dir == Direction.Clockwise) ? i + 1 : i - 1;
        if (i < 0) {
            i = players.size() - 1;
        }
        return players.get(i % players.size()).getId();
    }

    public boolean isGameOver() {
        return players.stream().filter(p -> p.getHand().isEmpty())
                .count() == players.size() - 1;
    }

    public Player getWinner() {
        return players.stream()
                .filter(p -> p.getHand().isEmpty())
                .findFirst().orElse(null);
    }

    public Player getLooser() {
        return players.stream()
                .filter(p -> !p.getHand().isEmpty())
                .findFirst()
                .orElse(null);
    }

    public GameFrame getGameFrame() {
        return  new GameFrame(
                floor,
                players,
                turnId,
                direction);
    }


}
