package dev.jacobandersen.codechallenges.challenge.adventofcode;

import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.Year2015;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2020.Year2020;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2022.Year2022;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.Year2023;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;

import java.util.List;

public class AdventOfCodeBootstrap {
    public static void main(String[] args) {
        new AdventOfCodeBootstrap().run();
    }

    private static class Dummy {
        void m() {}
    }

    public void run() {
        // jvm warmup
        for (int i = 0; i < 1_000_000; i++) {
            new Dummy().m();
        }

        Year2015.getDays().forEach(Day::run);
//        Year2020.getDays().forEach(Day::run);
//        Year2022.getDays().forEach(Day::run);
//        Year2023.getDays().forEach(Day::run);
    }
}
