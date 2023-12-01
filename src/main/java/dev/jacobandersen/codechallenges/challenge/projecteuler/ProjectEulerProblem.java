package dev.jacobandersen.codechallenges.challenge.projecteuler;

import dev.jacobandersen.codechallenges.Problem;
import dev.jacobandersen.codechallenges.challenge.projecteuler.problem.Problem1_MultiplesOf3And5;
import dev.jacobandersen.codechallenges.challenge.projecteuler.problem.Problem2_EvenFibonacciNumbers;
import dev.jacobandersen.codechallenges.challenge.projecteuler.problem.Problem3_LargestPrimeFactor;

import java.util.List;

public abstract class ProjectEulerProblem extends Problem {
    private final int number;
    private final String name;

    public ProjectEulerProblem(int number, String name) {
        super("Project Euler");
        this.number = number;
        this.name = name;
    }

    public static List<ProjectEulerProblem> getAll() {
        return List.of(
                new Problem1_MultiplesOf3And5(),
                new Problem2_EvenFibonacciNumbers(),
                new Problem3_LargestPrimeFactor()
        );
    }

    @Override
    public String getInputPath() {
        return "";
    }

    public abstract String solution();

    @Override
    public void run() {
        System.out.printf("Project Euler #%d - %s%n", number, name);
        System.out.printf("| %s%n", solution());
    }
}
