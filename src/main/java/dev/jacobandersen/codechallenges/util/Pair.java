package dev.jacobandersen.codechallenges.util;

import java.util.function.Function;

public record Pair<T, R>(T first, R second) {
    public static <T, R> Pair<T, R> of(T first, R second) {
        return new Pair<>(first, second);
    }

    public static <T> Pair<T, T> fromStringPair(String pair, Function<String, T> mapper) {
        final String[] parts = pair.split(",");
        return Pair.of(mapper.apply(parts[0]), mapper.apply(parts[1]));
    }

    public Pair<R, T> reverse() {
        return Pair.of(second(), first());
    }

    public Pair<T, R> copy() {
        return Pair.of(first, second);
    }
}
