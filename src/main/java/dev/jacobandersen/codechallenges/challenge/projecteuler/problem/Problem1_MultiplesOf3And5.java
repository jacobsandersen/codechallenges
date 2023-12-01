package dev.jacobandersen.codechallenges.challenge.projecteuler.problem;

import dev.jacobandersen.codechallenges.challenge.projecteuler.ProjectEulerProblem;

public class Problem1_MultiplesOf3And5 extends ProjectEulerProblem {
    public Problem1_MultiplesOf3And5() {
        super(1, "Multiples of 3 and 5");
    }

    @Override
    public String solution() {
        final int maxN = 1000;
        int sum = 0;
        for (int i = 3; i < maxN; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i;
            }
        }
        return String.valueOf(sum);
    }
}
