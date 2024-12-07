package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day7;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;
import dev.jacobandersen.codechallenges.util.Pair;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

public class Day7 extends Day {
    public Day7() {
        super(2024, 7, "Bridge Repair");
    }

    private boolean solve(long testValue, List<String> operators, List<Long> numbers) {
        List<List<String>> operatorOrders = CombinatoricsUtil.selfCartesianProduct(operators, numbers.size() - 1);
        for (List<String> operatorOrder : operatorOrders) {
            Deque<String> operatorStack = new ArrayDeque<>(operatorOrder);

            Deque<Long> stack = new ArrayDeque<>();
            for (long number : numbers) {
                stack.push(number);

                while (stack.size() == 2) {
                    long num1 = stack.pop(), num2 = stack.pop();
                    String nextOperator = operatorStack.pop();
                    switch (nextOperator) {
                        case "+":
                            stack.addFirst(num1 + num2);
                            break;
                        case "*":
                            stack.addFirst(num1 * num2);
                            break;
                        case "||":
                            stack.addFirst(Long.parseLong(String.format("%d%d", num2, num1)));
                            break;
                    }
                }
            }

            if (stack.size() == 1) {
                if (stack.pop() == testValue) {
                    return true;
                }
            } else {
                throw new RuntimeException("Unexpected stack state: " + stack);
            }
        }

        return false;
    }

    private Stream<Pair<Long, List<Long>>> loadInput() {
        return getInputLinesStreamNoBlanks().map(line -> {
            final String[] split = line.split(":");
            final long testValue = Long.parseLong(split[0]);
            final List<Long> numbers = Arrays.stream(split[1].trim().split(" ")).map(Long::parseLong).toList();
            return Pair.of(testValue, numbers);
        });
    }

    @Override
    public String partOne() {
        return String.valueOf(loadInput().mapToLong(instance ->
                solve(instance.first(), List.of("+", "*"), instance.second()) ? instance.first() : 0).sum());
    }

    @Override
    public String partTwo() {
        return String.valueOf(loadInput().mapToLong(instance ->
                solve(instance.first(), List.of("+", "*", "||"), instance.second()) ? instance.first() : 0).sum());
    }
}
