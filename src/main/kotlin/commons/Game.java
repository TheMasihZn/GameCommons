import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game extends GameFrame {

    private boolean skipPending = false;
    private int pendingDrawCount = 0;

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
        if(floor.getValue() == Card.Values.VII && pendingDrawCount > 0) { // 7 penalty bug fixed
            return playedCard.getValue() == Card.Values.VII;
        }
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
            skipPending = false;
        } else if (card.getValue() == Card.Values.A) {
            nextTurn = nextTurnId(nextTurn, newDirection);
            skipPending = true;
        } else if (card.getValue() == Card.Values.VII) {
            pendingDrawCount += 2;

        } else if (card.getValue() == Card.Values.J) {
//"implement choose Suit command"
            nextTurn = nextTurnId(nextTurn, newDirection);
//card 2 properties added
        } else if (card.getValue() == Card.Values.II) {
            Random random = new Random();
            List<Card> hand = player.getHand();
            if(!hand.isEmpty()){
                Card randomCard = hand.remove(random.nextInt(hand.size()));
                Player target = chooseTargetPlayer(player);
                takeRandom(target, randomCard);
            }

        } else if (card.getValue() == Card.Values.VIII) {
            nextTurn = turnId;

        } else {
            if (skipPending) {
//                skip another time
                nextTurn = nextTurnId(nextTurn, newDirection);
            }
            skipPending = false;
        }
        floor = card; //  update floor fixed
        turnId = nextTurn;
    }

    // draw card logic fixed

    public void drawCard(Player player) {

        long nextTurn = nextTurnId();

        if (pendingDrawCount > 0) {
            for (int i = 0; i < pendingDrawCount; i++) {
                player.getHand().add(deck.take());
            }
            pendingDrawCount = 0;
        }else{
            player.getHand().add(deck.take());
        }
        turnId = nextTurn;
    }
//method attemptPlay added to handle turn update correctly

    public boolean attemptPlay(Player player, Card card) {
        if(!player.equals(getCurrentPlayer()))return false;

        if(isValidMove(card)){
            playCard(player, card);
            return true;
        }
        return false;
    }
    //method takeRandom for getting a card from other players implemented
    public void takeRandom(Player player,Card card){
        player.getHand().add(card);
    }
//target player method for card 2 reaction implemented
    public Player chooseTargetPlayer(Player exclude) {
        List<Player> others = new ArrayList<>(players);
        others.remove(exclude);

        if(others.isEmpty()) throw new IllegalStateException("No other players");

        Random random = new Random();
        return others.get(random.nextInt(others.size()));
    }

    private long nextTurnId() {
        return nextTurnId(turnId, direction);
    }


    private long nextTurnId(long fromId, Direction dir) {
        int i = players.stream().map(Player::getId).collect(Collectors.toList()).indexOf(fromId);
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
        return new GameFrame(
                floor,
                players,
                turnId,
                direction);
    }


    interface Ga{
        void g();
    }


}
