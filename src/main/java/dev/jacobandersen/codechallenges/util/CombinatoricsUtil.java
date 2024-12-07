package dev.jacobandersen.codechallenges.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CombinatoricsUtil {
    public static <T> List<List<T>> generateSublists(List<T> list, int numElements) {
        if (numElements <= 0 || numElements > list.size()) {
            throw new IllegalArgumentException(String.format("Cannot generate sublists of size <= 0 or size >= list size (%d)", list.size()));
        }

        List<List<T>> result = new ArrayList<>();
        generateSublists(list, numElements, 0, new ArrayList<>(), result);
        return result;
    }

    private static <T> void generateSublists(List<T> list, int numElements, int idx, List<T> current, List<List<T>> result) {
        if (numElements == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = idx; i < list.size(); i++) {
            current.add(list.get(i));
            generateSublists(list, numElements - 1, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public static <T> Collection<List<T>> partitionList(List<T> list, int numPerPartition) {
        AtomicInteger numPartitions = new AtomicInteger(0);
        return list.stream().collect(Collectors.groupingBy(item -> numPartitions.getAndIncrement() / numPerPartition)).values();
    }

    public static <T> List<List<T>> overlappingPartitionList(List<T> list, int numPerPartition) {
        AtomicInteger start = new AtomicInteger(0);

        return list.stream()
                .map(item -> {
                    int currentIndex = start.getAndIncrement();
                    int endIndex = Math.min(currentIndex + numPerPartition, list.size());
                    if (endIndex - currentIndex < numPerPartition) {
                        return new ArrayList<T>();
                    }
                    return new ArrayList<>(list.subList(currentIndex, endIndex));
                })
                .filter(l -> !l.isEmpty())
                .collect(Collectors.toList());
    }

    public static <T> List<List<T>> selfCartesianProduct(List<T> set, int nTimes) {
        List<List<T>> target = new ArrayList<>();
        for (int i = 0; i < nTimes; i++) {
            target.add(set);
        }

        return cartesianProduct(target);
    }

    public static <T> List<List<T>> cartesianProduct(List<List<T>> sets) {
        List<List<T>> result = new ArrayList<>();

        if (sets == null || sets.isEmpty()) {
            return result;
        }

        result.add(new ArrayList<>());

        for (List<T> set : sets) {
            List<List<T>> temp = new ArrayList<>();

            for (List<T> current : result) {
                for (T item : set) {
                    List<T> newCombination = new ArrayList<>(current);
                    newCombination.add(item);
                    temp.add(newCombination);
                }
            }

            result = temp;
        }

        return result;
    }
}
