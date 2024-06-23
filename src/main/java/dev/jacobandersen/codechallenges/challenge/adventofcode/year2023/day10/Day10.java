package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day10;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Direction;
import dev.jacobandersen.codechallenges.util.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 extends Day {
    public Day10() {
        super(2023, 10, "Pipe Maze");
    }

    private Grid<Character> getGrid() {
        return Grid.create(getInputLinesStreamNoBlanks(), str -> Arrays.stream(str.split("")).map(part -> part.charAt(0)).toList());
    }

    public List<Component> walkLoop(Grid<Character> grid) {
        List<Component> loop = new ArrayList<>();

        Component current = new Component(grid.getCurrentRow(), grid.getCurrentCol(), Component.Type.fromSymbol(grid.get()));
        loop.add(current);

        Direction last = null;
        do {
            for (Direction dir : Direction.values()) {
                Character nextSymb = grid.peek(dir);
                if (nextSymb == null) {
                    continue;
                }

                int[] nextPos = grid.peekPosition(dir);
                Component next = new Component(nextPos[0], nextPos[1], Component.Type.fromSymbol(nextSymb));

                // does the current pipe component let us go that way, and does the target receive us that way
                if (current.type().getValidDirections().contains(dir) && next.type().getValidDirections().contains(dir.getOpposite())) {
                    if (dir == last) {
                        // don't go backwards
                        continue;
                    }
                    grid.move(dir);
                    if (next.type() != Component.Type.START) loop.add(next);
                    last = dir.getOpposite();
                    current = next;
                    break;
                }
            }
        } while (current.type() != Component.Type.START);

        return loop;
    }

    @Override
    public String partOne() {
        Grid<Character> grid = getGrid().moveCellToFirstOccurrence('S');
        List<Component> loop = walkLoop(grid);

        int highestStep = 0;
        int highest = -1;

        Component start = loop.get(0);
        for (int i = 0; i < loop.size(); i++) {
            Component next = loop.get(i);
            System.out.println(next);
            int distance = Math.abs(next.x() - start.x()) + Math.abs(next.y() - start.y());
            if (distance > highest) {
                System.out.printf("...Got highest distance = %d at step %d%n", distance, i);
                highest = distance;
                highestStep = i;
            }
        }

        System.out.println(highestStep);

        return null;
    }

    @Override
    public String partTwo() {
        return null;
    }
}
