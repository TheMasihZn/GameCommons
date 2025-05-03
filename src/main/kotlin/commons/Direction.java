
public enum Direction {
    Clockwise,
    Counterclockwise;

    public Direction reverse() {
        return this == Clockwise ? Counterclockwise : Clockwise;
    }
}
