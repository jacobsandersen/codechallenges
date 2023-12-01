package dev.jacobandersen.codechallenges.util;

import java.util.ArrayList;
import java.util.List;

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
}
