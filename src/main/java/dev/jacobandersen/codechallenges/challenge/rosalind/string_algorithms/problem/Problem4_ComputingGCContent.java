package dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.problem;

import dev.jacobandersen.codechallenges.challenge.rosalind.string_algorithms.StringAlgorithmChallenge;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem4_ComputingGCContent extends StringAlgorithmChallenge {
    public Problem4_ComputingGCContent() {
        super(4, "Computing GC Content");
    }

    @Override
    public String solution() {
        Map<String, Long[]> numGCMap = new HashMap<>();
        List<String> lines = getInputLinesStream().filter(line -> !line.isBlank()).toList();

        String curId = "";
        for (String line : lines) {
            if (line.startsWith(">")) {
                curId = line.substring(1);
                numGCMap.put(curId, new Long[]{0L,0L});
                continue;
            }

            Long[] values = numGCMap.get(curId);
            if (values == null) {
                continue;
            }

            values[0] += line.chars().filter(c -> c == 'G' || c == 'C').count();
            values[1] += line.length();

            numGCMap.put(curId, values);
        }

        return String.valueOf(numGCMap.entrySet().stream()
                .map(entry -> Map.entry(
                        entry.getKey(),
                        ((double)entry.getValue()[0]/entry.getValue()[1]) * 100
                ))
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .stream().findFirst().orElseThrow());
    }
}
