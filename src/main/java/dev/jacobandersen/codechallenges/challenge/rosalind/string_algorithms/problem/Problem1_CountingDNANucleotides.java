package dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.problem;

import dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.StringAlgorithmChallenge;

public class Problem1_CountingDNANucleotides extends StringAlgorithmChallenge {
    public Problem1_CountingDNANucleotides() {
        super(1, "Counting DNA Nucleotides");
    }

    @Override
    public String solution() {
        int A = 0;
        int C = 0;
        int G = 0;
        int T = 0;

        for (String line : getInputLinesStream().filter(line -> !line.isBlank()).toList()) {
            for (int j = 0; j < line.toCharArray().length; j++) {
                switch (line.charAt(j)) {
                    case 'A':
                        A++;
                        break;
                    case 'C':
                        C++;
                        break;
                    case 'G':
                        G++;
                        break;
                    case 'T':
                        T++;
                        break;
                    default:
                        break;
                }
            }
        }

        return String.format("%d %d %d %d", A, C, G, T);
    }
}
