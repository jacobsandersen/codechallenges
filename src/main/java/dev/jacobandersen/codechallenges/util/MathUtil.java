package dev.jacobandersen.codechallenges.util;

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
}
