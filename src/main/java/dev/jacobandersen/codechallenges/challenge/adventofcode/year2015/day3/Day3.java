package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day3;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day5.Map;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 extends Day {
    public Day3() {
        super(2015, 3, "Perfectly Spherical Houses in a Vacuum");
    }

    private final Set<Pair<Integer, Integer>> visitedCoordinates = new HashSet<>();

    private Pair<Integer, Integer> processInstruction(Pair<Integer, Integer> start, String instruction) {
        return switch (instruction) {
            case "^":
                yield new Pair<>(start.first(), start.second() + 1);
            case "v":
                yield new Pair<>(start.first(), start.second() - 1);
            case ">":
                yield new Pair<>(start.first() + 1, start.second());
            case "<":
                yield new Pair<>(start.first() - 1, start.second());
            default:
                yield start;
        };
    }

    @Override
    public String partOne() {
        visitedCoordinates.clear();

        Pair<Integer, Integer> last = new Pair<>(0, 0);
        visitedCoordinates.add(last);

        for (String instruction : getInputLinesNoBlanks().get(0).split("")) {
            last = processInstruction(last, instruction);
            visitedCoordinates.add(last);
        }

        return String.valueOf(visitedCoordinates.size());
    }

    @Override
    public String partTwo() {
        visitedCoordinates.clear();

        Pair<Integer, Integer> santaLast = new Pair<>(0, 0);
        Pair<Integer, Integer> robotLast = new Pair<>(0, 0);
        visitedCoordinates.addAll(List.of(santaLast, robotLast)); // for consistency

        String[] instructions = getInputLinesNoBlanks().get(0).split("");
        for (int i = 0; i < instructions.length; i+=2) {
            santaLast = processInstruction(santaLast, instructions[i]);
            robotLast = processInstruction(robotLast, instructions[i + 1]);
            visitedCoordinates.addAll(List.of(santaLast, robotLast));
        }

        return String.valueOf(visitedCoordinates.size());
    }
}
