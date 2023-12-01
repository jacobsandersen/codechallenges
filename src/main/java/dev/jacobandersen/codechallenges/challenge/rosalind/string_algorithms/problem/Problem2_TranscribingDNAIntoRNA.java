package dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.problem;

import dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.StringAlgorithmChallenge;

public class Problem2_TranscribingDNAIntoRNA extends StringAlgorithmChallenge {
    public Problem2_TranscribingDNAIntoRNA() {
        super(2, "Transcribing DNA into RNA");
    }

    @Override
    public String solution() {
        return String.valueOf(getInputLinesStream()
                .filter(line -> !line.isBlank())
                .map(line -> line.replaceAll("T", "U"))
                .toList());
    }
}
