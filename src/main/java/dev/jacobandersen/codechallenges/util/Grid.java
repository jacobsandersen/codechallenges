package dev.jacobandersen.codechallenges.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid<T extends Comparable<T>> {
    private final int rows;
    private final int cols;
    private final List<List<T>> grid;
    private int currentRow;
    private int currentCol;

    public Grid(int rows, int cols, List<List<T>> grid) {
        this(rows, cols, 0, 0, grid);
    }

    public Grid(int rows, int cols, int currentRow, int currentCol, List<List<T>> grid) {
        this.rows = rows;
        this.cols = cols;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.grid = grid;
    }

    public static <T extends Comparable<T>> Grid<T> create(Stream<String> input, Function<String, List<T>> mapper) {
        final List<List<T>> grid = input.map(mapper).collect(Collectors.toList());
        if (!grid.stream().map(List::size).allMatch(size -> grid.get(0).size() == size)) {
            throw new IllegalArgumentException("Received grid had rows with varying columns - invalid");
        }

        return new Grid<>(grid.size(), grid.get(0).size(), grid);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public Pair<Integer, Integer> getCurrentPos() {
        return Pair.of(getCurrentRow(), getCurrentCol());
    }

    public T get() {
        return grid.get(currentRow).get(currentCol);
    }

    public Grid<T> set(T value) {
        grid.get(currentRow).set(currentCol, value);
        return this;
    }

    public Grid<T> move(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Tried to move current cell beyond boundaries");
        }

        currentRow = row;
        currentCol = col;

        return this;
    }

    public T peek(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }

        return grid.get(row).get(col);
    }

    public Grid<T> moveNorth() {
        move(currentRow - 1, currentCol);
        return this;
    }

    public T peekNorth() {
        return peek(currentRow - 1, currentCol);
    }

    public Grid<T> moveSouth() {
        move(currentRow + 1, currentCol);
        return this;
    }

    public T peekSouth() {
        return peek(currentRow + 1, currentCol);
    }

    public Grid<T> moveWest() {
        move(currentRow, currentCol - 1);
        return this;
    }

    public T peekWest() {
        return peek(currentRow, currentCol - 1);
    }

    public Grid<T> moveEast() {
        move(currentRow, currentCol + 1);
        return this;
    }

    public T peekEast() {
        return peek(currentRow, currentCol + 1);
    }

    public Grid<T> moveCellToFirstOccurrence(T needle) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (peek(row, col).equals(needle)) {
                    move(row, col);
                    return this;
                }
            }
        }

        throw new IllegalArgumentException("Needle not found in Grid.");
    }

    public Grid<T> move(Direction direction) {
        return switch (direction) {
            case NORTH -> moveNorth();
            case EAST -> moveEast();
            case WEST -> moveWest();
            case SOUTH -> moveSouth();
            default -> this;
        };
    }

    public T peek(Direction direction) {
        return switch (direction) {
            case NORTH -> peekNorth();
            case EAST -> peekEast();
            case WEST -> peekWest();
            case SOUTH -> peekSouth();
            default -> peek(currentRow, currentCol);
        };
    }

    public int[] peekPosition(Direction direction) {
        int[] position = new int[2];
        move(direction);
        position[0] = getCurrentRow();
        position[1] = getCurrentCol();
        move(direction.getOpposite());
        return position;
    }

    public Grid<T> copy() {
        List<List<T>> copy = new ArrayList<>(rows);

        for (int row = 0; row < rows; row++) {
            copy.add(new ArrayList<>(cols));

            for (int col = 0; col < cols; col++) {
                copy.get(row).add(grid.get(row).get(col));
            }
        }

        return new Grid<>(rows, cols, currentRow, currentCol, copy);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Grid {\n" +
                "  rows =" + rows + "\n" +
                "  cols =" + cols + "\n" +
                "  currentRow =" + currentRow + "\n" +
                "  currentCol =" + currentCol + "\n" +
                "  grid = {\n");

        grid.forEach(row -> builder.append("    ").append(row).append("\n"));

        builder.append("}");

        return builder.toString();
    }
}
