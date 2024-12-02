package dev.jacobandersen.codechallenges.util;

public class Pair<T, R> {
    private final T first;
    private final R second;

    public Pair(T first, R second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public R second() {
        return second;
    }
}
