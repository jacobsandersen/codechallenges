package dev.jacobandersen.codechallenges.challenge.projecteuler;

import dev.jacobandersen.codechallenges.Problem;

public class ProjectEulerBootstrap {
    public static void main(String[] args) {
        new ProjectEulerBootstrap().run();
    }

    public void run() {
        ProjectEulerProblem.getAll().forEach(Problem::run);
    }
}
