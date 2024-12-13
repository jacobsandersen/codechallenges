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

    record Plot(String crop, int y, int x) implements Comparable<Plot> {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Plot that = (Plot) obj;
            return crop.equals(that.crop) && y == that.y && x == that.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(crop(), y(), x());
        }

        public boolean areAdjacent(Plot other) {
            return (Math.abs(y - other.y) == 1 && x == other.x) || (Math.abs(x - other.x) == 1 && y == other.y);
        }

        @Override
        public int compareTo(Plot other) {
            if (y == other.y) {
                return Integer.compare(x, other.x);
            }

            return Integer.compare(y, other.y);
        }
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

        // sort edge points by y, then by x, such that (0,0) < (0,1) < (2,0)
        // start at top left of shape (y, x), move up such that now at (y-1, x), save this as "start"
        // copy "start" into a position pointer called "ptr"

        // set "movingDirection" = right
        // set "observingDirection" = down (should point at (y,x), the top left of the shape)
        // set "sideCount" = 0

        // continue looping until "ptr" == "start" (do-while):
        //   - set "nextPos" = peek("ptr", "movingDirection")
        //   - if "nextPos" is NOT in the region:
        //     - set "ptr" = move("ptr", "movingDirection")
        //     - set "observedPos" = peek("ptr", "observingDirection")
        //     - if "observedPos" is NOT in the region: [we have moved off an edge, only way is to turn right, or clockwise]
        //       - increment "sideCount"
        //       - set "movingDirection" = clockwiseOf("movingDirection") [right should become down]
        //       - set "observingDirection" = clockwiseOf("observingDirection") [down should become left]
        //       - continue loop
        //   - else: [we're at a corner, only way is to turn left, or counterclockwise]
        //     - increment "sideCount"
        //     - set "movingDirection" = counterclockwiseOf("movingDirection") [right should become up]
        //     - set "observingDirection" = counterclockwiseOf("observingDirection") [down should become right]
        //     - continue loop
        // return "sideCount"

        public int sides() {
            final List<Pair<Integer, Integer>> points = plots.stream().map(plot -> Pair.of(plot.y, plot.x)).toList();
            final Pair<Integer, Integer> start = Pair.of(points.get(0).first() - 1, points.get(0).second());

            Pair<Integer, Integer> ptr = start.copy();

            Direction movingDirection = Direction.EAST;
            Direction observingDirection = Direction.SOUTH;

            int sideCount = 0;

            do {
                Pair<Integer, Integer> movingRel = movingDirection.relativeCoordinate(), observingRel = observingDirection.relativeCoordinate();
                Pair<Integer, Integer> nextPos = Pair.of(ptr.first() + movingRel.first(), ptr.second() + movingRel.second());
                if (!points.contains(nextPos)) {
                    ptr = nextPos;

                    Pair<Integer, Integer> observedPos = Pair.of(ptr.first() + observingRel.first(), ptr.second() + observingRel.second());
                    if (!points.contains(observedPos)) {
                        sideCount++;
                        movingDirection = movingDirection.getCardinalRight();
                        observingDirection = observingDirection.getCardinalRight();
                    }
                } else {
                    sideCount++;
                    movingDirection = movingDirection.getCardinalLeft();
                    observingDirection = observingDirection.getCardinalLeft();
                }
            } while (!ptr.equals(start));

            return sideCount;
        }

        public int discountedFencePrice() {
            System.out.printf("region: %s%n", this.plots.get(0).crop);
            int area = area();
            System.out.printf("  area = %d%n", area);
            int sides = sides();
            System.out.printf("  sides = %d%n", sides);
            return area * sides;
        }
    }
}
