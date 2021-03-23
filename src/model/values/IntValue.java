package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {
    private final IntType type = new IntType();
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntValue) {
            IntValue val = (IntValue)obj;
            return val.value == value;
        }
        return false;
    }

    @Override
    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
