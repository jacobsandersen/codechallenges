package dev.jacobandersen.codechallenges.challenge.adventofcode.year2020;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;

import java.util.Collection;

public class Day1 extends Day {
    public Day1() {
        super(2020, 1, "Report Repair");
    }

    @Override
    public String partOne() {
        return String.valueOf(CombinatoricsUtil.generateSublists(getInputIntegers(), 2)
                .stream()
                .filter(pair -> pair.get(0) + pair.get(1) == 2020)
                .flatMap(Collection::stream)
                .reduce(1, (x, y) -> x * y));
    }

    @Override
    public String partTwo() {
        return String.valueOf(CombinatoricsUtil.generateSublists(getInputIntegers(), 3)
                .stream()
                .filter(pair -> pair.get(0) + pair.get(1) + pair.get(2) == 2020)
                .flatMap(Collection::stream)
                .reduce(1, (x, y) -> x * y));
    }
}
