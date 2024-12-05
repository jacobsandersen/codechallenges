package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day1.Day1;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day2.Day2;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day3.Day3;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day5.Day5;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day6.Day6;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day7.Day7;

import java.util.List;

public class Year2015 {
    public static List<Day> getDays() {
        return List.of(
                new Day1(),
                new Day2(),
                new Day3(),
//              Omitted normally, Day 4 is very slow due to MD5 bruteforce requirement
//                new Day4(),
                new Day5(),
                new Day6(),
                new Day7()
        );
    }
}
