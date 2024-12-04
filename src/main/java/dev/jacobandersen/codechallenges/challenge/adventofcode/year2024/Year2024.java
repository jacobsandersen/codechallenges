package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day1.Day1;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day2.Day2;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day3.Day3;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day4.Day4;

import java.util.List;

public class Year2024 {
    public static List<Day> getDays() {
        return List.of(
                new Day1(),
                new Day2(),
                new Day3(),
                new Day4()
        );
    }
}
