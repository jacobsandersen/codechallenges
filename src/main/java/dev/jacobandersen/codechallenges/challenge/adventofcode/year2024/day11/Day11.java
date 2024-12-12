package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day11;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.MathUtil;

import java.util.*;

public class Day11 extends Day {
    public Day11() {
        super(2024, 11, "Plutonian Pebbles");
    }

    private Map<Long, Long> getInitialCounts() {
        final List<Long> input = getInputLinesStreamNoBlanks()
                .flatMap(str -> Arrays.stream(str.split(" ")).map(Long::parseLong))
                .toList();

        final Map<Long, Long> counts = new HashMap<>();

        for (final Long stone : input) {
            counts.merge(stone, 1L, Long::sum);
        }

        return counts;
    }

    private Map<Long, Long> processRules(final Map<Long, Long> counts) {
        final Map<Long, Long> out = new HashMap<>();

        for (final var entry : counts.entrySet()) {
            final Long stone = entry.getKey();
            final Long count = entry.getValue();

            if (stone == 0L) {
                out.merge(1L, count, Long::sum);
            } else {
                final int digits = MathUtil.countDigits(stone);
                if (digits % 2 == 0) {
                    for (final Long split : MathUtil.split(stone, digits)) {
                        out.merge(split, count, Long::sum);
                    }
                } else {
                    out.merge(stone * 2024, count, Long::sum);
                }
            }
        }

        return out;
    }

    private long toCount(final Map<Long, Long> counts) {
        return counts.values().stream().mapToLong(Long::longValue).sum();
    }

    private long getCounts(final int blinks) {
        Map<Long, Long> counts = getInitialCounts();

        if (blinks <= 0) return toCount(counts);

        for (int i = 0; i < blinks; i++) {
            counts = processRules(counts);
        }

        return toCount(counts);
    }

    @Override
    public String partOne() {
        return String.valueOf(getCounts(25));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getCounts(75));
    }
}
