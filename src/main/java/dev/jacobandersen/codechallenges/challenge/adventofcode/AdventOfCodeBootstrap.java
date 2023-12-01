package dev.jacobandersen.codechallenges.challenge.adventofcode;

import dev.jacobandersen.codechallenges.challenge.adventofcode.year2020.Year2020;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2022.Year2022;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.Year2023;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;

import java.util.List;

public class AdventOfCodeBootstrap {
    public static void main(String[] args) {
        new AdventOfCodeBootstrap().run();
    }

    public void run() {
        Year2020.getDays().forEach(Day::run);
        Year2022.getDays().forEach(Day::run);
        Year2023.getDays().forEach(Day::run);
    }
}
