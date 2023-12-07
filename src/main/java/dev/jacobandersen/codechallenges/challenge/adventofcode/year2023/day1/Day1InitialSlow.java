package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day1;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.HashMap;
import java.util.Map;

public class Day1InitialSlow extends Day {
    private static final Map<String, String> name2digit = new HashMap<>();

    static {
        name2digit.put("one", "1");
        name2digit.put("two", "2");
        name2digit.put("three", "3");
        name2digit.put("four", "4");
        name2digit.put("five", "5");
        name2digit.put("six", "6");
        name2digit.put("seven", "7");
        name2digit.put("eight", "8");
        name2digit.put("nine", "9");
    }

    private String findNumberInString(String substring, boolean considerWords) {
        try {
            Integer.parseInt(substring);
            return substring;
        } catch (NumberFormatException ex) {
            if (considerWords) {
                return name2digit.get(substring);
            }

            return null;
        }
    }

    private int firstForwardMatch(String str, boolean considerWords) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {
                String maybeMatch = findNumberInString(str.substring(i, j + 1), considerWords);
                if (maybeMatch != null) {
                    return Integer.parseInt(maybeMatch);
                }
            }
        }

        throw new IllegalStateException("Didn't find a match. Invalid string.");
    }

    private int firstReverseMatch(String str, boolean considerWords) {
        for (int i = str.length() - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                String maybeMatch = findNumberInString(str.substring(j, i + 1), considerWords);
                if (maybeMatch != null) {
                    return Integer.parseInt(maybeMatch);
                }
            }
        }

        throw new IllegalStateException("Didn't find a match. Invalid string.");
    }

    private int getDigitFromString(String str, boolean considerWords) {
        int first = firstForwardMatch(str, considerWords);
        int last = firstReverseMatch(str, considerWords);
        return Integer.parseInt(String.format("%d%d", first, last));
    }

    private int solution(boolean considerWords) {
        return getInputLinesStream()
                .filter(line -> !line.isBlank())
                .map(line -> getDigitFromString(line, considerWords))
                .reduce(0, Integer::sum);
    }

    public Day1InitialSlow() {
        super(2023, 1, "Trebuchet");
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
