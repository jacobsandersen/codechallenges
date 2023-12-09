package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day2;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day2 extends Day {
    public Day2() {
        super(2015, 2, "I Was Told There Would Be No Math");
    }

    private List<List<Long>> getData() {
        return getInputLinesStreamNoBlanks().map(line -> Arrays.stream(line.split("x")).map(Long::parseLong).toList()).toList();
    }

    // Sorry, for what you are about to read. Yes it could be cleaner.
    // But, oh well. :D

    @Override
    public String partOne() {
        return String.valueOf(getData().stream()
                .map(gift -> (2 * gift.get(0) * gift.get(1)) + (2 * gift.get(1) * gift.get(2)) +
                        (2 * gift.get(2) * gift.get(0)) + Stream.of(List.of(gift.get(0), gift.get(1)),
                        List.of(gift.get(0), gift.get(2)), List.of(gift.get(1), gift.get(2)))
                        .map(part -> part.get(0) * part.get(1))
                        .min(Long::compareTo).orElseThrow())
                .reduce(0L, Long::sum));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getData().stream()
                .map(gift -> (gift.get(0) * gift.get(1) * gift.get(2)) +
                        Stream.of(List.of(gift.get(0), gift.get(1)), List.of(gift.get(0), gift.get(2)),
                                List.of(gift.get(1), gift.get(2)))
                                .map(part -> (2 * part.get(0)) + (2 * part.get(1))).min(Long::compareTo).orElseThrow())
                .reduce(0L, Long::sum));
    }
}
