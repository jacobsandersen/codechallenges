package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day8;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CircularFifoQueue;
import dev.jacobandersen.codechallenges.util.MathUtil;
import dev.jacobandersen.codechallenges.util.TreeNode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 extends Day {
    public Day8() {
        super(2023, 8, "Haunted Wasteland");
    }

    public CircularFifoQueue<Character> getInstructions(String instructions) {
        CircularFifoQueue<Character> out = new CircularFifoQueue<>();

        for (char inst : instructions.toCharArray()) {
            out.add(inst);
        }

        return out;
    }

    public Map<String, String[]> getNodeConnectionMap(List<String> data) {
        return data.stream().map(line -> {
            final String[] parts = line.split(" = ");
            final String[] connections = parts[1].split(", ");

            return Map.entry(parts[0], new String[]{
                    connections[0].substring(1),
                    connections[1].substring(0, connections[1].length() - 1)
            });
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<TreeNode> getNetwork(List<String> data, Pattern nodeKeyPattern) {
        Map<String, String[]> connectionMap = getNodeConnectionMap(data);
        Map<String, TreeNode> nodeMap = connectionMap.keySet().stream().collect(Collectors.toMap(key -> key, TreeNode::new));

        return nodeMap.keySet().parallelStream()
                .filter(key -> nodeKeyPattern.matcher(key).matches())
                .map(key -> buildSubtree(nodeMap.get(key), connectionMap, nodeMap, new HashSet<>()))
                .toList();
    }

    private TreeNode buildSubtree(TreeNode root, Map<String, String[]> connectionMap, Map<String, TreeNode> nodeMap, Set<TreeNode> visited) {
        if (root == null || !visited.add(root)) {
            return root;
        }

        String[] connections = connectionMap.get(root.getValue());
        if (connections == null) {
            throw new IllegalStateException("Did not find connections for TreeNode.");
        }

        root.setLeft(buildSubtree(nodeMap.get(connections[0]), connectionMap, nodeMap, visited));
        root.setRight(buildSubtree(nodeMap.get(connections[1]), connectionMap, nodeMap, visited));

        return root;
    }

    @Override
    public String partOne() {
        List<String> data = getInputLinesNoBlanks();
        CircularFifoQueue<Character> instructions = getInstructions(data.get(0));

        long steps = 0;

        TreeNode current = getNetwork(data.subList(1, data.size()), Pattern.compile("AAA")).get(0);
        while (!current.getValue().equals("ZZZ")) {
            Character inst = instructions.next();
            if (inst == 'L') {
                current = current.getLeft();
            } else if (inst == 'R') {
                current = current.getRight();
            }

            steps++;
        }

        return String.valueOf(steps);
    }

    @Override
    public String partTwo() {
        List<String> data = getInputLinesNoBlanks();

        return String.valueOf(getNetwork(data.subList(1, data.size()), Pattern.compile(".{2}A")).parallelStream().map(tree -> {
            CircularFifoQueue<Character> instructions = getInstructions(data.get(0));

            long steps = 0;

            TreeNode current = tree;
            while (!current.getValue().endsWith("Z")) {
                Character inst = instructions.next();
                if (inst == 'L') {
                    current = current.getLeft();
                } else if (inst == 'R') {
                    current = current.getRight();
                }

                steps++;
            }

            return steps;
        }).reduce(1L, MathUtil::lcm));
    }
}
