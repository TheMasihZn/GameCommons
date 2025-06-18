package gameCommons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirtyNineGame extends GameBase {

    public DirtyNineGame(GameFrame frame, GameCallback callback, GameConfig config) {
        super(frame, config, callback);
        initializeDeck();
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
            case X:
                newDirection = newDirection.reverse();
                callback.onReverseDirection(getGameFrame());
                nextTurn = nextTurnId(turnId, newDirection);
                break;
            case A:
                nextTurn = nextTurnId(nextTurn, newDirection);
                skipNext = true;
                break;
            case VII:
                callback.onPenaltyChanged(getGameFrame());
                pendingDrawCount += 4;
                break;
            case J:
                callback.onRequestSuit(currentPlayer, getGameFrame());
                nextTurn = nextTurnId(nextTurn, newDirection);
                break;
            case II:
                callback.onRequestGift(currentPlayer, to, card, getGameFrame());
                break;
            case VIII:
                nextTurn = turnId;
                callback.onNextTurn(currentPlayer, currentPlayer, getGameFrame());
                break;
            case IX:
                Map<Long, List<Card>> handCopies = new HashMap<>();
                for (Player p : players) {
                    handCopies.put(p.getId(), new ArrayList<>(p.getHand()));
                }
                for (Player p : players) {
                    long nextId = nextTurnId(p.getId(), direction);
                    Player nextPlayer = getPlayerById(nextId);

                    List<Card> cardsToGive = handCopies.get(p.getId());
                    p.getHand().clear();
                    nextPlayer.getHand().addAll(cardsToGive);

                    callback.onMoveHandByDirection(getGameFrame(), currentPlayer, to, card);
                }
                break;
            default:
                if (skipNext) {
                    nextTurn = nextTurnId(nextTurn, newDirection);
                }
                skipNext = false;
                break;
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
