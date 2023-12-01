package dev.jacobandersen.codechallenges.challenge.adventofcode.year2020;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 extends Day {
    public Day6() {
        super(2020, 6, "Custom Customs");
    }

    private List<List<Set<Character>>> getGroups() {
        List<List<Set<Character>>> groups = new ArrayList<>();
        List<Set<Character>> currentGroup = new ArrayList<>();
        Set<Character> currentIndividual = new HashSet<>();

        for (String line : getInputLines()) {
            if (line.isBlank()) {
                if (!currentGroup.isEmpty()) {
                    groups.add(currentGroup);
                    currentGroup = new ArrayList<>();
                }

                continue;
            }

            char[] chars = line.toCharArray();
            for (char c : chars) {
                currentIndividual.add(c);
            }

            currentGroup.add(currentIndividual);
            currentIndividual = new HashSet<>();
        }

        return groups;
    }

    @Override
    public String partOne() {
        return String.valueOf(getGroups()
                .stream()
                .map(group -> {
                    Set<Character> out = new HashSet<>();
                    group.forEach(out::addAll);
                    return out;
                })
                .map(Set::size)
                .reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getGroups()
                .stream()
                .map(group -> {
                    Set<Character> out = new HashSet<>(group.get(0));
                    for (int i = 1; i < group.size(); i++) {
                        out.retainAll(group.get(i));
                    }
                    return out;
                })
                .map(Set::size)
                .reduce(0, Integer::sum));
    }
}
