package dev.jacobandersen.codechallenges.challenge.rosalind;

import dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.StringAlgorithmChallenge;

public class RosalindBootstrap {
    public static void main(String[] args) {
        new RosalindBootstrap().run();
    }

    public void run() {
        StringAlgorithmChallenge.getAll().forEach(Challenge::run);
    }
}
