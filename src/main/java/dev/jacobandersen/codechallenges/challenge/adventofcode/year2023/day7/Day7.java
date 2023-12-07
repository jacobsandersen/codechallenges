package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day7;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.List;

public class Day7 extends Day {
    public Day7() {
        super(2023, 7, "Camel Cards");
    }

    private int getWinnings(int part) {
        if (part < 1 || part > 2) {
            throw new IllegalArgumentException("Invalid part.");
        }

        List<Hand> hands = getInputLinesNoBlanks().stream().map(line -> new Hand(line, part)).sorted().toList();

        int winnings = 0;

        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
            winnings += hand.getBid() * (i + 1);
        }

        return winnings;
    }

    @Override
    public String partOne() {
        return String.valueOf(getWinnings(1));
    }

    @Override
    public String partTwo() {
        return String.valueOf(getWinnings(2));
    }
}
