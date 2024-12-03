package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day1;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

public class Day1 extends Day {
    public Day1() {
        super(2015, 1, "Not Quite Lisp");
    }

    @Override
    public String partOne() {
        return String.valueOf(getInputLinesNoBlanks().get(0).chars().map(c -> c == '(' ? 1 : c == ')' ? -1 : 0).reduce(0, Integer::sum));
    }

    @Override
    public String partTwo() {
        int floor = 0;

        char[] input = getInputLinesNoBlanks().get(0).toCharArray();
        for (int i = 0; i < input.length; i++) {
            if (input[i] == '(') floor++;
            if (input[i] == ')') floor--;
            if (floor == -1) {
                return String.valueOf(i + 1);
            }
        }

        return "Never reached basement.";
    }
}
