package gameCommons;

public class GameConfig {
    private final int deckCount;
    private final int cardsPerPlayer;
    private final int mode;
    private final String name;

    public GameConfig(int deckCount, int cardsPerPlayer, int mode, String name) {
        this.deckCount = deckCount;
        this.cardsPerPlayer = cardsPerPlayer;
        this.mode = mode;
        this.name = name;
    }

    public int getDeckCount() {
        return deckCount;
    }

    public int getCardsPerPlayer() {
        return cardsPerPlayer;
    }

    public int getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }
}
