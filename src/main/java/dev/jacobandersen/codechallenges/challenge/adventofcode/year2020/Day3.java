package dev.jacobandersen.codechallenges.challenge.adventofcode.year2020;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.List;
import java.util.stream.Stream;

public class Day3 extends Day {
    public Day3() {
        super(2020, 3, "Toboggan Trajectory");
    }

    private long countTrees(List<List<Boolean>> data, int slopeX, int slopeY) {
        int currentX = 0, realX = 0, currentY = 0, maxX = data.get(0).size();
        long trees = 0;

        for (; currentY < data.size(); currentY += slopeY) {
            boolean tree = data.get(currentY).get(currentX);

            if (tree) {
                trees++;
            }

            if (currentX + slopeX >= maxX) {
                if (maxX - currentX == 0) {
                    currentX += slopeX;
                } else {
                    currentX += slopeX - maxX;
                }
            } else {
                currentX += slopeX;
            }
        }

        return trees;
    }

    private List<List<Boolean>> buildInput() {
        return getInputLinesStreamNoBlanks()
                .map(line -> line.chars().mapToObj(c -> c == '#').toList())
                .toList();
    }

    @Override
    public String partOne() {
        return String.valueOf(countTrees(buildInput(), 3, 1));
    }

    @Override
    public String partTwo() {
        final List<List<Boolean>> data = buildInput();

        return String.valueOf(Stream.of(new int[]{1, 1}, new int[]{3, 1}, new int[]{5, 1}, new int[]{7, 1}, new int[]{1, 2})
                .map(slope -> countTrees(data, slope[0], slope[1]))
                .reduce(1L, (x, y) -> x * y));
    }
}
