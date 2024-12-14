package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day13;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends Day {
    private final Pattern pattern = Pattern.compile("^.*[+=](\\d+).*[+=](\\d+)$");

    public Day13() {
        super(2024, 13, "Claw Contraption");
    }

    record Machine(double[] x, double[] y) {
        private long[] solve() throws RuntimeException {
            final SimpleMatrix A = new SimpleMatrix(2, 2);
            A.set(0, 0, x[0]);
            A.set(0, 1, x[1]);
            A.set(1, 0, y[0]);
            A.set(1, 1, y[1]);

            final SimpleMatrix B = new SimpleMatrix(2, 1);
            B.set(0, 0, x[2]);
            B.set(1, 0, y[2]);

            final SimpleMatrix sol = A.solve(B);

            long solA = Math.round(sol.get(0)), solB = Math.round(sol.get(1));

            double xCheck = (x[0] * solA) + (x[1] * solB);
            double yCheck = (y[0] * solA) + (y[1] * solB);

            if (Math.abs(xCheck - x[2]) < 1e-6 && Math.abs(yCheck - y[2]) < 1e-6) {
                return new long[] {solA, solB};
            } else throw new RuntimeException("No integer solution.");
        }

        @Override
        public String toString() {
            return "Machine{" +
                    "x=" + Arrays.toString(x) +
                    ", y=" + Arrays.toString(y) +
                    '}';
        }
    }

    private double[] matchLine(String line) {
        Matcher matcher = pattern.matcher(line);
        if(!matcher.matches()) throw new IllegalArgumentException();
        return new double[]{Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2))};
    }

    private List<Machine> loadMachines() {
        List<String> lines = getInputLinesNoBlanks();

        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i+=3) {
            final double[] x = new double[3], y = new double[3];

            final double[] a = matchLine(lines.get(i)), b = matchLine(lines.get(i+1)), prize = matchLine(lines.get(i+2));
            x[0] = a[0];
            y[0] = a[1];
            x[1] = b[0];
            y[1] = b[1];
            x[2] = prize[0];
            y[2] = prize[1];

            machines.add(new Machine(x, y));
        }

        return machines;
    }

    private long countTokens(List<Machine> machines) {
        return machines.stream().mapToLong(machine -> {
            try {
                final long[] solution = machine.solve();
                return (3 * solution[0]) + solution[1];
            } catch (RuntimeException ignored) {}

            return 0;
        }).sum();
    }

    @Override
    public String partOne() {
        return String.valueOf(countTokens(loadMachines()));
    }

    @Override
    public String partTwo() {
        return String.valueOf(countTokens(loadMachines().stream().map(m -> {
            final double[] x = new double[] { m.x[0], m.x[1], m.x[2] + 10_000_000_000_000d };
            final double[] y = new double[] { m.y[0], m.y[1], m.y[2] + 10_000_000_000_000d };
            return new Machine(x, y);
        }).toList()));
    }
}
