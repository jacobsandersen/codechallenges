package dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms;

import dev.jacobandersen.codechallenges.challenge.rosalind.Challenge;
import dev.jacobandersen.codechallenges.challenge.rosalind.Topic;
import dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.problem.*;

import java.util.List;

public abstract class StringAlgorithmChallenge extends Challenge {
    public StringAlgorithmChallenge(int number, String name) {
        super(Topic.STRING_ALGORITHMS, number, name);
    }

    public static List<Challenge> getAll() {
        return List.of(
                new Problem1_CountingDNANucleotides(),
                new Problem2_TranscribingDNAIntoRNA(),
                new Problem3_ComplementingAStrandOfDNA(),
                new Problem4_ComputingGCContent(),
                new Problem5_FindingAMotifInDNA()
        );
    }
}
