package gameCommons;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;


public class Card {
    private final Suit suit;
    private final Values value;

    public Card(Suit suit, Values value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }
    public Values getValue() {
        return value;
    }


    public Card() {
        this(Suit.Null, Values.T);
    }

    public Card(Values value) {
        this(Suit.Null, value);
    }

    public Card(Suit suit) {
        this(suit, Values.T);
    }

    public boolean tops(Card c) {
        if (this.suit == Suit.Null || c.suit == Suit.Null) {
            return this.value == c.value;
        }
        if (this.value == Values.T || c.value == Values.T) {
            return this.suit == c.suit;
        }
        return this.suit == c.suit || this.value == c.value;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }

    public enum Suit {
        Diamonds, Hearts, Spades, Clubs, Null;

        public static Set<Suit> legal() {
            return EnumSet.complementOf(EnumSet.of(Null));
        }
    }

    public enum Values {
        A, II, III, IV, V, VI, VII, VIII, IX, X, J, Q, K, T;

        public static Set<Values> legal() {
            return EnumSet.complementOf(EnumSet.of(T));
        }
    }
}
