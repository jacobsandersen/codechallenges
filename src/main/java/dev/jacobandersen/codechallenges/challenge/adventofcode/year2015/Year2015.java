package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day1.Day1;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day2.Day2;

import java.util.List;

public class Year2015 {
    public static List<Day> getDays() {
        return List.of(
                new Day1(),
                new Day2()
        );
    }
}
