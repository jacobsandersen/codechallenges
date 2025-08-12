package dev.jacobandersen.codechallenges.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid<T> {
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

    public static <T> Grid<T> create(Stream<String> input, Function<String, List<T>> mapper) {
        final List<List<T>> grid = input.map(mapper).collect(Collectors.toList());
        if (!grid.stream().map(List::size).allMatch(size -> grid.get(0).size() == size)) {
            throw new IllegalArgumentException("Received grid had rows with varying columns - invalid");
        }

        return new Grid<>(grid.size(), grid.get(0).size(), grid);
    }

    public static <T> Grid<T> emptyGrid(int rows, int cols, Supplier<T> initializer) {
        final List<List<T>> grid = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < cols; j++) {
                grid.get(i).add(initializer.get());
            }
        }

        return new Grid<>(rows, cols, grid);
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

    public Grid<T> set(T value) {
        return set(currentRow, currentCol, value);
    }

    public Grid<T> set(Pair<Integer, Integer> pos, T value) {
        return set(pos.first(), pos.second(), value);
    }

    public Grid<T> set(Direction direction, T value) {
        Pair<Integer, Integer> rel = direction.relativeCoordinate();
        return set(currentRow + rel.first(), currentCol + rel.second(), value);
    }


    public Grid<T> set(int row, int col, T value) {
        grid.get(row).set(col, value);
        return this;
    }

    public Pair<Integer, Integer> locateWrappedPosition(int targetRow, int targetCol) {
        int wrappedRow = targetRow;
        int wrappedCol = targetCol;

        if (wrappedRow < 0) {
            wrappedRow = (wrappedRow % rows + rows) % rows;
        } else if (wrappedRow >= rows) {
            wrappedRow = wrappedRow % rows;
        }

        if (wrappedCol < 0) {
            wrappedCol = (wrappedCol % cols + cols) % cols;
        } else if (wrappedCol >= cols) {
            wrappedCol = wrappedCol % cols;
        }

        return Pair.of(wrappedRow, wrappedCol);
    }

    public T get() {
        return get(currentRow, currentCol);
    }

    public T getOrNull(Pair<Integer, Integer> pos) {
        return getOrNull(pos.first(), pos.second());
    }

    public T getOrNull(int row, int col) {
        try {
            return get(row, col);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public T get(Pair<Integer, Integer> pos) {
        return get(pos.first(), pos.second());
    }

    public T get(Direction direction) {
        Pair<Integer, Integer> rel = direction.relativeCoordinate();
        return get(currentRow + rel.first(), currentCol + rel.second());
    }

    public T get(int row, int col) {
        return grid.get(row).get(col);
    }

    public T peek(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }

        return grid.get(row).get(col);
    }

    public T peek(Pair<Integer, Integer> pos) {
        return peek(pos.first(), pos.second());
    }

    public Grid<T> move(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Tried to move current cell beyond boundaries");
        }

        currentRow = row;
        currentCol = col;

        return this;
    }

    public Grid<T> move(Pair<Integer, Integer> pos) {
        return move(pos.first(), pos.second());
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

    public Grid<T> move(Direction direction, int times) {
        for (int i = 0; i < times; i++) {
            move(direction);
        }
        return this;
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

    public T[] peek(Direction direction, int steps) {
        Pair<Integer, Integer> pos = getCurrentPos();

        @SuppressWarnings("unchecked")
        final T[] out = (T[]) new Object[steps];

        for (int i = 0; i < steps; i++) {
            T val = peek(direction);
            out[i] = val;
            if (val == null) break;
        }

        move(pos);
        return out;
    }

    public int[] peekPosition(Direction direction) {
        int[] position = new int[2];
        move(direction);
        position[0] = getCurrentRow();
        position[1] = getCurrentCol();
        move(direction.getOpposite());
        return position;
    }

    public void forEach(Consumer<PositionContext<T>> consumer) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                consumer.accept(new PositionContext<>(grid.get(row).get(col), new Pair<>(row, col)));
            }
        }
    }

    public <R extends Comparable<R>> Grid<R> map(Function<PositionContext<T>, R> mapper) {
        Grid<R> output = new Grid<>(rows, cols, new ArrayList<>());

        for (int row = 0; row < rows; row++) {
            output.grid.add(new ArrayList<>());
            for (int col = 0; col < cols; col++) {
                output.grid.get(row).add(mapper.apply(new PositionContext<>(grid.get(row).get(col), new Pair<>(row, col))));
            }
        }

        return output;
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

    public boolean contains(Pair<Integer, Integer> position) {
        return position.first() >= 0 && position.first() < rows && position.second() >= 0 && position.second() < cols;
    }

    public List<Grid<T>> getQuadrants() {
        int midRow = rows / 2, midCol = cols / 2;

        Grid<T> q1 = extractSubgrid(0, midRow, 0, midCol);
        Grid<T> q2 = extractSubgrid(0, midRow, midCol + 1, cols);
        Grid<T> q3 = extractSubgrid(midRow + 1, rows, 0, midCol);
        Grid<T> q4 = extractSubgrid(midRow + 1, rows, midCol + 1, cols);

        return List.of(q1, q2, q3, q4);
    }

    private Grid<T> extractSubgrid(int startRow, int endRow, int startCol, int endCol) {
        Grid<T> subgrid = Grid.emptyGrid(endRow - startRow, endCol - startCol, () -> null);
        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                subgrid.set(i - startRow, j - startCol, get(i, j));
            }
        }
        return subgrid;
    }

    public List<List<T>> getBackingList() {
        return grid;
    }

    public void printSimple() {
        getBackingList().forEach(lst -> {
            lst.forEach(System.out::print);
            System.out.println();
        });
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Grid {\n" +
                "\trows = " + rows + "\n" +
                "\tcols = " + cols + "\n" +
                "\tcurrentRow = " + currentRow + "\n" +
                "\tcurrentCol = " + currentCol + "\n" +
                "\tgrid = {\n");

        grid.forEach(row -> builder.append("\t\t").append(row).append("\n"));

        builder.append("\t}\n}");

        return builder.toString();
    }

    public record PositionContext<T>(T value, Pair<Integer, Integer> position) {}
}
