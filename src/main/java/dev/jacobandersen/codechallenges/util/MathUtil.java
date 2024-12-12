package dev.jacobandersen.codechallenges.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MathUtil {
    public static long gcd(long a, long b) {
        if (a == 0 && b == 0) {
            return 0;
        } else if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }

    public static long lcm(long a, long b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    public static boolean isMonotonic(List<Integer> list) {
        return isMonotonic(list.toArray(new Integer[0]));
    }

    public static boolean isMonotonic(Integer[] arr) {
        if (arr.length <= 1) {
            return true;
        }

        int difference = 0;
        for (int i = 1; i < arr.length; i++) {
            int currDifference = arr[i] - arr[i - 1];
            if (currDifference * difference < 0) {
                return false;
            }
            difference = currDifference;
        }

        return true;
    }

    public static double distance(Pair<Integer, Integer> pointOne, Pair<Integer, Integer> pointTwo) {
        return Math.sqrt(Math.pow(pointOne.first() - pointTwo.first(), 2) + Math.pow(pointOne.second() - pointTwo.second(), 2));
    }

    public static int integerDistance(Pair<Integer, Integer> pointOne, Pair<Integer, Integer> pointTwo) {
        return (int) distance(pointOne, pointTwo);
    }

    public static Slope findSlope(Pair<Integer, Integer> pointOne, Pair<Integer, Integer> pointTwo) {
        return new Slope(pointOne.first() - pointTwo.first(), pointOne.second() - pointTwo.second());
    }

    public static <T extends Comparable<T>> Set<Pair<Integer, Integer>> getIntegerPointsOnLine(final Grid<T> grid, final Slope slope, final List<Pair<Integer, Integer>> known) {
        if (known.isEmpty()) throw new IllegalArgumentException("No known points provided, starting point unknown");

        final Set<Pair<Integer, Integer>> points = new HashSet<>(known);

        final Grid<T> tmp = grid.copy();

        tmp.move(known.get(0));
        while (true) {
            try {
                tmp.move(tmp.getCurrentRow() - slope.rise, tmp.getCurrentCol() - slope.run);
            } catch (IllegalArgumentException ex) {
                // Left the grid
                break;
            }

            points.add(tmp.getCurrentPos());
        }

        tmp.move(known.get(0));
        while (true) {
            try {
                tmp.move(tmp.getCurrentRow() + slope.rise, tmp.getCurrentCol() + slope.run);
            } catch (IllegalArgumentException ex) {
                // Left the grid
                break;
            }

            points.add(tmp.getCurrentPos());
        }

        return points;
    }

    public static int countDigits(final long in) {
        int length = 0;
        long temp = 1;

        while (temp <= in) {
            length++;
            temp *= 10;
        }

        return length;
    }

    public static List<Long> split(final long in, final long digits) {
        final long half = (long) Math.pow(10d, (double) digits / 2);
        return List.of(in / half, in % half);
    }

    public record Slope(int rise, int run) {}
}
