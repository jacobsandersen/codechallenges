package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day2;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.RectangularCuboid;

import java.util.Arrays;
import java.util.List;

public class Day2 extends Day {
    public Day2() {
        super(2015, 2, "I Was Told There Would Be No Math");
    }

    private List<RectangularCuboid> getData() {
        return getInputLinesStreamNoBlanks()
                .map(line -> Arrays.stream(line.split("x")).map(Integer::parseInt).toList())
                .map(coords -> new RectangularCuboid(coords.get(0), coords.get(1), coords.get(2)))
                .toList();
    }

    @Override
    public String partOne() {
        return String.valueOf(getData().stream()
                .map(gift -> gift.surfaceArea() + gift.areaOfSmallestSide())
                .reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getData().stream()
                .map(gift -> gift.perimeterOfSmallestSide() + gift.volume())
                .reduce(0, Integer::sum));
    }
}
