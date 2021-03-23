package model.types;

import model.values.BooleanValue;

public class BooleanType implements Type {
    static final BooleanValue defaultValue = new BooleanValue(false);

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }
}
