package model.expressions;

import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.InterpretorException;
import model.types.Type;
import model.values.Value;

public interface Expression {
    Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) throws InterpretorException;
    Type typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException;
}
