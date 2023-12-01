package dev.jacobandersen.codechallenges.challenge.adventofcode.year2022;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day1 extends Day {
    public Day1() {
        super(2022, 1, "Calorie Counting");
    }

    private Stream<Integer> getCalorieTotals() {
        List<List<String>> separated = new ArrayList<>();
        List<String> tmp = new ArrayList<>();

        for (String str : getInputLines()) {
            if (str.isBlank()) {
                if (!tmp.isEmpty()) {
                    separated.add(tmp);
                    tmp = new ArrayList<>();
                }

                continue;
            }

            tmp.add(str);
        }

        return separated.stream().map(
                lst -> lst.stream()
                        .map(Integer::parseInt)
                        .reduce(0, Integer::sum));
    }

    @Override
    public String partOne() {
        return String.valueOf(getCalorieTotals().max(Integer::compareTo).orElseThrow());
    }

    @Override
    public String partTwo() {
        return String.valueOf(getCalorieTotals().sorted(Comparator.reverseOrder()).limit(3).reduce(0, Integer::sum));
    }
}
