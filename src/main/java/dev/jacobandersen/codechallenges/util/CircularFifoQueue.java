package dev.jacobandersen.codechallenges.util;

import java.util.ArrayList;
import java.util.List;

public final class CircularFifoQueue<T> {
    private final List<T> items;
    private int current;

    public CircularFifoQueue() {
        items = new ArrayList<>();
        current = 0;
    }

    public void add(T item) {
        items.add(item);
    }

    public T next() {
        T item = items.get(current);

        current++;
        if (current >= items.size()) {
            current = 0;
        }

        return item;
    }

    @Override
    public String toString() {
        return "CircularFifoQueue{" +
                "items=" + items +
                ", current=" + current +
                '}';
    }
}
