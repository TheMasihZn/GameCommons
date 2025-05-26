package gameCommons;

import gameCommons.*;
import kotlin.jvm.internal.Lambda;
import serverCommons.Packet;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class GameBase extends GameFrame {
    protected final GameConfig config;
    protected Deck deck;
    protected GameCallback callback;
    protected int pendingDrawCount = 0;
    protected boolean skipNext = false;

    public GameBase(GameFrame frame, GameConfig config, GameCallback callback) {
        super(frame.getFloor(), frame.getPlayers(), frame.getTurnId(), frame.getDirection());
        this.callback = callback;
        this.config = config;
        callback.onStart(getGameFrame());
    }

    protected void initializeDeck() {
        this.deck = new Deck(config.getDeckCount());
        for (Player player : players) {
            List<Card> hand = deck.take(config.getCardsPerPlayer());
            player.setHand(hand);
        }
    }

    public abstract boolean isValidMove(Card card);

    public abstract void playCard(Player from, Player to, Card card);

    public abstract void drawCard(Player player, Card card);

    public boolean attemptPlay(Player currentPlayer, Player to, Card card) {
        if (!currentPlayer.equals(getCurrentPlayer())) return false;
        if (isValidMove(card)) {
            playCard(currentPlayer, to, card);
            return true;
        }
        return false;
    }

    public void transferCard(Player from, Player to, Card card) {
        from.getHand().remove(card);
        to.getHand().add(card);
        callback.onCardTransferred(from, to, card, getGameFrame());
    }


    public abstract HashMap<Card, Consumer> rules();

    public Player getCurrentPlayer() {
        return players.stream()
                .filter(p -> p.getId() == turnId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No current player"));
    }

    public boolean isGameOver() {
        return players.stream().filter(p -> p.getHand().isEmpty()).count() == players.size() - 1;
    }

    public Player getWinner() {
        return players.stream().filter(p -> p.getHand().isEmpty()).findFirst().orElse(null);
    }

    public Player getLooser() {
        return players.stream().filter(p -> !p.getHand().isEmpty()).findFirst().orElse(null);
    }

    protected Player getPlayerById(long id) {
        return players.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No player with ID: " + id));
    }

    protected long nextTurnId() {
        return nextTurnId(turnId, direction);
    }

    protected long nextTurnId(long fromId, Direction dir) {
        int i = players.stream().map(Player::getId).collect(Collectors.toList()).indexOf(fromId);
        i = (dir == Direction.Clockwise) ? i + 1 : i - 1;
        if (i < 0) i = players.size() - 1;
        return players.get(i % players.size()).getId();
    }

    protected GameFrame getGameFrame() {
        return new GameFrame(floor, players, turnId, direction);
    }
}