package model.expressions;

import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.InterpretorException;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression {
    Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) throws InterpretorException {
        return value;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
