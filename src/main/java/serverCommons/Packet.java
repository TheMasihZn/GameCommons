package serverCommons;

import java.io.Serializable;

public class Packet implements Serializable {
    public Type packetType;

    public Packet(Type packetType) {
        this.packetType = packetType;
    }

    public enum Type {
        ON_REQUEST_SUIT,
        ON_GAME_FINISHED,
        ON_NEXT_TURN,
        ON_CARD_PLAYED,
        ON_CARD_DRAWN,
        ON_INVALID_MOVE_MADE,
        ON_OUT_OF_TURN_PLAYED,
        ON_START,
        ON_CARD_TRANSFERRED,
        ON_PENALTY_CHANGED,
        ON_REQUEST_GIFT,
        ON_REVERSE_DIRECTION,
        ON_MOVE_HAND_BY_DIRECTION
    }
}

