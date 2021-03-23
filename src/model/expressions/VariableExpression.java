package model.expressions;

import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.InterpretorException;
import model.exceptions.VariableDoesNotExistException;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) throws InterpretorException {
        if (!symbolTable.isDefined(id))
            throw new VariableDoesNotExistException(id);
        return symbolTable.lookup(id);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        return typeEnvironment.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
