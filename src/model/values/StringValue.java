package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value {
    private static final StringType type = new StringType();
    String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringValue) {
            StringValue val = (StringValue)obj;
            return val.value.equals(value);
        }
        return false;
    }

    @Override
    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
