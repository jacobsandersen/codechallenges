package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day5;

import java.util.Arrays;
import java.util.List;

public record MapEntry(long destinationStart, long destinationEnd, long sourceStart, long sourceEnd) {
    static MapEntry create(String line) {
        List<Long> parts = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
        return new MapEntry(parts.get(0), parts.get(0) + parts.get(2) - 1, parts.get(1), parts.get(1) + parts.get(2) - 1);
    }
}
