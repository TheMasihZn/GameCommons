package gameCommons;

public class DirtySevenGame extends GameBase {

    public DirtySevenGame(GameFrame frame, GameCallback callback) {
        super(frame, callback);
    }

    @Override
    public boolean isValidMove(Card playedCard) {
        if (floor.getValue() == Card.Values.VII && pendingDrawCount > 0) {
            return playedCard.getValue() == Card.Values.VII;
        }
        return playedCard.tops(floor) || playedCard.getValue() == Card.Values.J;
    }

    @Override
    public void playCard(Player currentPlayer, Player to, Card card) {
        if (!currentPlayer.equals(getCurrentPlayer())) {
            callback.onOutOfTurnPlayed(currentPlayer);
            return;
        }
        if (!isValidMove(card)) {
            callback.onInvalidMoveMade(currentPlayer);
            return;
        }

        callback.onCardPlayed(currentPlayer, card, getGameFrame());
        currentPlayer.getHand().remove(card);

        Direction newDirection = direction;
        long nextTurn = nextTurnId();

        switch (card.getValue()) {
            case X -> {
                newDirection = newDirection.reverse();
                callback.onReverseDirection(getGameFrame());
                nextTurn = nextTurnId(turnId, newDirection);
            }
            case A -> {
                nextTurn = nextTurnId(nextTurn, newDirection);
                skipNext = true;
            }
            case VII -> {
                callback.onPenaltyChanged(getGameFrame());
                pendingDrawCount += 2;
            }
            case J -> {
                callback.onRequestSuit(currentPlayer, getGameFrame());
                nextTurn = nextTurnId(nextTurn, newDirection);
            }
            case II -> callback.onRequestGift(currentPlayer, to, card, getGameFrame());
            case VIII -> {
                nextTurn = turnId;
                callback.onNextTurn(currentPlayer, currentPlayer, getGameFrame());
            }
            default -> {
                if (skipNext) {
                    nextTurn = nextTurnId(nextTurn, newDirection);
                }
                skipNext = false;
            }
        }

        floor = card;
        turnId = nextTurn;

        callback.onNextTurn(currentPlayer, to, getGameFrame());
        if (isGameOver()) callback.onGameFinished(getGameFrame());
    }

    @Override
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
}
