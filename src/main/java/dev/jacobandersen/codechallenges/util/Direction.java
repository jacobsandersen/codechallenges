package dev.jacobandersen.codechallenges.util;

public enum Direction {
    NORTH(Pair.of(-1, 0)),
    NORTH_EAST(Pair.of(-1, 1)),
    EAST(Pair.of(0, 1)),
    SOUTH_EAST(Pair.of(1, 1)),
    SOUTH(Pair.of(1, 0)),
    SOUTH_WEST(Pair.of(1, -1)),
    WEST(Pair.of(0, -1)),
    NORTH_WEST(Pair.of(-1, -1));

    private final Pair<Integer, Integer> relativeCoordinate;

    Direction(final Pair<Integer, Integer> relativeCoordinate) {
        this.relativeCoordinate = relativeCoordinate;
    }

    public final Pair<Integer, Integer> relativeCoordinate() {
        return relativeCoordinate;
    }

    public final Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST -> WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST -> EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }

    public final Direction getCardinalRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> this;
        };
    }
}
