package dev.jacobandersen.codechallenges.challenge.rosalind;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Topic {
    STRING_ALGORITHMS,
    SORTING,
    SET_THEORY,
    PROBABLITY,
    POPULATION_DYNAMICS,
    PHYLOGENY,
    HEREDITY,
    GRAPHS,
    GRAPH_ALGORITHMS,
    GENOME_REARRANGEMENTS,
    GENOME_ASSEMBLY,
    DIVIDE_AND_CONQUER,
    COMPUTATIONAL_MASS_SPECTROMETRY,
    COMBINATORICS,
    ALIGNMENT;

    @Override
    public String toString() {
        return Arrays.stream(name().split("_")).map(part -> part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }
}
