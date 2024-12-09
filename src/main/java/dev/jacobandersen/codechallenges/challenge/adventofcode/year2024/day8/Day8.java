package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day8;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;
import dev.jacobandersen.codechallenges.util.Grid;
import dev.jacobandersen.codechallenges.util.MathUtil;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 extends Day {
    public Day8() {
        super(2024, 8, "Resonant Collinearity");
    }

    private Grid<String> loadGrid() {
        return Grid.create(getInputLinesStreamNoBlanks(), str -> new ArrayList<>(Arrays.asList(str.split(""))));
    }

    private Map<String, List<Pair<Integer, Integer>>> findAntennae(final Grid<String> grid) {
        final Map<String, List<Pair<Integer, Integer>>> antennae = new HashMap<>();

        grid.forEach(ctx -> {
            if (ctx.value().matches("[.#]")) return;
            antennae.compute(ctx.value(), (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }

                v.add(ctx.position());
                return v;
            });
        });

        return antennae;
    }

    private int countAntinodes(final Grid<String> grid, boolean partTwo) {
        final Set<Pair<Integer, Integer>> validAntinodes = new HashSet<>();

        findAntennae(grid).entrySet().stream()
                .filter(ent -> ent.getValue().size() >= 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach((key, value) -> CombinatoricsUtil.generateSublists(value, 2)
                        .forEach(currentAntennae -> {
                            final MathUtil.Slope slope = MathUtil.findSlope(currentAntennae.get(0), currentAntennae.get(1));
                            MathUtil.getIntegerPointsOnLine(grid, slope, currentAntennae).stream()
                                    .filter(pt -> partTwo || !currentAntennae.contains(pt))
                                    .forEach(potentialAntinode -> {
                                        if (partTwo) {
                                            validAntinodes.add(potentialAntinode);
                                        } else {
                                            double distanceFromFirst = MathUtil.distance(currentAntennae.get(0), potentialAntinode);
                                            double distanceFromSecond = MathUtil.distance(currentAntennae.get(1), potentialAntinode);
                                            double ratioOne = distanceFromFirst / distanceFromSecond;
                                            double ratioTwo = distanceFromSecond / distanceFromFirst;
                                            if (ratioOne == 0.5 || ratioTwo == 0.5) {
                                                validAntinodes.add(potentialAntinode);
                                            }
                                        }
                                    });
                        }));

        return validAntinodes.size();
    }

    @Override
    public String partOne() {
        return String.valueOf(countAntinodes(loadGrid(), false));
    }

    @Override
    public String partTwo() {
        return String.valueOf(countAntinodes(loadGrid(), true));
    }
}
