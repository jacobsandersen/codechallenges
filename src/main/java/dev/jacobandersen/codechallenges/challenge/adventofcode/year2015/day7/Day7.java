package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day7;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.StringUtil;
import dev.jacobandersen.codechallenges.util.UInt16;

import java.util.*;
import java.util.stream.Stream;

public class Day7 extends Day {
    private UInt16 wireASignal = null;

    public Day7() {
        super(2015, 7, "Some Assembly Required");
    }

    private void runCommands(WireContext ctx, List<String> finalSymbols) {
        Iterator<List<Token>> commandItr = ctx.commands.iterator();

        while (commandItr.hasNext()) {
            final List<Token> command = commandItr.next();
            if (command.size() == 3 && command.get(2).type() == Token.TokenType.SYMBOL) {
                Token arg = command.get(0);
                Token dest = command.get(2);

                if (finalSymbols.contains(dest.token())) {
                    commandItr.remove();
                    continue;
                }

                UInt16 maybeArg = arg.type() == Token.TokenType.SYMBOL ? ctx.wires.get(arg.token()) : arg.tokenAsUInt16();
                if (maybeArg != null) {
                    ctx.wires.put(command.get(2).token(), maybeArg);
                    commandItr.remove();
                }
            } else if (command.size() == 4 && command.get(0).type() == Token.TokenType.OPERATOR && command.get(3).type() == Token.TokenType.SYMBOL) {
                Token op = command.get(0);
                Token arg = command.get(1);
                Token dest = command.get(3);

                if (finalSymbols.contains(dest.token())) {
                    commandItr.remove();
                    continue;
                }

                UInt16 maybeArg = arg.type() == Token.TokenType.SYMBOL ? ctx.wires.get(arg.token()) : arg.tokenAsUInt16();
                if (maybeArg != null && op.tokenAsOperator() == Operator.NOT) {
                    ctx.wires.put(dest.token(), maybeArg.logicalNot());
                    commandItr.remove();
                }
            } else if (command.size() == 5 && command.get(1).type() == Token.TokenType.OPERATOR && command.get(4).type() == Token.TokenType.SYMBOL) {
                Token op = command.get(1);
                Token lhsArg = command.get(0);
                Token rhsArg = command.get(2);
                Token dest = command.get(4);

                if (finalSymbols.contains(dest.token())) {
                    commandItr.remove();
                    continue;
                }

                UInt16 maybeLhsArg = lhsArg.type() == Token.TokenType.SYMBOL ? ctx.wires.get(lhsArg.token()) : lhsArg.tokenAsUInt16();
                UInt16 maybeRhsArg = rhsArg.type() == Token.TokenType.SYMBOL ? ctx.wires.get(rhsArg.token()) : rhsArg.tokenAsUInt16();
                if (maybeLhsArg != null && maybeRhsArg != null) {
                    boolean didRun = false;

                    switch (op.tokenAsOperator()) {
                        case AND -> {
                            ctx.wires.put(dest.token(), maybeLhsArg.logicalAnd(maybeRhsArg));
                            didRun = true;
                        }
                        case OR -> {
                            ctx.wires.put(dest.token(), maybeLhsArg.logicalOr(maybeRhsArg));
                            didRun = true;
                        }
                        case RSHIFT -> {
                            ctx.wires.put(dest.token(), maybeLhsArg.rightShift(maybeRhsArg));
                            didRun = true;
                        }
                        case LSHIFT -> {
                            ctx.wires.put(dest.token(), maybeLhsArg.leftShift(maybeRhsArg));
                            didRun = true;
                        }
                    }

                    if (didRun) commandItr.remove();
                }
            }
        }
    }

    @Override
    public String partOne() {
        WireContext ctx = new WireContext(new HashMap<>(), new ArrayList<>(tokenize(getInputLinesStreamNoBlanks())));
        while (!ctx.commands.isEmpty()) {
            runCommands(ctx, Collections.emptyList());
        }

        wireASignal = ctx.wires.get("a");

        return String.valueOf((int) wireASignal.getValue());
    }

    @Override
    public String partTwo() {
        Map<String, UInt16> wires = new HashMap<>();
        wires.put("b", wireASignal);

        WireContext ctx = new WireContext(wires, new ArrayList<>(tokenize(getInputLinesStreamNoBlanks())));
        while (!ctx.commands.isEmpty()) {
            runCommands(ctx, List.of("b"));
        }

        return String.valueOf((int) ctx.wires.get("a").getValue());
    }

    private List<List<Token>> tokenize(Stream<String> input) {
        return input.map(instruction -> Arrays.stream(instruction.split(" ")).map(token -> {
            if (StringUtil.tryParseInt(token) != null) {
                return new Token(token, Token.TokenType.VALUE);
            } else if (Operator.fromSyntax(token) != null) {
                return new Token(token, Token.TokenType.OPERATOR);
            } else {
                return new Token(token, Token.TokenType.SYMBOL);
            }
        }).toList()).toList();
    }

    private enum Operator {
        NOT("NOT", 1),
        AND("AND", 2),
        OR("OR", 2),
        RSHIFT("RSHIFT", 2),
        LSHIFT("LSHIFT", 2),
        ASSIGNMENT("->", 0);

        private final String syntax;
        private final int argCount;

        Operator(String syntax, int argCount) {
            this.syntax = syntax;
            this.argCount = argCount;
        }

        public static Operator fromSyntax(String syntax) {
            return Arrays.stream(values()).filter(op -> op.syntax().equals(syntax)).findFirst().orElse(null);
        }

        public String syntax() {
            return syntax;
        }

        public int argCount() {
            return argCount;
        }
    }

    private record WireContext(Map<String, UInt16> wires, List<List<Token>> commands) {
    }

    private record Token(String token, TokenType type) {
        public UInt16 tokenAsUInt16() {
            if (type != TokenType.VALUE) {
                throw new RuntimeException("Tried to get token as uint-16 for non-value Token");
            }

            UInt16 out = new UInt16();
            out.setValue((char) Short.parseShort(token));

            return out;
        }

        public Operator tokenAsOperator() {
            if (type != TokenType.OPERATOR) {
                throw new RuntimeException("Tried to get token as Operator for non-operator Token");
            }

            return Operator.valueOf(token);
        }

        public enum TokenType {
            VALUE,
            SYMBOL,
            OPERATOR
        }
    }
}
