package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day6;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 extends Day {
    public Day6() {
        super(2023, 6, "Wait For It");
    }

    public List<Race> getRaces() {
        List<Race> races = new ArrayList<>();

        List<String> data = getInputLinesNoBlanks();
        List<Long> times = Arrays.stream(data.get(0).split(": ")[1].trim().split(" ")).filter(part -> !part.isBlank()).map(Long::parseLong).toList();
        List<Long> distances = Arrays.stream(data.get(1).split(": ")[1].trim().split(" ")).filter(part -> !part.isBlank()).map(Long::parseLong).toList();

        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }

        return races;
    }

    public Race getRaceNoKerning() {
        List<String> data = getInputLinesNoBlanks();
        long time = Long.parseLong(data.get(0).split(": ")[1].trim().replaceAll(" ", ""));
        long distance = Long.parseLong(data.get(1).split(": ")[1].trim().replaceAll(" ", ""));
        return new Race(time, distance);
    }

    private int calculateWaysToWin(Race race) {
        int numWays = 0;

        for (long i = 1; i < race.time(); i++) {
            long distance = i * (race.time() - i);
            if (distance > race.distance()) {
                numWays++;
            }
        }

        return numWays;
    }

    @Override
    public String partOne() {
        return String.valueOf(getRaces().stream().map(this::calculateWaysToWin).reduce(1, (x, y) -> x * y));
    }

    @Override
    public String partTwo() {
        return String.valueOf(calculateWaysToWin(getRaceNoKerning()));
    }
}
