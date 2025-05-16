package gameCommons;

public interface GameCallback {
    void onRequestSuit(Player player, GameFrame frame);
    void onGameFinished(GameFrame frame);
    void onNextTurn(Player oldPlayer, Player newPlayer, GameFrame frame);
    void onCardPlayed(Player player, Card card, GameFrame frame);
    void onCardDrawn(Player player, Card card, GameFrame frame);
    void onInvalidMoveMade(Player player);
    void onOutOfTurnPlayed(Player player);
    void onStart(GameFrame frame);
    void onCardTransferred(Player from, Player to, Card card, GameFrame frame);
    void onPenaltyChanged(GameFrame frame);
    void onRequestGift(Player from, Player to, Card card, GameFrame frame);
    void onReverseDirection(GameFrame frame);
    }
