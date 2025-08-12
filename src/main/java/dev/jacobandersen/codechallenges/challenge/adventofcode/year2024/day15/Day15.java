package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day15;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Direction;
import dev.jacobandersen.codechallenges.util.Grid;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day15 extends Day {
    private static final String ROBOT = "@";
    private static final String CRATE = "O";
    private static final String BIG_CRATE_LEFT = "[";
    private static final String BIG_CRATE_RIGHT = "]";
    private static final String WALL = "#";
    private static final String FREE = ".";

    public Day15() {
        super(2024, 15, "Warehouse Woes");
    }

    private Pair<Grid<String>, String[]> loadInput() {
        final List<String> raw = getInputLines();
        final int blankIdx = raw.indexOf("");

        return Pair.of(
                Grid.create(
                        raw.subList(0, blankIdx).stream(),
                        str -> new ArrayList<>(Arrays.stream(str.split("")).toList())
                ).moveCellToFirstOccurrence(ROBOT),
                String.join("", raw.subList(blankIdx + 1, raw.size())).split("")
        );
    }

    private Pair<Grid<String>, String[]> loadResizedInput() {
        var initial = loadInput();

        Grid<String> resizedGrid = Grid.emptyGrid(initial.first().getRows(), initial.first().getCols() * 2, () -> ".");
        for (int row = 0; row < resizedGrid.getRows(); row++) {
            for (int initialCol = 0, col = 0; initialCol < initial.first().getCols() && col < resizedGrid.getCols(); initialCol++, col += 2) {
                final String initialValue = initial.first().get(row, initialCol);
                switch (initialValue) {
                    case WALL -> {
                        resizedGrid.set(row, col, WALL);
                        resizedGrid.set(row, col + 1, WALL);
                    }
                    case FREE -> {
                        resizedGrid.set(row, col, FREE);
                        resizedGrid.set(row, col + 1, FREE);
                    }
                    case CRATE -> {
                        resizedGrid.set(row, col, BIG_CRATE_LEFT);
                        resizedGrid.set(row, col + 1, BIG_CRATE_RIGHT);
                    }
                    case ROBOT -> {
                        resizedGrid.set(row, col, ROBOT);
                        resizedGrid.set(row, col + 1, FREE);
                    }
                }
            }
        }

        return Pair.of(resizedGrid.moveCellToFirstOccurrence(ROBOT), initial.second());
    }

    private boolean canPush(Grid<String> grid, Direction direction, boolean part2) {
        int steps = 0;
        boolean canPush = false;

        while (grid.peek(direction).equals(CRATE)) {
            grid.move(direction);
            steps++;
        }

        if (grid.peek(direction).equals(FREE)) {
            canPush = true;
        }

        grid.move(direction.getOpposite(), steps);
        return canPush;
    }

    private void pushCrates(Grid<String> grid, Direction direction, boolean part2) {
        int steps = 0;

        while (grid.peek(direction).equals(CRATE)) {
            grid.move(direction);
            steps++;
        }

        for (int i = 0; i < steps; i++) {
            grid.set(direction, CRATE);
            grid.set(FREE);
            grid.move(direction.getOpposite());
        }
    }

    private void doMove(Grid<String> grid, Direction direction, boolean part2) {
        switch (grid.peek(direction)) {
            case WALL -> {
            }
            case FREE -> {
                grid.set(direction, ROBOT);
                grid.set(FREE);
                grid.move(direction);
            }
            case CRATE, BIG_CRATE_LEFT, BIG_CRATE_RIGHT -> {
                if (canPush(grid, direction, part2)) {
                    pushCrates(grid, direction, part2);
                    grid.set(direction, ROBOT);
                    grid.set(FREE);
                    grid.move(direction);
                }
            }
        }
    }

    private int sumCrateGpsCoords(Grid<String> grid) {
        AtomicInteger sum = new AtomicInteger();
        grid.forEach(ctx -> {
            if (ctx.value().equals(CRATE)) {
                sum.addAndGet((100 * ctx.position().first()) + (ctx.position().second()));
            }
        });
        return sum.get();
    }

    private List<Direction> translateInstructions(String[] instructions) {
        return Arrays.stream(instructions).map(inst -> switch(inst) {
            case "^":
                yield Direction.NORTH;
            case ">":
                yield Direction.EAST;
            case "v":
                yield Direction.SOUTH;
            case "<":
                yield Direction.WEST;
            default:
                throw new IllegalStateException("Unexpected instruction: " + inst);
        }).toList();
    }

    @Override
    public String partOne() {
        final var input = loadInput();

        final var grid = input.first();
        translateInstructions(input.second()).forEach(inst -> doMove(grid, inst, false));

        return String.valueOf(sumCrateGpsCoords(grid));
    }

    @Override
    public String partTwo() {
        final var input = loadResizedInput();

        final var grid = input.first();
        translateInstructions(input.second()).forEach(inst -> doMove(grid, inst, true));

        return "";
    }
}
