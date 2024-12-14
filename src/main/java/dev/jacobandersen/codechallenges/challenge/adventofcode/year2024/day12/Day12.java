package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day12;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.Direction;
import dev.jacobandersen.codechallenges.util.Grid;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 extends Day {
    private final List<Region> regions = new ArrayList<>();

    public Day12() {
        super(2024, 12, "Garden Groups");
    }

    private void exploreFrom(Grid<Plot> grid, Plot plot, Region region, boolean[][] visited) {
        if (visited[plot.y][plot.x]) return;

        visited[plot.y][plot.x] = true;
        region.plots.add(plot);

        for (Direction direction : Direction.cardinalValues()) {
            final Plot peek = grid.peek(direction);
            if (peek != null && peek.crop.equals(plot.crop)) {
                grid.move(direction);
                exploreFrom(grid, peek, region, visited);
                grid.move(direction.getOpposite());
            }
        }
    }

    private List<Region> explore(Grid<Plot> grid) {
        List<Region> regions = new ArrayList<>();
        boolean[][] visited = new boolean[grid.getRows()][grid.getCols()];

        for (int y = 0; y < grid.getRows(); y++) {
            for (int x = 0; x < grid.getCols(); x++) {
                if (visited[y][x]) continue;

                grid.move(y, x);

                Region region = new Region(new ArrayList<>(), grid);
                exploreFrom(grid, grid.get(), region, visited);
                regions.add(region);
            }
        }

        return regions;
    }

    private Grid<Plot> loadGrid() {
        return Grid.create(getInputLinesStreamNoBlanks(), str -> Arrays.stream(str.split("")).toList())
                .map(ctx -> new Plot(ctx.value(), ctx.position().first(), ctx.position().second()));
    }

    @Override
    public String partOne() {
        regions.addAll(explore(loadGrid()));

        return String.valueOf(regions.stream().mapToInt(Region::fencePrice).sum());
    }

    @Override
    public String partTwo() {
        return String.valueOf(regions.stream().mapToInt(Region::discountedFencePrice).sum());
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

        @Override
        public int compareTo(Plot other) {
            if (y == other.y) {
                return Integer.compare(x, other.x);
            }

            return Integer.compare(y, other.y);
        }
    }

    record Region(List<Plot> plots, Grid<Plot> grid) {
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

        private Plot[] getCornerNeighbors(Direction corner, Map<Direction, Plot> neighborhood) {
            return switch (corner) {
                case NORTH_WEST ->
                        new Plot[]{neighborhood.get(Direction.WEST), neighborhood.get(Direction.NORTH), neighborhood.get(corner)};
                case NORTH_EAST ->
                        new Plot[]{neighborhood.get(Direction.EAST), neighborhood.get(Direction.NORTH), neighborhood.get(corner)};
                case SOUTH_EAST ->
                        new Plot[]{neighborhood.get(Direction.EAST), neighborhood.get(Direction.SOUTH), neighborhood.get(corner)};
                case SOUTH_WEST ->
                        new Plot[]{neighborhood.get(Direction.WEST), neighborhood.get(Direction.SOUTH), neighborhood.get(corner)};
                default -> throw new IllegalArgumentException("Unexpected corner direction: " + corner);
            };
        }

        private boolean isCorner(final Plot plot, final Plot[] neighbors) {
            final boolean side1Same = neighbors[0] != null && neighbors[0].crop.equals(plot.crop);
            final boolean side2Same = neighbors[1] != null && neighbors[1].crop.equals(plot.crop);
            final boolean diagonalSame = neighbors[2] != null && neighbors[2].crop.equals(plot.crop);

            return (!side1Same && !side2Same) || (side1Same && side2Same && !diagonalSame);
        }

        public int countCorners(final Plot plot, final Map<Direction, Plot> neighborhood) {
            int corners = 0;

            for (final Direction corner : Direction.diagonalValues()) {
                if (isCorner(plot, getCornerNeighbors(corner, neighborhood))) {
                    corners++;
                }
            }

            return corners;
        }

        public int sides() {
            final Map<Plot, Map<Direction, Plot>> neighborhoods = plots.stream().map(plot -> {
                final Map<Direction, Plot> neighborhood = new HashMap<>();
                for (final Direction direction : Direction.values()) {
                    final Pair<Integer, Integer> rel = direction.relativeCoordinate();
                    final int adjY = plot.y + rel.first(), adjX = plot.x + rel.second();
                    neighborhood.put(
                            direction,
                            grid.getOrNull(adjY, adjX)
                    );
                }
                return Map.entry(plot, neighborhood);
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            return plots.stream().mapToInt(plot -> countCorners(plot, neighborhoods.get(plot))).sum();
        }

        public int discountedFencePrice() {
            int area = area();
            int sides = sides();
            return area * sides;
        }
    }
}
