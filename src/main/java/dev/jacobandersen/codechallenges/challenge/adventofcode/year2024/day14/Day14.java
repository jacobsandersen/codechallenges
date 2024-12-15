package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day14;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Grid;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Day14 extends Day {
    private static final int ROWS = 103, COLS = 101;

    public Day14() {
        super(2024, 14, "Restroom Redoubt");
    }

    public record Robot(Pair<Integer, Integer> position, Pair<Integer, Integer> velocity) {}

    public List<Robot> loadRobots() {
        return getInputLinesNoBlanks().stream().map(line -> {
            final String[] parts = line.split(" ");
            final String pos = parts[0].substring(2), vel = parts[1].substring(2);
            return new Robot(Pair.fromStringPair(pos, Integer::parseInt).reverse(), Pair.fromStringPair(vel, Integer::parseInt).reverse());
        }).toList();
    }

    public Grid<List<Robot>> loadInitialState() {
        final Grid<List<Robot>> patrolArea = Grid.emptyGrid(ROWS, COLS, ArrayList::new);

        loadRobots().forEach(robot -> {
            List<Robot> lst = patrolArea.get(robot.position);
            lst.add(robot);

            patrolArea.set(robot.position, lst);
        });

        return patrolArea;
    }

    public Grid<List<Robot>> tickRobots(Grid<List<Robot>> in) {
        final Grid<List<Robot>> updatedGrid = Grid.emptyGrid(ROWS, COLS, ArrayList::new);

        in.forEach(robots -> {
            List<Robot> lst = robots.value();
            Iterator<Robot> itr = lst.iterator();
            while (itr.hasNext()) {
                final Robot robot = itr.next();
                final Pair<Integer, Integer> currentPos = robot.position();
                final Pair<Integer, Integer> velocity = robot.velocity();
                final Pair<Integer, Integer> newPosition = updatedGrid.locateWrappedPosition(currentPos.first() + velocity.first(), currentPos.second() + velocity.second());
                final List<Robot> currentRobots = updatedGrid.get(newPosition);
                currentRobots.add(new Robot(newPosition, velocity));
                updatedGrid.set(newPosition, currentRobots);
                itr.remove();
            }
        });

        return updatedGrid;
    }

    @Override
    public String partOne() {
        Grid<List<Robot>> room = loadInitialState();

        for (int i = 0; i < 100; i++) {
            room = tickRobots(room);
        }

        return String.valueOf(
                room.getQuadrants().stream()
                        .mapToInt(quad ->
                                quad.map(ctx -> ctx.value().size())
                                        .getBackingList().stream()
                                        .flatMap(Collection::stream)
                                        .reduce(0, Integer::sum)
                        )
                        .reduce(1, (a, b) -> a * b)
        );
    }

    @Override
    public String partTwo() {
        Grid<List<Robot>> room = loadInitialState();

        int seconds = 0;
        while (true) {
            seconds++;

            room = tickRobots(room);

            Grid<Integer> asRobotCounts = room.map(ctx -> ctx.value().size());
            boolean hasSharedPositions = asRobotCounts.getBackingList().stream().flatMap(Collection::stream).anyMatch(x -> x >= 2);

            if (hasSharedPositions) continue;

            for (List<Integer> integers : asRobotCounts.getBackingList()) {
                integers.forEach(System.out::print);
                System.out.println();
            }

            break;
        }

        return String.valueOf(seconds);
    }
}
