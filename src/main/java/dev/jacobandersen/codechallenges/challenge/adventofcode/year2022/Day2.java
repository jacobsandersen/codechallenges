package dev.jacobandersen.codechallenges.challenge.adventofcode.year2022;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day2 extends Day {
    public Day2() {
        super(2022, 2, "Rock Paper Scissors");
    }

    private Stream<String[]> getPreparedRounds() {
        return getInputLinesStream()
                .filter(str -> !str.isBlank())
                .map(round -> round.split(" "));
    }

    @Override
    public String partOne() {
        return String.valueOf(getPreparedRounds()
                .map(round -> getRoundScore(Choice.getByFirst(round[0]), Choice.getBySecond(round[1])))
                .reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getPreparedRounds()
                .map(round -> getRoundScore(Choice.getByFirst(round[0]), Choice.getByRequiredResult(Choice.getByFirst(round[0]), RoundResult.getByRequiredResult(round[1]))))
                .reduce(0, Integer::sum));
    }

    private int getRoundScore(Choice elfChoice, Choice myChoice) {
        int score = 0;

        switch (myChoice) {
            case ROCK -> score++;
            case PAPER -> score += 2;
            case SCISSORS -> score += 3;
        }

        switch (getRoundResult(elfChoice, myChoice)) {
            case WIN -> score += 6;
            case DRAW -> score += 3;
        }

        return score;
    }

    private RoundResult getRoundResult(Choice elfChoice, Choice myChoice) {
        switch (elfChoice) {
            case ROCK -> {
                switch (myChoice) {
                    case ROCK -> {
                        return RoundResult.DRAW;
                    }
                    case PAPER -> {
                        return RoundResult.WIN;
                    }
                    case SCISSORS -> {
                        return RoundResult.LOSS;
                    }
                }
            }
            case PAPER -> {
                switch (myChoice) {
                    case ROCK -> {
                        return RoundResult.LOSS;
                    }
                    case PAPER -> {
                        return RoundResult.DRAW;
                    }
                    case SCISSORS -> {
                        return RoundResult.WIN;
                    }
                }
            }
            case SCISSORS -> {
                switch (myChoice) {
                    case ROCK -> {
                        return RoundResult.WIN;
                    }
                    case PAPER -> {
                        return RoundResult.LOSS;
                    }
                    case SCISSORS -> {
                        return RoundResult.DRAW;
                    }
                }
            }
        }

        throw new IllegalStateException("Reached end of getRoundResult - cannot happen");
    }

    enum RoundResult {
        WIN("Z"),
        DRAW("Y"),
        LOSS("X");

        private final String requiredResult;

        RoundResult(String requiredResult) {
            this.requiredResult = requiredResult;
        }

        public static RoundResult getByRequiredResult(String requiredResult) {
            return Arrays.stream(values()).filter(result -> result.requiredResult.equals(requiredResult)).findFirst().orElseThrow();
        }
    }

    enum Choice {
        ROCK("A", "X"),
        PAPER("B", "Y"),
        SCISSORS("C", "Z");

        private final String first;
        private final String second;

        Choice(String first, String second) {
            this.first = first;
            this.second = second;
        }

        public static Choice getByFirst(String first) {
            return Arrays.stream(values()).filter(choice -> choice.first.equals(first)).findFirst().orElseThrow();
        }

        public static Choice getBySecond(String second) {
            return Arrays.stream(values()).filter(choice -> choice.second.equals(second)).findFirst().orElseThrow();
        }

        public static Choice getByRequiredResult(Choice firstChoice, RoundResult requiredResult) {
            switch (firstChoice) {
                case ROCK -> {
                    switch (requiredResult) {
                        case WIN -> {
                            return PAPER;
                        }
                        case DRAW -> {
                            return ROCK;
                        }
                        case LOSS -> {
                            return SCISSORS;
                        }
                    }
                }
                case PAPER -> {
                    switch (requiredResult) {
                        case WIN -> {
                            return SCISSORS;
                        }
                        case DRAW -> {
                            return PAPER;
                        }
                        case LOSS -> {
                            return ROCK;
                        }
                    }
                }
                case SCISSORS -> {
                    switch (requiredResult) {
                        case WIN -> {
                            return ROCK;
                        }
                        case DRAW -> {
                            return SCISSORS;
                        }
                        case LOSS -> {
                            return PAPER;
                        }
                    }
                }
            }

            throw new IllegalStateException("Reached end of getByRequiredResult - cannot happen");
        }
    }
}
