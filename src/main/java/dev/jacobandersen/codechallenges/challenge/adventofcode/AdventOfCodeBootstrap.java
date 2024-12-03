package dev.jacobandersen.codechallenges.challenge.adventofcode;

import dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.Year2015;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.Year2024;

public class AdventOfCodeBootstrap {
    public static void main(String[] args) {
        new AdventOfCodeBootstrap().run();
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
//        Year2024.getDays().forEach(Day::run);
    }

    private static class Dummy {
        void m() {
        }
    }
}
