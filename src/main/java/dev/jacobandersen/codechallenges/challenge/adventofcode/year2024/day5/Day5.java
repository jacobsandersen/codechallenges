package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day5;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 extends Day {
    private final Map<Integer, List<Integer>> rules;
    private final List<List<Integer>> allUpdates;
    private final List<List<Integer>> validUpdates;
    private final List<List<Integer>> invalidUpdates;

    public Day5() {
        super(2024, 5, "Print Queue");
        var input = loadInput();
        rules = input.first();
        allUpdates = input.second();
        validUpdates = new ArrayList<>();
        invalidUpdates = new ArrayList<>();
    }

    public Pair<Map<Integer, List<Integer>>, List<List<Integer>>> loadInput() {
        final List<String> lines = getInputLines();
        final int sectionIdx = lines.indexOf("");

        final Map<Integer, List<Integer>> rules = new HashMap<>();
        for (String rule : lines.subList(0, sectionIdx)) {
            String[] split = rule.split("\\|");

            Integer key = Integer.parseInt(split[0]);
            Integer value = Integer.parseInt(split[1]);

            rules.compute(key, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add(value);
                return v;
            });
        }

        final List<List<Integer>> updates = new ArrayList<>();
        for (String update : lines.subList(sectionIdx + 1, lines.size())) {
            List<Integer> pages = new ArrayList<>();
            for (String page : update.split(",")) {
                pages.add(Integer.parseInt(page));
            }
            updates.add(pages);
        }

        return Pair.of(rules, updates);
    }

    @Override
    public String partOne() {
        for (List<Integer> update : allUpdates) {
            boolean valid = true;

            List<Integer> pagesPrinted = new ArrayList<>();

            for (Integer page : update) {
                final List<Integer> rule = rules.get(page);

                if (rule != null && pagesPrinted.stream().anyMatch(rule::contains)) {
                    valid = false;
                }

                pagesPrinted.add(page);
            }

            if (valid) {
                validUpdates.add(update);
            } else {
                invalidUpdates.add(update);
            }
        }

        return String.valueOf(validUpdates.stream().mapToInt(update -> update.get((update.size() - 1) / 2)).sum());
    }

    @Override
    public String partTwo() {
        for (List<Integer> update : invalidUpdates) {
            for (int i = update.size() - 1; i >= 0; i--) {
                final int page = update.get(i);

                final List<Integer> rule = rules.get(page);
                if (rule != null) {
                    for (int target : rule) {
                        final int targetIdx = update.indexOf(target);
                        if (targetIdx != -1 && targetIdx < i) {
                            final int tmp = update.get(targetIdx);
                            update.set(targetIdx, page);
                            update.set(i, tmp);
                            i++; // reconsider this page after swapping
                            break;
                        }
                    }
                }
            }
        }

        return String.valueOf(invalidUpdates.stream().mapToInt(update -> update.get((update.size() - 1) / 2)).sum());
    }
}
