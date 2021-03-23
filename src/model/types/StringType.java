package model.types;

import model.values.StringValue;

public class StringType implements Type {
    private static final StringValue defaultValue = new StringValue("");

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }
}
