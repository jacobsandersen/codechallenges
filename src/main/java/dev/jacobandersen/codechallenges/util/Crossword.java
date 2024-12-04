package dev.jacobandersen.codechallenges.util;

import java.util.List;
import java.util.Map;

public class Crossword {
    private final char[][] grid;

    public Crossword(List<String> lines) {
        int xLength = lines.stream().mapToInt(String::length).max().orElse(lines.get(0).length());

        char[][] grid = new char[lines.size()][xLength];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x] = lines.get(y).charAt(x);
            }
        }

        this.grid = grid;
    }

    public int search(String needle) {
        return search(needle, Direction.values());
    }

    public int search(String needle, Direction... directions) {
        int count = 0;
        char startChar = needle.charAt(0), endChar = needle.charAt(needle.length() - 1);

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[y][x] != startChar) continue;

                for (Direction direction : directions) {
                    Pair<Integer, Integer> relCoordinate = direction.relativeCoordinate();

                    char lastChar = startChar;

                    for (int idx = 1, dirY = y + relCoordinate.first(), dirX = x + relCoordinate.second(); idx < needle.length() && dirY >= 0 && dirY < grid.length && dirX >= 0 && dirX < grid[x].length; idx++, dirY += relCoordinate.first(), dirX += relCoordinate.second()) {
                        char current = grid[dirY][dirX];

                        if (current != nextChar(needle, idx)) {
                            break;
                        }

                        lastChar = grid[dirY][dirX];
                    }

                    if (lastChar == endChar) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int search(char needle, List<Map<Direction, Character>> patterns) {
        int count = 0;

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[y][x] != needle) continue;

                for (Map<Direction, Character> pattern : patterns) {
                    boolean patternFound = true;

                    for (Map.Entry<Direction, Character> entry : pattern.entrySet()) {
                        final Pair<Integer, Integer> relCoordinate = entry.getKey().relativeCoordinate();
                        final int finalY = y + relCoordinate.first(), finalX = x + relCoordinate.second();
                        if (finalY >= 0 && finalY < grid.length && finalX >= 0 && finalX < grid[x].length) {
                            if (grid[finalY][finalX] != entry.getValue()) {
                                patternFound = false;
                            }
                        } else {
                            patternFound = false;
                        }

                        if (!patternFound) break;
                    }

                    if (patternFound) {
                        count++;
                        break;
                    }
                }
            }
        }

        return count;
    }

    private char nextChar(String needle, int idx) {
        if (idx < 0 || idx >= needle.length()) return 0;
        return needle.charAt(idx);
    }
}
