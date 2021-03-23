package model.expressions;

import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.InterpretorException;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class ReadHeapExpression implements Expression {
    Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) throws InterpretorException {
        Value value = expression.evaluate(symbolTable, heap);
        ReferenceValue val = (ReferenceValue)value;

        if(!heap.isDefined(val.getAddress()))
            throw new InterpretorException("Address not allocated in heap.");

        return heap.lookup(val.getAddress());
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        Type type = expression.typeCheck(typeEnvironment);
        if (!(type instanceof ReferenceType))
            throw new InterpretorException("The readHeap argument must be a Reference Type");

        return ((ReferenceType) type).getInner();
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
