package model.types;

import model.values.IntValue;

public class IntType implements Type {
    static final IntValue defaultValue = new IntValue(0);

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }
}
