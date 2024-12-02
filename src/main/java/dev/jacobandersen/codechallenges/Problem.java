package dev.jacobandersen.codechallenges;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public abstract class Problem {
    protected final String challenge;

    public Problem(String challenge) {
        this.challenge = challenge;
    }

    public abstract String getInputPath();

    public final File getInputFile() {
        final String inputPath = getInputPath();

        try {
            URL fileUrl = getClass().getResource(String.format("/inputdata/challenge/%s/%s", challenge.replaceAll(" ", "").toLowerCase(), inputPath));
            if (fileUrl == null) {
                throw new NullPointerException(String.format("Cannot find input file for challenge %s: %s", challenge, inputPath));
            }

            return new File(fileUrl.toURI());
        } catch (NullPointerException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final List<String> getInputLines() {
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(getInputFile())) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    public final Stream<String> getInputLinesStream() {
        return getInputLines().stream();
    }

    public final Stream<String> getInputLinesStreamNoBlanks() {
        return getInputLinesStream().filter(line -> !line.isBlank());
    }

    public final List<String> getInputLinesNoBlanks() {
        return getInputLinesStreamNoBlanks().toList();
    }

    public final Stream<Integer> getInputIntegersStream() {
        return getInputLinesStreamNoBlanks().map(Integer::parseInt);
    }

    public final List<Integer> getInputIntegers() {
        return getInputIntegersStream().toList();
    }

    public abstract void run();
}
