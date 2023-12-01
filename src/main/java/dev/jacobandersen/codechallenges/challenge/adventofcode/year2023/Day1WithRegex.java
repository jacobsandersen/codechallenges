package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1WithRegex extends Day {
    private static final Pattern part1PatternSingle = Pattern.compile(".*?(\\d).*");
    private static final Pattern part1PatternBoth = Pattern.compile(".*?(\\d).*(\\d).*");
    private static final Pattern part2PatternSingle = Pattern.compile(".*?(\\d|one|two|three|four|five|six|seven|eight|nine).*");
    private static final Pattern part2PatternBoth = Pattern.compile(".*?(\\d|one|two|three|four|five|six|seven|eight|nine).*(\\d|one|two|three|four|five|six|seven|eight|nine).*");
    private static final Map<String, Integer> lookupTable = new HashMap<>();

    static {
        lookupTable.put("1", 1);
        lookupTable.put("one", 1);
        lookupTable.put("2", 2);
        lookupTable.put("two", 2);
        lookupTable.put("3", 3);
        lookupTable.put("three", 3);
        lookupTable.put("4", 4);
        lookupTable.put("four", 4);
        lookupTable.put("5", 5);
        lookupTable.put("five", 5);
        lookupTable.put("6", 6);
        lookupTable.put("six", 6);
        lookupTable.put("7", 7);
        lookupTable.put("seven", 7);
        lookupTable.put("8", 8);
        lookupTable.put("eight", 8);
        lookupTable.put("9", 9);
        lookupTable.put("nine", 9);
    }

    private int getDigitFromString(String str, boolean considerWords) {
        Matcher matcher = considerWords ? part2PatternBoth.matcher(str) : part1PatternBoth.matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(String.format(
                    "%d%d",
                    lookupTable.get(matcher.group(1)),
                    lookupTable.get(matcher.group(2)))
            );
        } else {
            matcher = considerWords ? part2PatternSingle.matcher(str) : part1PatternSingle.matcher(str);
            if (matcher.find()) {
                return Integer.parseInt(String.format(
                        "%d%d",
                        lookupTable.get(matcher.group(1)),
                        lookupTable.get(matcher.group(1)))
                );
            } else {
                throw new IllegalStateException("String did not match pattern - invalid input");
            }
        }
    }

    private int solution(boolean considerWords) {
        return getInputLinesStream()
                .filter(line -> !line.isBlank())
                .map(line -> getDigitFromString(line, considerWords))
                .reduce(0, Integer::sum);
    }

    public Day1WithRegex() {
        super(2023, 1, "Trebuchet (with Regex)");
    }

    @Override
    public String partOne() {
        return String.valueOf(solution(false));
    }

    @Override
    public String partTwo() {
        return String.valueOf(solution(true));
    }
}
