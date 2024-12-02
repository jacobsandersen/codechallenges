package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day2;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;
import dev.jacobandersen.codechallenges.util.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day2 extends Day {
    public Day2() {
        super(2024, 2, "Red-Nosed Reports");
    }

    Stream<Report> getReports() {
        return getInputLinesStreamNoBlanks()
                .mapMulti((line, consumer) -> {
                    final List<Integer> levels = new ArrayList<>();
                    for (String part : line.split(" ")) {
                        levels.add(Integer.parseInt(part));
                    }

                    consumer.accept(new Report(levels));
                });
    }

    @Override
    public String partOne() {
        return String.valueOf(getReports().filter(Report::isSafe).count());
    }

    @Override
    public String partTwo() {
        return String.valueOf(getReports().filter(rpt -> rpt.isSafe() || rpt.isSafeIfDampened()).count());
    }

    record Report(List<Integer> levels) {
        boolean adjacentDifferencesWithinLimits() {
            return CombinatoricsUtil.overlappingPartitionList(levels, 2)
                    .stream()
                    .allMatch(partition -> {
                        int difference = Math.abs(partition.get(0) - partition.get(1));
                        return difference >= 1 && difference <= 3;
                    });
        }

        boolean isSafe() {
            return MathUtil.isMonotonic(levels) && adjacentDifferencesWithinLimits();
        }

        boolean isSafeIfDampened() {
            return CombinatoricsUtil.generateSublists(levels, levels.size() - 1).stream().anyMatch(lst -> new Report(lst).isSafe());
        }
    }
}
