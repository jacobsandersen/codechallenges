package dev.jacobandersen.codechallenges.util;

public class UInt16 {
    private int inner;

    public UInt16() {
        this((char) 0);
    }

    public UInt16(char value) {
        this.inner = value;
    }

    public void setValue(char value) {
        this.inner = value;
    }

    public char getValue() {
        return (char) this.inner;
    }

    public UInt16 logicalAnd(UInt16 other) {
        return new UInt16((char) (getValue() & other.getValue()));
    }

    public UInt16 logicalOr(UInt16 other) {
        return new UInt16((char) (getValue() | other.getValue()));
    }

    public UInt16 logicalNot() {
        return new UInt16((char) ~getValue());
    }

    public UInt16 rightShift(UInt16 other) {
        return new UInt16((char) (getValue() >> other.getValue()));
    }

    public UInt16 leftShift(UInt16 other) {
        return new UInt16((char) (getValue() << other.getValue()));
    }

    @Override
    public String toString() {
        return String.valueOf((int)getValue());
    }
}
