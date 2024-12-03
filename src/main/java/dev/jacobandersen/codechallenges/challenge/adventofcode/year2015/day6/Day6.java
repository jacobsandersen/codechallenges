package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day6;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 extends Day {
    private final Pattern instructionPattern = Pattern.compile("^(toggle|turn on|turn off) (\\d{1,3}),(\\d{1,3}) through (\\d{1,3}),(\\d{1,3})$");

    public Day6() {
        super(2015, 6, "Probably a Fire Hazard");
    }

    <T> void processInstructions(T[][] data, Function<LightInstructionContext<T>, T[][]> onToggle, Function<LightInstructionContext<T>, T[][]> onTurnOn, Function<LightInstructionContext<T>, T[][]> onTurnOff) {
        for (String line : getInputLinesNoBlanks()) {
            final Matcher matcher = instructionPattern.matcher(line);
            if (!matcher.find()) {
                System.out.println("instruction skipped (no match!): " + line);
                continue;
            }

            final Pair<Integer, Integer> origin = new Pair<>(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
            final Pair<Integer, Integer> target = new Pair<>(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));
            for (int x = origin.first(); x <= target.first(); x++) {
                for (int y = origin.second(); y <= target.second(); y++) {
                    final LightInstructionContext<T> ctx = new LightInstructionContext<>(data, new Pair<>(x, y));
                    switch (matcher.group(1)) {
                        case "toggle":
                            data = onToggle.apply(ctx);
                            break;
                        case "turn on":
                            data = onTurnOn.apply(ctx);
                            break;
                        case "turn off":
                            data = onTurnOff.apply(ctx);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public String partOne() {
        Boolean[][] lights = new Boolean[1000][1000];

        for (Boolean[] light : lights) {
            Arrays.fill(light, false);
        }

        processInstructions(
                lights,
                ctx -> ctx.operate(b -> !b),
                ctx -> ctx.operate(b -> true),
                ctx -> ctx.operate(b -> false)
        );

        return String.valueOf(Arrays.stream(lights).flatMap(Arrays::stream).filter(bool -> bool).count());
    }

    @Override
    public String partTwo() {
        Integer[][] lights = new Integer[1000][1000];

        for (Integer[] light : lights) {
            Arrays.fill(light, 0);
        }

        processInstructions(
                lights,
                ctx -> ctx.operate(i -> i + 2),
                ctx -> ctx.operate(i -> i + 1),
                ctx -> ctx.operate(i -> Math.max(i - 1, 0))
        );

        return String.valueOf(Arrays.stream(lights).flatMap(Arrays::stream).reduce(0, Integer::sum));
    }

    record LightInstructionContext<T>(T[][] data, Pair<Integer, Integer> coordinate) {
        T[][] operate(Function<T, T> operator) {
            data[coordinate.first()][coordinate.second()] = operator.apply(data[coordinate.first()][coordinate.second()]);
            return data;
        }
    }
}
