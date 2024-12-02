package dev.jacobandersen.codechallenges.util;

import java.util.stream.Stream;

public class RectangularCuboid implements Shape {
    private final int length;
    private final int width;
    private final int height;

    public RectangularCuboid(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public int surfaceArea() {
        return 2 * ((length * width) + (length * height) + (width * height));
    }

    @Override
    public int volume() {
        return length * width * height;
    }

    @Override
    public int areaOfSmallestSide() {
        return Stream.of(length * width, length * height, width * height)
                .min(Integer::compareTo)
                .orElseThrow();
    }

    @Override
    public int perimeterOfSmallestSide() {
        return Stream.of(length + width, length + height, width + height)
                .map(halfPerimeter -> 2 * halfPerimeter)
                .min(Integer::compareTo)
                .orElseThrow();
    }
}
