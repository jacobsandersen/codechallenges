package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.List;

public class Year2023 {
    public static List<Day> getDays() {
        return List.of(
                new Day1(),
                new Day2(),
                new Day3(),
                new Day4(),
                new Day5()
        );
    }
}
