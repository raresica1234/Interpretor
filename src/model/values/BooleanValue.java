package model.values;

import model.types.BooleanType;
import model.types.Type;

public class BooleanValue implements Value {
    private final BooleanType type = new BooleanType();
    boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BooleanValue) {
            BooleanValue val = (BooleanValue)obj;
            return val.value == value;
        }
        return false;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
