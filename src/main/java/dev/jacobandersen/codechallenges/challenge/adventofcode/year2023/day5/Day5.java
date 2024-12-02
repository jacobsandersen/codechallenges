package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day5;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;

import java.util.*;
import java.util.stream.LongStream;

public class Day5 extends Day {
    public Day5() {
        super(2023, 5, "If You Give A Seed A Fertilizer");
    }

    private List<List<String>> getComponents() {
        List<List<String>> components = new ArrayList<>();
        List<String> currentComponent = new ArrayList<>();
        getInputLines().forEach(line -> {
            if (line.isBlank()) {
                if (!currentComponent.isEmpty()) {
                    components.add(new ArrayList<>(currentComponent));
                    currentComponent.clear();
                }
            } else {
                currentComponent.add(line);
            }
        });
        return components;
    }

    private List<Long> getSeeds(List<List<String>> components) {
        return components.get(0)
                .stream()
                .limit(1)
                .flatMap(line -> Arrays.stream(line.split(": ")[1].trim().split(" ")))
                .map(Long::parseLong).toList();
    }

    private List<Map> getMaps(List<List<String>> components) {
        return components.stream()
                .skip(1)
                .map(Map::create)
                .toList();
    }

    @Override
    public String partOne() {
        final List<List<String>> components = getComponents();
        final List<Long> seeds = getSeeds(components);
        final List<Map> maps = getMaps(components);

        return String.valueOf(seeds.stream().map(seed -> {
            long value = seed;

            for (Map map : maps) {
                value = map.get(value);
            }

            return value;
        }).min(Long::compareTo).orElseThrow());
    }

    public boolean isOriginalSeed(Collection<List<Long>> seedRanges, long seed) {
        for (List<Long> range : seedRanges) {
            if (range.get(0) <= seed && seed < range.get(0) + range.get(1)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String partTwo() {
        final List<List<String>> components = getComponents();

        final Collection<List<Long>> seedRanges = CombinatoricsUtil.partitionList(getSeeds(components), 2);

        List<Map> maps = new ArrayList<>(getMaps(components));
        Collections.reverse(maps);

        LongStream locations = maps.get(0).getAllValues();
        PrimitiveIterator.OfLong iterator = locations.iterator();
        while (iterator.hasNext()) {
            long next = iterator.next();

            long value = next;
            for (Map map : maps) {
                value = map.getKey(value);
            }

            if (isOriginalSeed(seedRanges, value)) {
                return String.valueOf(next);
            }
        }

        return "Not found";
    }
}
