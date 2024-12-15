package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day1.Day1;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day10.Day10;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day11.Day11;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day12.Day12;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day13.Day13;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day14.Day14;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day2.Day2;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day3.Day3;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day4.Day4;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day5.Day5;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day8.Day8;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day9.Day9;

import java.util.List;

public class Year2024 {
    public static List<Day> getDays() {
        return List.of(
                new Day1(),
                new Day2(),
                new Day3(),
                new Day4(),
                new Day5(),
//                Day 6 is slow. I'll try to fix it later.
//                new Day6(),
//                Also slow. Problem for later.
//                new Day7(),
                new Day8(),
                new Day9(),
                new Day10(),
                new Day11(),
                new Day12(),
                new Day13()
//                Day 6 part 2 is slow due to slow update algorithm
//                new Day14()
        );
    }
}
