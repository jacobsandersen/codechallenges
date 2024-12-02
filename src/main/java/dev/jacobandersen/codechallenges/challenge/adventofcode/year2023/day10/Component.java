package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day10;

import dev.jacobandersen.codechallenges.util.Direction;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record Component(int y, int x, Type type) {
    public enum Type {
        NORTH_SOUTH('|', Set.of(Direction.NORTH, Direction.SOUTH)),
        EAST_WEST('-', Set.of(Direction.EAST, Direction.WEST)),
        NORTH_EAST('L', Set.of(Direction.NORTH, Direction.EAST)),
        NORTH_WEST('J', Set.of(Direction.NORTH, Direction.WEST)),
        SOUTH_WEST('7', Set.of(Direction.SOUTH, Direction.WEST)),
        SOUTH_EAST('F', Set.of(Direction.SOUTH, Direction.EAST)),
        GROUND('.', Collections.emptySet()),
        START('S', Set.of(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH));

        private final char symbol;
        private final Set<Direction> validDirections;

        Type(char symbol, Set<Direction> validDirections) {
            this.symbol = symbol;
            this.validDirections = validDirections;
        }

        public static Type fromSymbol(char symbol) {
            return Arrays.stream(values()).filter(value -> value.symbol == symbol).findFirst().orElseThrow();
        }

        public char getSymbol() {
            return symbol;
        }

        public Set<Direction> getValidDirections() {
            return validDirections;
        }

        public Direction getNextDirection(Direction from) {
            if (!getValidDirections().contains(from)) {
                return null;
            }

            return new HashSet<>(getValidDirections()).stream().filter(dir -> dir != from).findFirst().orElseThrow();
        }
    }
}
