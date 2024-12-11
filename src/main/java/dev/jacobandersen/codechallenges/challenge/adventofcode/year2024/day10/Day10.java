package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day10;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Direction;
import dev.jacobandersen.codechallenges.util.Grid;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.*;

public class Day10 extends Day {
    public Day10() {
        super(2024, 10, "Hoof It");
    }

    public void followTrail(Grid<Integer> map, Trail parent) {
        for (Direction direction : Direction.cardinalValues()) {
            Pair<Integer, Integer> coord = Pair.of(
                    parent.pos.first() + direction.relativeCoordinate().first(),
                    parent.pos.second() + direction.relativeCoordinate().second()
            );

            Integer peek = map.peek(coord);
            if (peek != null && peek == parent.value + 1) {
                Trail child = new Trail(peek, coord);
                parent.addChild(child);
                followTrail(map.copy().move(coord), child);
            }
        }
    }

    public int score(Trail trail, Set<Pair<Integer, Integer>> visited) {
        if (visited.contains(trail.pos)) return 0;

        int score = trail.value == 9 ? 1 : 0;
        visited.add(trail.pos);

        for (Trail child : trail.children) {
            score += score(child, visited);
        }

        return score;
    }

    public int rating(Trail trail) {
        int rating = trail.value == 9 ? 1 : 0;

        for (Trail child : trail.children) {
            rating += rating(child);
        }

        return rating;
    }

    List<Trail> getTrailheads() {
        Grid<Integer> map = Grid.create(
                getInputLinesStreamNoBlanks(),
                str -> Arrays.stream(str.split("")).map(Integer::parseInt).toList()
        );

        List<Trail> trailheads = new ArrayList<>();

        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {
                int value = map.move(row, col).get();
                if (value != 0) continue;
                Trail head = new Trail(value, new Pair<>(row, col));
                followTrail(map, head);
                trailheads.add(head);
            }
        }

        return trailheads;
    }

    @Override
    public String partOne() {
        return String.valueOf(getTrailheads().stream().mapToInt(trailhead -> score(trailhead, new HashSet<>())).sum());
    }

    @Override
    public String partTwo() {
        return String.valueOf(getTrailheads().stream().mapToInt(this::rating).sum());
    }

    public static class Trail {
        private final int value;
        private final Pair<Integer, Integer> pos;
        private final Set<Trail> children;

        public Trail(int value, Pair<Integer, Integer> pos) {
            this.value = value;
            this.pos = pos;
            children = new HashSet<>();
        }

        public int getValue() {
            return value;
        }

        public void addChild(Trail child) {
            children.add(child);
        }
    }
}
