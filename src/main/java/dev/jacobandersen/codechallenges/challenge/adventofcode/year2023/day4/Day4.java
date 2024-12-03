package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day4;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 extends Day {
    public Day4() {
        super(2023, 4, "Scratchcards");
    }

    private Map<Integer, CardSet> getCards() {
        return getInputLinesNoBlanks().stream().map(Card::create).collect(Collectors.toMap(card -> card.number, CardSet::new));
    }

    @Override
    public String partOne() {
        return String.valueOf(getCards().values().stream()
                .map(set -> set.card)
                .map(Card::getWinningNumbers)
                .map(winning -> (int) Math.pow(2, winning.size() - 1))
                .reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        Map<Integer, CardSet> cards = getCards();

        for (Map.Entry<Integer, CardSet> entry : cards.entrySet()) {
            CardSet set = entry.getValue();
            Set<Integer> winningNumbers = set.card.getWinningNumbers();

            for (int i = set.card.number + 1; i < set.card.number + 1 + winningNumbers.size(); i++) {
                for (int j = 0; j < set.count; j++) {
                    cards.get(i).incrementCount();
                }
            }
        }

        return String.valueOf(cards.values().stream().map(set -> set.count).reduce(0, Integer::sum));
    }

    private record Card(int number, Set<Integer> winning, Set<Integer> have) {
        static Card create(String line) {
            String[] split = line.split(":");
            String[] items = split[1].trim().split("\\|");
            int number = Integer.parseInt(split[0].replaceAll(" +", " ").split(" ")[1]);
            Set<Integer> winning = Arrays.stream(items[0].trim().replaceAll(" +", " ").split(" ")).map(Integer::parseInt).collect(Collectors.toSet());
            Set<Integer> have = Arrays.stream(items[1].trim().replaceAll(" +", " ").split(" ")).map(Integer::parseInt).collect(Collectors.toSet());
            return new Card(number, winning, have);
        }

        public Set<Integer> getWinningNumbers() {
            Set<Integer> intersection = new HashSet<>(have);
            intersection.retainAll(winning);
            return intersection;
        }
    }

    private static final class CardSet {
        private final Card card;
        private int count;

        public CardSet(Card card) {
            this.card = card;
            count = 1;
        }

        public void incrementCount() {
            count++;
        }
    }
}
