package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day3;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends Day {
    private final Pattern validMulPattern;
    private final Pattern validCommandPattern;

    public Day3() {
        super(2024, 3, "Mull It Over");
        validMulPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        validCommandPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");
    }

    @Override
    public String partOne() {
        return String.valueOf(getInputLinesStreamNoBlanks().mapToLong(line -> {
            final Matcher matcher = validMulPattern.matcher(line);
            long lineSum = 0;
            while (matcher.find()) {
                lineSum += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
            }
            return lineSum;
        }).sum());
    }

    @Override
    public String partTwo() {
        long sum = 0;
        boolean mulEnabled = true;

        for (String line : getInputLinesNoBlanks()) {
            final Matcher matcher = validCommandPattern.matcher(line);
            while (matcher.find()) {
                final String substring = line.substring(matcher.start(), matcher.end());

                mulEnabled = switch (substring) {
                    case "do()":
                        yield true;
                    case "don't()":
                        yield false;
                    default:
                        yield mulEnabled;
                };

                if (mulEnabled && substring.startsWith("mul(")) {
                    sum += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
                }
            }
        }

        return String.valueOf(sum);
    }
}
