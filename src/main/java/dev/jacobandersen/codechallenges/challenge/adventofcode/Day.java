package dev.jacobandersen.codechallenges.challenge.adventofcode;

import dev.jacobandersen.codechallenges.Problem;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

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
        System.out.printf("Year %d, Day %d - %s:%n", year, day, nickname);
        System.out.printf("| Part 1: %s%n", partOne());
        System.out.printf("| Part 2: %s%n", partTwo());
    }
}
