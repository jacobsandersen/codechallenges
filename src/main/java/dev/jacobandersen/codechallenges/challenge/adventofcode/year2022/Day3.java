package dev.jacobandersen.codechallenges.challenge.adventofcode.year2022;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 extends Day {
    public Day3() {
        super(2022, 3, "Rucksack Reorganization");
    }

    private Set<Character> stringToCharSet(String str) {
        Set<Character> charSet = new HashSet<>();
        for (char c : str.toCharArray()) {
            charSet.add(c);
        }
        return charSet;
    }

    @Override
    public String partOne() {
        return String.valueOf(getInputLinesStream()
                .filter(line -> !line.isBlank())
                .map(line -> List.of(stringToCharSet(line.substring(0, line.length()/2)), stringToCharSet(line.substring(line.length()/2))))
                .flatMap(sets -> {
                    Set<Character> firstHalf = sets.get(0);
                    firstHalf.retainAll(sets.get(1));
                    return firstHalf.stream();
                })
                .map(item -> {
                    if (item > 97) {
                        return item - 96; // in ASCII range 97-122
                    } else {
                        return item - 38; // in ASCII range 65-90
                    }
                })
                .reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        List<String> inputLines = getInputLinesStream().filter(line -> !line.isBlank()).toList();

        List<List<String>> groups = new ArrayList<>();
        for (int i = 0; i < inputLines.size(); i+=3) {
            groups.add(List.of(inputLines.get(i), inputLines.get(i + 1), inputLines.get(i + 2)));
        }

        return String.valueOf(groups.stream()
                .map(group -> group.stream().map(this::stringToCharSet).toList())
                .flatMap(sets -> {
                    Set<Character> intersection = new HashSet<>(sets.get(0));
                    intersection.retainAll(sets.get(1));
                    intersection.retainAll(sets.get(2));
                    return intersection.stream();
                })
                .map(item -> {
                    if (item > 97) {
                        return item - 96; // in ASCII range 97-122
                    } else {
                        return item - 38; // in ASCII range 65-90
                    }
                })
                .reduce(0, Integer::sum));
    }
}
