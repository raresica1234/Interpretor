package model.expressions;

import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.InterpretorException;
import model.types.BooleanType;
import model.types.Type;
import model.values.BooleanValue;
import model.values.Value;

public class LogicExpression implements Expression {
    static final BooleanType booleanType = new BooleanType();
    Expression leftExpression;
    Expression rightExpression;
    LogicOperation logicOperation;

    public LogicExpression(Expression leftExpression, Expression rightExpression, LogicOperation logicOperation) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.logicOperation = logicOperation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) throws InterpretorException {
        boolean n1 = ((BooleanValue)leftExpression.evaluate(symbolTable, heap)).getValue();
        boolean n2 = ((BooleanValue)rightExpression.evaluate(symbolTable, heap)).getValue();

        BooleanValue result = (BooleanValue)booleanType.getDefaultValue();

        switch (logicOperation) {
            case AND -> result = new BooleanValue(n1 && n2);
            case OR -> result = new BooleanValue(n1 || n2);
        }
        return result;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if(!leftExpression.typeCheck(typeEnvironment).equals(booleanType))
            throw new InterpretorException("Right operand must be a boolean");

        if(!rightExpression.typeCheck(typeEnvironment).equals(booleanType))
            throw new InterpretorException("Left operand must be a boolean");

        return booleanType;
    }

    @Override
    public String toString() {
        String result = leftExpression.toString() + " ";
        switch (logicOperation) {
            case AND -> {
                result += "&& ";
            }
            case OR -> {
                result += "|| ";
            }
        }
        result += rightExpression.toString();
        return result;
    }
}
