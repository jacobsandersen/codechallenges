package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day2 extends Day {
    public Day2() {
        super(2023, 2, "Cube Conundrum");
    }

    private Stream<Game> getGames() {
        return getInputLinesStream()
                .filter(line -> !line.isBlank())
                .map(desc -> {
                    String[] parts = desc.split(":");

                    Set<GameSet> sets = new HashSet<>();
                    for (String[] values : Arrays.stream(parts[1].split(";")).map(piece -> piece.split(",")).toList()) {
                        GameSet set = new GameSet();

                        for (String value : values) {
                            String[] countAndType = value.trim().split(" ");
                            int number = Integer.parseInt(countAndType[0]);

                            switch (countAndType[1]) {
                                case "red":
                                    set.red = number;
                                    break;
                                case "green":
                                    set.green = number;
                                    break;
                                case "blue":
                                    set.blue = number;
                                    break;
                            }
                        }

                        sets.add(set);
                    }

                    return new Game(Integer.parseInt(parts[0].split(" ")[1]), sets);
                });
    }

    @Override
    public String partOne() {
        final int numRed = 12;
        final int numGreen = 13;
        final int numBlue = 14;

        return String.valueOf(getGames()
                .filter(game -> game.sets().stream()
                        .allMatch(set -> set.red <= numRed
                                && set.green <= numGreen
                                && set.blue <= numBlue)
                ).map(Game::id)
                .reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getGames()
                .map(game -> game.sets)
                .map(sets -> {
                    int maxRed = sets.stream().map(set -> set.red).max(Integer::compareTo).orElseThrow();
                    int maxGreen = sets.stream().map(set -> set.green).max(Integer::compareTo).orElseThrow();
                    int maxBlue = sets.stream().map(set -> set.blue).max(Integer::compareTo).orElseThrow();
                    return maxRed * maxGreen * maxBlue;
                })
                .reduce(0, Integer::sum));
    }

    record Game(int id, Set<GameSet> sets) {}

    static class GameSet {
        private int red = 0;
        private int green = 0;
        private int blue = 0;
    }
}
