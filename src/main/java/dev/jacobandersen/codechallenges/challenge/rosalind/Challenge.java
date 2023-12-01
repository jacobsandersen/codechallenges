package dev.jacobandersen.codechallenges.challenge.rosalind;

import dev.jacobandersen.codechallenges.Problem;

public abstract class Challenge extends Problem {
    private final Topic topic;
    private final int number;
    private final String challengeName;

    public Challenge(Topic topic, int number, String challengeName) {
        super("Rosalind");
        this.topic = topic;
        this.number = number;
        this.challengeName = challengeName;
    }

    @Override
    public String getInputPath() {
        return String.format("topic/%s/%03d.%s.txt", topic.name().toLowerCase(), number, challengeName.replaceAll(" ", "-").toLowerCase());
    }

    public abstract String solution();

    @Override
    public void run() {
        System.out.printf("%s - %s - %s%n", challenge, topic, challengeName);
        System.out.println(solution());
    }
}
