package dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.problem;

import dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.StringAlgorithmChallenge;

public class Problem3_ComplementingAStrandOfDNA extends StringAlgorithmChallenge {
    public Problem3_ComplementingAStrandOfDNA() {
        super(3, "Complementing a Strand of DNA");
    }

    @Override
    public String solution() {
        return String.valueOf(getInputLinesStream()
                .filter(line -> !line.isBlank())
                .map(line -> {
                    char[] original = line.toCharArray();
                    char[] rev = new char[line.length()];

                    for (int i = 0, j = original.length - 1; i < original.length; i++, j--) {
                        rev[i] = original[j];
                    }

                    return new String(rev);
                })
                .map(line -> {
                    char[] c = line.toCharArray();

                    for (int i = 0; i < c.length; i++) {
                        switch (c[i]) {
                            case 'G':
                                c[i] = 'C';
                                break;
                            case 'T':
                                c[i] = 'A';
                                break;
                            case 'C':
                                c[i] = 'G';
                                break;
                            case 'A':
                                c[i] = 'T';
                                break;
                            default:
                                break;
                        }
                    }

                    return new String(c);
                })
                .toList());
    }
}
