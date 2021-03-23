package model.expressions;

import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InterpretorException;
import model.exceptions.OperandMustBeIntegerException;
import model.exceptions.OperandTypeMismatchException;
import model.types.BooleanType;
import model.types.IntType;
import model.types.Type;
import model.values.BooleanValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    static final IntType intType = new IntType();
    static final BooleanType booleanType = new BooleanType();
    Expression leftExpression, rightExpression;
    RelationalOperation operation;

    public RelationalExpression(Expression leftExpression, Expression rightExpression, RelationalOperation operation) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) throws InterpretorException {
        int n1 = ((IntValue)leftExpression.evaluate(symbolTable, heap)).getValue();
        int n2 = ((IntValue)rightExpression.evaluate(symbolTable, heap)).getValue();

        BooleanValue result = (BooleanValue) booleanType.getDefaultValue();
        switch (operation) {
            case EQUAL ->                   result = new BooleanValue(n1 == n2);
            case NOT_EQUAL ->               result = new BooleanValue(n1 != n2);
            case LESS_THAN ->               result = new BooleanValue(n1 < n2);
            case GREATER_THAN ->            result = new BooleanValue(n1 > n2);
            case LESS_THAN_OR_EQUAL ->      result = new BooleanValue(n1 <= n2);
            case GREATER_THAN_OR_EQUAL ->   result = new BooleanValue(n1 >= n2);
        }
        return result;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!leftExpression.typeCheck(typeEnvironment).equals(intType))
            throw new InterpretorException("Second operand must be an integer");

        if (!rightExpression.typeCheck(typeEnvironment).equals(intType))
            throw new InterpretorException("First operand must be an integer");

        return booleanType;
    }

    @Override
    public String toString() {
        String result = leftExpression.toString() + " ";
        switch (operation) {
            case LESS_THAN -> {
                result += "<";
            }
            case LESS_THAN_OR_EQUAL -> {
                result += "<=";
            }
            case EQUAL -> {
                result += "==";
            }
            case NOT_EQUAL -> {
                result += "!=";
            }
            case GREATER_THAN -> {
                result += ">";
            }
            case GREATER_THAN_OR_EQUAL -> {
                result += ">=";
            }
        }
        result = result + " " + rightExpression.toString();
        return result;
    }
}
