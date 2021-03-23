package model.types;

import model.values.ReferenceValue;

public class ReferenceType implements Type {
    Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReferenceType && inner.equals(((ReferenceType) obj).getInner());
    }

    @Override
    public Object getDefaultValue() {
        return new ReferenceValue(0, inner);
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
