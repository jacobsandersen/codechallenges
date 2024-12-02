package dev.jacobandersen.codechallenges.util;

import java.util.List;

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
}
