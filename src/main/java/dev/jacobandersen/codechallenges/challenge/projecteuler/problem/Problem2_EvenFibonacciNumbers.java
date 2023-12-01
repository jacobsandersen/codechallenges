package dev.jacobandersen.codechallenges.challenge.projecteuler.problem;

import dev.jacobandersen.codechallenges.challenge.projecteuler.ProjectEulerProblem;

import java.util.ArrayList;
import java.util.List;

public class Problem2_EvenFibonacciNumbers extends ProjectEulerProblem {
    public Problem2_EvenFibonacciNumbers() {
        super(2, "Even Fibonacci Numbers");
    }

    @Override
    public String solution() {

        int a = 1;
        int b = 2;

        List<Integer> fibonacciTerms = new ArrayList<>(List.of(a, b));

        while (true) {
            int tmp = a + b;
            a = b;
            b = tmp;
            if (b > 4_000_000) {
                break;
            } else {
                fibonacciTerms.add(b);
            }
        }

        return String.valueOf(fibonacciTerms.stream().filter(n -> n % 2 == 0).reduce(0, Integer::sum));
    }
}
