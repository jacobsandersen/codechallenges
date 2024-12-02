package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day1;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day1 extends Day {
    public Day1() {
        super(2024, 1, "Historian Hysteria");
    }

    private Pair<List<Integer>, List<Integer>> getLists() {
        final List<Integer> a = new ArrayList<>(), b = new ArrayList<>();

        getInputLinesStreamNoBlanks()
                .map(line -> line.replaceAll(" +", " ").split(" "))
                .map(arr -> new Integer[] { Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) })
                .forEach(arr -> {
                    a.add(arr[0]);
                    b.add(arr[1]);
                });

        return new Pair<>(a, b);
    }

    @Override
    public String partOne() {
        final Pair<List<Integer>, List<Integer>> lists = getLists();

        lists.first().sort(Integer::compareTo);
        lists.second().sort(Integer::compareTo);

        final List<Integer> differences = new ArrayList<>();
        for (int i = 0; i < lists.first().size() && i < lists.second().size(); i++) {
            differences.add(Math.abs(lists.first().get(i) - lists.second().get(i)));
        }

        return String.valueOf(differences.stream().mapToInt(Integer::intValue).sum());
    }

    @Override
    public String partTwo() {
        final Pair<List<Integer>, List<Integer>> lists = getLists();

        return String.valueOf(
                lists.first().stream()
                        .mapToLong(leftNum ->
                                leftNum * lists.second()
                                        .stream()
                                        .filter(rightNum -> Objects.equals(rightNum, leftNum))
                                        .count()).sum()
        );
    }
}
