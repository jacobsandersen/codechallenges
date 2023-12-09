package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day9;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 extends Day {
    public Day9() {
        super(2023, 9, "Mirage Maintenance");
    }

    private List<List<Long>> getReadings() {
        return getInputLinesStreamNoBlanks().map(line -> line.split(" ")).map(parts -> Arrays.stream(parts).map(Long::parseLong).collect(Collectors.toList())).collect(Collectors.toList());
    }

    private List<List<Long>> generateDifferences(List<Long> reading) {
        List<List<Long>> differences = new ArrayList<>();
        differences.add(reading);

        while (true) {
            List<List<Long>> partition = CombinatoricsUtil.overlappingPartitionList(differences.get(differences.size() - 1), 2);
            List<Long> difference = new ArrayList<>();
            partition.forEach(entry -> difference.add(entry.get(1) - entry.get(0)));
            differences.add(difference);
            if (difference.stream().allMatch(entry -> entry == 0)) {
                break;
            }
        }

        return differences;
    }

    private long generateForwardPrediction(List<Long> reading) {
        List<List<Long>> differences = generateDifferences(reading);
        List<Long> first = differences.get(0);

        int numRows = differences.size();
        for (int i = numRows - 1; i >= 0; i--) {
            List<Long> difference = differences.get(i);
            if (i == numRows - 1) {
                difference.add(0L);
            } else {
                List<Long> previous = differences.get(i + 1);
                difference.add(difference.get(difference.size() - 1) + previous.get(previous.size() - 1));
            }
        }

        return first.get(first.size() - 1);
    }

    private long generateBackwardExtrapolation(List<Long> reading) {
        List<List<Long>> differences = generateDifferences(reading);

        int numRows = differences.size();
        for (int i = numRows - 1; i >= 0; i--) {
            List<Long> difference = differences.get(i);
            if (i == numRows - 1) {
                difference.add(0, 0L);
            } else {
                List<Long> previous = differences.get(i + 1);
                difference.add(0, difference.get(0) - previous.get(0));
            }
        }

        return differences.get(0).get(0);
    }

    @Override
    public String partOne() {
        return String.valueOf(getReadings().stream().map(this::generateForwardPrediction).reduce(0L, Long::sum));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getReadings().stream().map(this::generateBackwardExtrapolation).reduce(0L, Long::sum));
    }
}
