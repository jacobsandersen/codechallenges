package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand implements Comparable<Hand> {
    private static final int CARDS_PER_HAND = 5;
    private static final Map<Character, Integer> cardWeights = new HashMap<>();
    private static final Map<Character, Integer> modifiedCardWeights = new HashMap<>();

    static {
        char[] cards = {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
        for (int i = 0, j = 13; i < cards.length; i++, j--) {
            cardWeights.put(cards[i], j);
        }

        char[] modifiedCards = {'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'};
        for (int i = 0, j = 13; i < modifiedCards.length; i++, j--) {
            modifiedCardWeights.put(modifiedCards[i], j);
        }
    }

    private final char[] cards;
    private final int bid;
    private final Type type;
    private final int part;

    public Hand(final String data, final int part) {
        String[] cardsBidSplit = data.split(" ");
        cards = cardsBidSplit[0].toCharArray();
        bid = Integer.parseInt(cardsBidSplit[1]);

        Map<Character, Integer> cardCounts = new HashMap<>();
        for (char card : cards) {
            cardCounts.compute(card, (k, v) -> v == null ? 1 : v + 1);
        }

        List<Map.Entry<Character, Integer>> cardCountEntries = cardCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();

        Type tmpType = switch (cardCountEntries.get(0).getValue()) {
            case 5 -> Type.FIVE_OF_A_KIND;
            case 4 -> Type.FOUR_OF_A_KIND;
            case 3 -> {
                if (cardCountEntries.get(1).getValue() == 2) {
                    yield Type.FULL_HOUSE;
                } else {
                    yield Type.THREE_OF_A_KIND;
                }
            }
            case 2 -> {
                if (cardCountEntries.get(1).getValue() == 2) {
                    yield Type.TWO_PAIR;
                } else {
                    yield Type.ONE_PAIR;
                }
            }
            case 1 -> Type.HIGH_CARD;
            default -> throw new IllegalStateException("Encountered invalid hand type!");
        };

        if (part == 2) {
            Map.Entry<Character, Integer> jCardCount = cardCountEntries.stream().filter(entry -> entry.getKey().equals('J')).findFirst().orElse(null);
            if (jCardCount != null) {
                tmpType = switch (tmpType) {
                    case FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE -> {
                        // Currently five of a kind, stays five of kind.

                        // four of a kind possibilities:
                        // 4 Js, 1 other - Js merge with other to become five of a kind
                        // 4 other, 1 J - J merges with other to become five of a

                        // full house possibilities:
                        // 2 Js - majority is one card type. Both merge to five of a kind.
                        // 3 Js - they merge with minority to five of a kind.
                        yield Type.FIVE_OF_A_KIND;
                    }
                    case THREE_OF_A_KIND -> {
                        // three of a kind possibilities:
                        // 1 J, 1 other, 3 another - merge J with "another" to become four of a kind
                        // 3 J, 1 other, 1 another - merge Js with either to become four of a kind
                        yield Type.FOUR_OF_A_KIND;
                    }
                    case TWO_PAIR -> {
                        // two pair possibilities:
                        // 1 J, 2 other, 2 another - merge with either to become a full house
                        // 2 Js, 2 other, 1 another - merge with other to become a four of a kind
                        if (jCardCount.getValue() == 1) {
                            yield Type.FULL_HOUSE;
                        } else {
                            yield Type.FOUR_OF_A_KIND;
                        }
                    }
                    case ONE_PAIR -> {
                        // possibilities:
                        // 1 J, 2 paired, 1 other, 1 another - merge with paired to become three of a kind
                        // 2 J, 1 other, 1 another, 1 further - merge with any to become three of a kind
                        yield Type.THREE_OF_A_KIND;
                    }
                    case HIGH_CARD -> {
                        // possibilities:
                        // 1 J, 4 distinct others - J merges with any of them to become a two pair
                        yield Type.ONE_PAIR;
                    }
                };
            }
        }

        type = tmpType;

        this.part = part;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(Hand other) {
        Map<Character, Integer> weights = part == 1 ? cardWeights : modifiedCardWeights;

        if (type == other.type) {
            for (int i = 0; i < CARDS_PER_HAND; i++) {
                char myCard = cards[i];
                char otherCard = other.cards[i];
                if (myCard != otherCard) {
                    return Integer.compare(weights.get(myCard), weights.get(otherCard));
                }
            }

            return 0;
        }

        return Integer.compare(type.weight, other.type.weight);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + Arrays.toString(cards) +
                ", bid=" + bid +
                ", type=" + type +
                '}';
    }

    public enum Type {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        private final int weight;

        Type(int weight) {
            this.weight = weight;
        }
    }
}
