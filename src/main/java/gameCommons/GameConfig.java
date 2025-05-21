package gameCommons;

public class GameConfig {
    private final int deckCount;
    private final int cardsPerPlayer;

    public GameConfig(int deckCount,int cardsPerPlayer) {
        this.deckCount = deckCount;
        this.cardsPerPlayer = cardsPerPlayer;
    }

    public int getDeckCount() {
        return deckCount;
    }
    public int getCardsPerPlayer() {
        return cardsPerPlayer;
    }
}
