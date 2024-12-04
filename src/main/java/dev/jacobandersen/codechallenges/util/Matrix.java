package dev.jacobandersen.codechallenges.util;

import java.lang.reflect.Array;
import java.util.*;

public class Matrix<T> {
    private final Class<T> type;
    private final T[][] matrix;

    public Matrix(Class<T> type, T[][] matrix) {
        this.type = type;
        this.matrix = matrix;
    }

    public List<List<T>> getRows() {
        List<List<T>> rows = new ArrayList<>();

        for (T[] row : matrix) {
            rows.add(Arrays.asList(row));
        }

        return rows;
    }

    public List<List<T>> getColumns() {
        return transpose().getRows();
    }

    public List<List<T>> getDiagonals() {
        List<List<T>> diagonals = new ArrayList<>();

        for (int x = 0; x < matrix[0].length; x++) {
            List<List<T>> partialDiags = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                partialDiags.add(new ArrayList<>());
            }

            // right, down
            for (int diagX = x, diagY = 0; diagX < matrix[0].length && diagY < matrix.length; diagX++, diagY++) {
                partialDiags.get(0).add(matrix[diagY][diagX]);
            }

            // left, down
            for (int diagX = x, diagY = 0; diagX >= 0 && diagY < matrix.length; diagX--, diagY++) {
                partialDiags.get(1).add(matrix[diagY][diagX]);
            }

            // right, up
            for (int diagX = x, diagY = matrix.length - 1; diagX < matrix[0].length && diagY >= 0; diagX++, diagY--) {
                partialDiags.get(2).add(matrix[diagY][diagX]);
            }

            // left, up
            for (int diagX = x, diagY = matrix.length - 1; diagX >= 0 && diagY < matrix.length; diagX--, diagY++) {
                partialDiags.get(3).add(matrix[diagY][diagX]);
            }

            diagonals.addAll(partialDiags);
        }

        return diagonals;
    }

    public Matrix<T> transpose() {
        @SuppressWarnings("unchecked")
        T[][] transposed = (T[][]) Array.newInstance(type, matrix[0].length, matrix.length);

        for (int y = 0; y < transposed.length; y++) {
            for (int x = 0; x < transposed[y].length; x++) {
                transposed[y][x] = matrix[x][y];
            }
        }

        return new Matrix<>(type, transposed);
    }
}
