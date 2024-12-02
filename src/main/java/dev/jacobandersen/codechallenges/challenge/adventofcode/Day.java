package dev.jacobandersen.codechallenges.challenge.adventofcode;

import dev.jacobandersen.codechallenges.Problem;

public abstract class Day extends Problem {
    private final int year;
    private final int day;
    private final String nickname;

    public Day(int year, int day, String nickname) {
        super("Advent of Code");
        this.year = year;
        this.day = day;
        this.nickname = nickname;
    }

    @Override
    public String getInputPath() {
        return String.format("%d/%d.txt", year, day);
    }

    public abstract String partOne();

    public abstract String partTwo();

    @Override
    public final void run() {
        long start = System.currentTimeMillis();
        System.out.printf("Year %d, Day %d - %s:%n", year, day, nickname);
        System.out.printf("| Part 1: %s ", partOne());
        long part1Elapsed = System.currentTimeMillis() - start;
        System.out.printf("(%dms)%n", part1Elapsed);
        System.out.printf("| Part 2: %s ", partTwo());
        long part2Elapsed = System.currentTimeMillis() - start - part1Elapsed;
        System.out.printf("(%dms)%n", part2Elapsed);
        System.out.printf("| Total time: %dms%n", part1Elapsed + part2Elapsed);
    }
}
