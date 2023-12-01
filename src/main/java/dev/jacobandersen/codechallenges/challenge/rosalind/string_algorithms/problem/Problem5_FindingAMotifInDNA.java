package dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.problem;

import dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.StringAlgorithmChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Problem5_FindingAMotifInDNA extends StringAlgorithmChallenge {
    public Problem5_FindingAMotifInDNA() {
        super(5, "Finding a Motif in DNA");
    }

    private List<Integer> allPositionsOfSubstringOccurrence(String haystack, String substring) {
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < haystack.length(); i++) {
            for (int j = i; j < haystack.length(); j++) {
                String haystackSubstring = haystack.substring(i, j + 1);
                if (haystackSubstring.equals(substring)) {
                    positions.add(i + 1);
                }
            }
        }

        return positions;
    }

    @Override
    public String solution() {
        List<String> lines = getInputLinesStream().filter(line -> !line.isBlank()).toList();
        return allPositionsOfSubstringOccurrence(lines.get(0), lines.get(1)).stream().map(String::valueOf).collect(Collectors.joining(" "));
    }
}
