package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day5;

import java.util.List;
import java.util.stream.LongStream;

public record Map(String name, List<MapEntry> entries) {
    static Map create(List<String> lines) {
        String name = lines.get(0).split(":")[0].split(" ")[0];
        List<MapEntry> entries = lines.stream().skip(1).map(MapEntry::create).toList();
        return new Map(name, entries);
    }

    public long get(long key) {
        for (MapEntry entry : entries) {
            long sourceLow = entry.sourceStart();
            long sourceHigh = entry.sourceStart() + entry.rangeLength() - 1;
            if (sourceLow <= key && key <= sourceHigh) {
                long distance = key - sourceLow;
                return entry.destinationStart() + distance;
            }
        }

        return key;
    }

    public long getKey(long value) {
        for (MapEntry entry : entries) {
            long destinationLow = entry.destinationStart();
            long destinationHigh = destinationLow + entry.rangeLength() - 1;
            if (destinationLow <= value && value <= destinationHigh) {
                long distance = value - destinationLow;
                return entry.sourceStart() + distance;
            }
        }

        return value;
    }

    public LongStream getAllValues() {
        long highestDestination = entries.stream().map(entry -> entry.destinationStart() + entry.rangeLength()).max(Long::compareTo).orElseThrow();
        return LongStream.range(0, highestDestination).sorted();
    }
}
