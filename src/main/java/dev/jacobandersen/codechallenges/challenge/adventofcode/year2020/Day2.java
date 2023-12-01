package dev.jacobandersen.codechallenges.challenge.adventofcode.year2020;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.List;

public class Day2 extends Day {
    public Day2() {
        super(2020, 2, "Password Philosophy");
    }

    private List<Password> parsePasswords() {
        return getInputLinesStreamNoBlanks().map(Password::create).toList();
    }

    @Override
    public String partOne() {
        return String.valueOf(parsePasswords().stream().filter(Password::isValidOne).count());
    }

    @Override
    public String partTwo() {
        return String.valueOf(parsePasswords().stream().filter(Password::isValidTwo).count());
    }

    private record Password(char inspected, int minInspected, int maxInspected, String pass) {
        public static Password create(String input) {
            String[] splitOne = input.split(":");

            String[] inspectedSplit = splitOne[0].split(" ");
            char inspected = inspectedSplit[1].trim().charAt(0);

            String[] minMaxSplit = inspectedSplit[0].split("-");
            int min = Integer.parseInt(minMaxSplit[0].trim());
            int max = Integer.parseInt(minMaxSplit[1].trim());

            String pass = splitOne[1].trim();

            return new Password(inspected, min, max, pass);
        }

        public boolean isValidOne() {
            long numInspected = pass.chars().mapToObj(c -> (char) c).filter(c -> c.equals(inspected)).count();
            return numInspected >= minInspected && numInspected <= maxInspected;
        }

        public boolean isValidTwo() {
            char posMin = pass.charAt(minInspected - 1);
            char posMax = pass.charAt(maxInspected - 1);
            return (posMin == inspected && posMax != inspected) || (posMax == inspected && posMin != inspected);
        }
    }
}
