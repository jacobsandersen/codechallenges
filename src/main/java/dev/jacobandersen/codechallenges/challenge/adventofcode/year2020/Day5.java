package dev.jacobandersen.codechallenges.challenge.adventofcode.year2020;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.Comparator;
import java.util.List;

public class Day5 extends Day {
    public Day5() {
        super(2020, 5, "Binary Boarding");
    }

    private final List<BoardingPass> getBoardingPasses() {
        return getInputLinesStreamNoBlanks().map(BoardingPass::new).toList();
    }

    @Override
    public String partOne() {
        return String.valueOf(getBoardingPasses()
                .stream()
                .map(BoardingPass::getSeat)
                .max(Comparator.comparingInt(Seat::getId))
                .map(Seat::getId)
                .orElseThrow());
    }

    @Override
    public String partTwo() {
        List<Seat> orderedSeats = getBoardingPasses()
                .stream()
                .map(BoardingPass::getSeat)
                .sorted(Comparator.comparingInt(Seat::getId))
                .toList();

        int seat = -1;

        for (int i = 0; i + 3 < orderedSeats.size(); i += 3) {
            Seat first = orderedSeats.get(i), middle = orderedSeats.get(i + 1), last = orderedSeats.get(i + 2);

            boolean lower = middle.getId() - first.getId() > 1, upper = last.getId() - middle.getId() > 1;
            if (lower || upper) {
                if (lower) {
                    seat = first.getId() + 1;
                } else {
                    seat = middle.getId() + 1;
                }

                break;
            }
        }

        return String.valueOf(seat);
    }

    private record BoardingPass(String seatPath) {
        public Seat getSeat() {
                return new Seat(
                        getRow(seatPath.substring(0, 7)),
                        getColumn(seatPath.substring(7, 10))
                );
            }

            private int getRow(String rowPath) {
                return followPath(rowPath, 127, 'F', 'B');
            }

            private int getColumn(String columnPath) {
                return followPath(columnPath, 7, 'L', 'R');
            }

            private int followPath(String path, int upperBound, char lowerInstruction, char upperInstruction) {
                SpacePartition partition = new SpacePartition(0, upperBound);

                char[] chars = path.toCharArray();
                for (char c : chars) {
                    if (c == lowerInstruction) {
                        partition = partition.takeLowerHalf();
                    } else if (c == upperInstruction) {
                        partition = partition.takeUpperHalf();
                    }
                }

                return partition.middle();
            }
        }


    private record Seat(int row, int column) {
        public int getId() {
                return (row() * 8) + column();
            }

            @Override
            public String toString() {
                return "Seat{" +
                        "row=" + row +
                        ", column=" + column +
                        ", id=" + getId() +
                        '}';
            }
        }

    private record SpacePartition(int min, int max) {
        public SpacePartition takeLowerHalf() {
                if (max - min == 1) {
                    return new SpacePartition(min, min);
                }

                return new SpacePartition(min, middle(false));
            }

            public SpacePartition takeUpperHalf() {
                if (max - min == 1) {
                    return new SpacePartition(max, max);
                }

                return new SpacePartition(middle(true), max);
            }

            public int middle() {
                return middle(false);
            }

            public int middle(boolean roundUp) {
                if (roundUp) {
                    return ((min + max) + 1) / 2;
                } else {
                    return (min + max) / 2;
                }
            }
        }
}
