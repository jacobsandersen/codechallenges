package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day12;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Direction;
import dev.jacobandersen.codechallenges.util.Grid;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 extends Day {
    public Day12() {
        super(2024, 12, "Garden Groups");
    }

    private boolean isInRegion(List<Region> regions, int y, int x) {
        return regions.stream().anyMatch(rg -> rg.plots.stream().anyMatch(plot -> plot.y == y && plot.x == x));
    }

    private void exploreFrom(Grid<String> grid, Plot plot, Region region) {
        if (region.plots.contains(plot)) return; // don't backtrack

        region.plots.add(plot);

        Grid<String> local = grid.copy();
        for (Direction direction : Direction.cardinalValues()) {
            final String peek = local.peek(direction);
            if (peek != null && peek.equals(plot.crop)) {
                local.move(direction);
                exploreFrom(local, new Plot(plot.crop, local.getCurrentRow(), local.getCurrentCol()), region);
                local.move(direction.getOpposite());
            }
        }
    }

    private List<Region> explore(Grid<String> grid) {
        List<Region> regions = new ArrayList<>();

        for (int y = 0; y < grid.getRows(); y++) {
            for (int x = 0; x < grid.getCols(); x++) {
                grid.move(y, x);
                if (isInRegion(regions, y, x)) continue;

                Region region = new Region(new ArrayList<>());
                exploreFrom(grid, new Plot(grid.get(), y, x), region);
                regions.add(region);
            }
        }

        return regions;
    }

    private Grid<String> loadGrid() {
        return Grid.create(getInputLinesStreamNoBlanks(), str -> Arrays.stream(str.split("")).toList());
    }

    @Override
    public String partOne() {
        return String.valueOf(
                explore(loadGrid()).stream().mapToInt(Region::fencePrice).sum()
        );
    }

    @Override
    public String partTwo() {
        Grid<String> grid = loadGrid();
        List<Region> regions = explore(grid);
        return String.valueOf(
                regions.stream().mapToInt(Region::discountedFencePrice).sum()
        );
    }

    record Plot(String crop, int y, int x) {
    }

    record Region(List<Plot> plots) {
        public int area() {
            return plots.size();
        }

        public int perimeter() {
            final Set<Pair<Integer, Integer>> points = plots.stream().map(plot -> Pair.of(plot.y, plot.x)).collect(Collectors.toSet());
            return points.stream().mapToInt(pt -> {
                int perimeter = 4;
                for (final Direction direction : Direction.cardinalValues()) {
                    final Pair<Integer, Integer> rel = direction.relativeCoordinate();
                    final Pair<Integer, Integer> neighbor = Pair.of(pt.first() + rel.first(), pt.second() + rel.second());
                    if (points.contains(neighbor)) perimeter--;
                }
                return perimeter;
            }).sum();
        }

        public int fencePrice() {
            return area() * perimeter();
        }

        public int sides() {
            return 0;
        }

        public int discountedFencePrice() {
            return area() * sides();
        }
    }
}
