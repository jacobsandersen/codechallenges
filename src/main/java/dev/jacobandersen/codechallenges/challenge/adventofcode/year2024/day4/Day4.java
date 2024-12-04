package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day4;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Crossword;
import dev.jacobandersen.codechallenges.util.Direction;

import java.util.List;
import java.util.Map;

public class Day4 extends Day {
    public Day4() {
        super(2024, 4, "Ceres Search");
    }

    @Override
    public String partOne() {
        return String.valueOf(new Crossword(getInputLinesNoBlanks()).search("XMAS"));
    }

    @Override
    public String partTwo() {
        return String.valueOf(new Crossword(getInputLinesNoBlanks()).search(
                'A',
                List.of(
                        Map.of(Direction.NORTH_WEST, 'M', Direction.NORTH_EAST, 'S', Direction.SOUTH_WEST, 'M', Direction.SOUTH_EAST, 'S'),
                        Map.of(Direction.NORTH_WEST, 'S', Direction.NORTH_EAST, 'M', Direction.SOUTH_WEST, 'S', Direction.SOUTH_EAST, 'M'),
                        Map.of(Direction.NORTH_WEST, 'M', Direction.NORTH_EAST, 'M', Direction.SOUTH_WEST, 'S', Direction.SOUTH_EAST, 'S'),
                        Map.of(Direction.NORTH_WEST, 'S', Direction.NORTH_EAST, 'S', Direction.SOUTH_WEST, 'M', Direction.SOUTH_EAST, 'M')
                )
        ));
    }
}
