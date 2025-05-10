public class Game extends GameFrame {

    private boolean skipPending = false;
    private int pendingDrawCount = 0;

    private Deck deck = new Deck();

    private CallBack callback;

    public Game(GameFrame initialFrame, CallBack Callback) {
        super(
                initialFrame.getFloor(),
                initialFrame.getPlayers(),
                initialFrame.getTurnId(),
                initialFrame.getDirection()
        );
        this.callback = Callback;
        callback.onStart(getGameFrame());
    }

    public Player getCurrentPlayer() {
        return players.stream()
                .filter(p -> p.getId() == turnId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No current player"));
    }

    public boolean isValidMove(Card playedCard) {
        if (floor.getValue() == Card.Values.VII && pendingDrawCount > 0) { // 7 penalty bug fixed
            return playedCard.getValue() == Card.Values.VII;
        }
        return playedCard.tops(floor) || playedCard.getValue() == Card.Values.J;
    }

    // transform to switch case
    public void playCard(Player currentPlayer, Player to, Card card) {
        if (!currentPlayer.equals(getCurrentPlayer())) {
            callback.onOutOfTurnPlayed(currentPlayer);
        }
        if (!isValidMove(card)) {
            callback.onInvalidMoveMade(currentPlayer);
        }
        callback.onCardPlayed(currentPlayer, card, getGameFrame());
        currentPlayer.getHand().remove(card);

        Direction newDirection = direction;
        long nextTurn = nextTurnId();

        if (card.getValue() == Card.Values.X) {
            newDirection = newDirection.reverse();
            callback.onReverseDirection(getGameFrame());
            nextTurn = nextTurnId(turnId, newDirection);
        } else if (card.getValue() == Card.Values.A) {
            nextTurn = nextTurnId(nextTurn, newDirection);
            skipPending = true;
        } else if (card.getValue() == Card.Values.VII) {
            callback.onPenaltychanged(getGameFrame());
            pendingDrawCount += 2;

        } else if (card.getValue() == Card.Values.J) {
            callback.onRequestSuit(currentPlayer, getGameFrame());
            nextTurn = nextTurnId(nextTurn, newDirection);
//card 2 properties added
        } else if (card.getValue() == Card.Values.II) {
            callback.onRequestGift(currentPlayer, to, card, getGameFrame());

        } else if (card.getValue() == Card.Values.VIII) {
            nextTurn = turnId;
            callback.onNextTurn(currentPlayer, currentPlayer, getGameFrame());

        } else {
            if (skipPending) {
//                skip another time
                nextTurn = nextTurnId(nextTurn, newDirection);
            }
            skipPending = false;
        }
        floor = card; //  update floor fixed
        turnId = nextTurn;
        callback.onNextTurn(currentPlayer, to, getGameFrame());
        if (isGameOver()) callback.onGameFinished(getGameFrame());
        getGameFrame();
    }

    // draw card logic fixed

    public void drawCard(Player player, Card card) {
        long nextTurn = nextTurnId();

        if (pendingDrawCount > 0) {
            for (int i = 0; i < pendingDrawCount; i++) {
                callback.onCardDrawn(player, card, getGameFrame());
                player.getHand().add(deck.take());
            }
            pendingDrawCount = 0;
        } else {
            callback.onCardDrawn(player, card, getGameFrame());
            player.getHand().add(deck.take());
        }
        turnId = nextTurn;
    }
//method attemptPlay added to handle turn update correctly

    public boolean attemptPlay(Player currentplayer, Player to, Card card) {
        if (!currentplayer.equals(getCurrentPlayer())) return false;

        if (isValidMove(card)) {
            playCard(currentplayer, to, card);
            return true;
        }
        return false;
    }

    //method takeRandom for getting a card from other players implemented
    public void tranferCard(Player from, Player to, Card card) {
        from.getHand().remove(card);
        to.getHand().add(card);
        callback.onCardTransfered(from, to, card, getGameFrame());
    }

    //target player method for card 2 reaction implemented
//    public Player chooseTargetPlayer(Player exclude) {
//        List<Player> others = new ArrayList<>(players);
//        others.remove(exclude);
//
//        if (others.isEmpty()) throw new IllegalStateException("No other players");
//
//        Random random = new Random();
//        return others.get(random.nextInt(others.size()));
//    }

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

    private GameFrame getGameFrame() {
        return new GameFrame(
                floor,
                players,
                turnId,
                direction);
    }


}

interface CallBack {
    void onRequestSuit(Player player, GameFrame frame);

    void onGameFinished(GameFrame frame);

    void onNextTurn(Player oldPlayer, Player newPlayer, GameFrame frame);

    void onCardPlayed(Player player, Card card, GameFrame frame);

    void onCardDrawn(Player player, Card card, GameFrame frame);

    void onInvalidMoveMade(Player player);

    void onOutOfTurnPlayed(Player player);

    void onStart(GameFrame frame);

    void onCardTransfered(Player from, Player to, Card card, GameFrame frame);

    void onPenaltychanged(GameFrame frame);

    void onRequestGift(Player from, Player to, Card card, GameFrame frame);

    void onReverseDirection(GameFrame frame);

}