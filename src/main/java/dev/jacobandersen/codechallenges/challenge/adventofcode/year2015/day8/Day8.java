package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day8;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 extends Day {
    private final Pattern backslashPattern = Pattern.compile("\\\\\\\\");
    private final Pattern quotePattern = Pattern.compile("\\\\\"");
    private final Pattern hexPattern = Pattern.compile("\\\\x([0-9A-Fa-f]{2})");

    public Day8() {
        super(2015, 8, "Matchsticks");
    }

    public String partOne() {
        return String.valueOf(getInputLinesStreamNoBlanks()
                .map(line -> {
                    String parsed = line.substring(1, line.length() - 1);

                    parsed = backslashPattern.matcher(parsed).replaceAll("B"); // idk why I can't do a single backslash, problem for later
                    parsed = quotePattern.matcher(parsed).replaceAll("\"");

                    final Matcher hexMatcher = hexPattern.matcher(parsed);
                    final StringBuilder hexReplResult = new StringBuilder();
                    while (hexMatcher.find()) {
                        String ascii = String.valueOf((char) Integer.parseInt(hexMatcher.group(1), 16));
                        hexMatcher.appendReplacement(hexReplResult, ascii);
                    }
                    hexMatcher.appendTail(hexReplResult);
                    parsed = hexReplResult.toString();

                    return line.length() - parsed.length();
                })
                .reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getInputLinesStreamNoBlanks()
                .map(line -> (StringEscapeUtils.escapeJava(line).length() + 2) - line.length())
                .reduce(0, Integer::sum));
    }
}
