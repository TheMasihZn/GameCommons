package gameCommons;

import kotlin.random.Random;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck(int numberOfDecks) {
        for (int d = 0; d < numberOfDecks; d++) {
            for (Card.Suit suit : Card.Suit.legal()) {
                for(Card.Values value : Card.Values.legal()) {
                    cards.add(new Card(suit, value));
                }
            }
        }
    }

    private List<Card> getCards() {
        if (cards.isEmpty()) {
            for (Card.Suit suit : Card.Suit.legal()) {
                for (Card.Values value : Card.Values.legal()) {
                    cards.add(new Card(suit, value));
                }
            }
        }
        return cards;
    }

    public Card take() {
        int index = Random.Default.nextInt(cards.size());
        return cards.remove(index);
    }

    public List<Card> take(int n) {
        List<Card> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(take());
        }
        return result;
    }

    public boolean add(Card c) {
        return getCards().add(c);
    }
}
