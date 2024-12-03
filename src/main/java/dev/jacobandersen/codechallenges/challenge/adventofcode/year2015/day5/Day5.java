package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day5;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day5 extends Day {
    public Day5() {
        super(2015, 5, "Doesn't He Have Intern-Elves For This?");
    }

    private long countMatching(Stream<String> words, Pattern pattern) {
        return words.filter(word -> pattern.matcher(word).matches()).count();
    }

    @Override
    public String partOne() {
        return String.valueOf(countMatching(
                getInputLinesStreamNoBlanks(),
                Pattern.compile("^(?=(.*[aeiou]){3,})(?=.*(.)\\2)(?!.*(?:ab|cd|pq|xy)).*$")
        ));
    }

    @Override
    public String partTwo() {
        return String.valueOf(countMatching(
                getInputLinesStreamNoBlanks(),
                Pattern.compile("^(?=.*(..).*\\1)(?=.*(.).\\2).*$")
        ));
    }
}
