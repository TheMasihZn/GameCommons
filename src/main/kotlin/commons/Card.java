import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Card {
    public final Suit suit;
    public final Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Card() {
        this(Suit.Null, Value.T);
    }

    public Card(Value value) {
        this(Suit.Null, value);
    }

    public Card(Suit suit) {
        this(suit, Value.T);
    }

    public boolean tops(Card c) {
        if (this.suit == Suit.Null || c.suit == Suit.Null) {
            return this.value == c.value;
        }
        if (this.value == Value.T || c.value == Value.T) {
            return this.suit == c.suit;
        }
        return this.suit == c.suit || this.value == c.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        return suit == other.suit && value == other.value;
    }

    @Override
    public int hashCode() {
        return suit.hashCode() * 255 + value.hashCode();
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

    public enum Value {
        A, II, III, IV, V, VI, VII, VIII, IX, X, J, Q, K, T;

        public static Set<Value> legal() {
            return EnumSet.complementOf(EnumSet.of(T));
        }
    }
}
