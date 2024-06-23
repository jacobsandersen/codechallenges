package dev.jacobandersen.codechallenges.util;

public enum Direction {
    NORTH,
    EAST,
    WEST,
    SOUTH;

    public Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case SOUTH -> NORTH;
        };
    }
}
