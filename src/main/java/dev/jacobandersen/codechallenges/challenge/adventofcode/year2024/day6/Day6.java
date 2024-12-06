package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day6;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Direction;
import dev.jacobandersen.codechallenges.util.Grid;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.*;

public class Day6 extends Day {
    public Day6() {
        super(2024, 6, "Guard Gallivant");
    }

    private List<List<String>> loadInput() {
        return new ArrayList<>(getInputLinesStreamNoBlanks().map(line -> new ArrayList<>(Arrays.stream(line.split("")).toList())).toList());
    }

    private Grid<String> makeGrid(List<List<String>> input) {
        return new Grid<>(input.size(), input.get(0).size(), input);
    }

    private int simulate(Grid<String> grid, boolean detectLoop) {
        grid = grid.moveCellToFirstOccurrence("^");

        Direction currentDirection = Direction.NORTH;

        final Set<GuardState> visitedStates = new HashSet<>();
        visitedStates.add(new GuardState(grid.getCurrentPos(), currentDirection));

        while (true) {
            String ahead = grid.peek(currentDirection);
            if (ahead == null) break;
            else while (ahead.equals("#")) {
                currentDirection = currentDirection.getCardinalClockwiseAdjacent();
                ahead = grid.peek(currentDirection);
            }

            try {
                grid.move(currentDirection);
            } catch (IllegalArgumentException ex) {
                break;
            }

            GuardState currentState = new GuardState(grid.getCurrentPos(), currentDirection);
            if (detectLoop && visitedStates.contains(currentState)) {
                return 1;
            } else {
                visitedStates.add(currentState);
            }
        }

        return detectLoop ? 0 : (int) visitedStates.stream().map(state -> state.pos).distinct().count();
    }

    @Override
    public String partOne() {
        return String.valueOf(simulate(makeGrid(loadInput()), false));
    }

    @Override
    public String partTwo() {
        int loops = 0;

        Grid<String> grid = makeGrid(loadInput());

        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                Grid<String> local = grid.copy().move(row, col);
                if (local.get().matches("[\\^#]")) continue;
                local.set("#");

                if (simulate(local, true) == 1) loops++;
            }
        }

        return String.valueOf(loops);
    }

    record GuardState(Pair<Integer, Integer> pos, Direction direction) {
    }
}
