package model.expressions;

import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InterpretorException;
import model.exceptions.OperandMustBeIntegerException;
import model.exceptions.OperandTypeMismatchException;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression {
    static final IntType intType = new IntType();

    Expression leftExpression;
    Expression rightExpression;
    ArithmeticOperation arithmeticOperation;

    public ArithmeticExpression(Expression leftExpression, Expression rightExpression, ArithmeticOperation arithmeticOperation) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.arithmeticOperation = arithmeticOperation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) throws InterpretorException {
        int n1 = ((IntValue)leftExpression.evaluate(symbolTable, heap)).getValue();
        int n2 = ((IntValue)rightExpression.evaluate(symbolTable, heap)).getValue();

        IntValue result = (IntValue) intType.getDefaultValue();
        switch (arithmeticOperation) {
            case ADD ->         result = new IntValue(n1 + n2);
            case SUBTRACT ->    result = new IntValue(n1 - n2);
            case MULTIPLY ->    result = new IntValue(n1 * n2);
            case DIVIDE -> {
                if(n2 == 0)
                    throw new DivisionByZeroException();
                result = new IntValue(n1/n2);
            }
        }
        return result;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!leftExpression.typeCheck(typeEnvironment).equals(intType))
            throw new InterpretorException("First operand must be an integer");

        if (!rightExpression.typeCheck(typeEnvironment).equals(intType))
            throw new InterpretorException("Second operand must be an integer");

        return intType;
    }

    @Override
    public String toString() {
        String result = leftExpression.toString() + " ";
        switch (arithmeticOperation) {
            case ADD -> {
                result += "+ ";
            }
            case SUBTRACT -> {
                result += "- ";
            }
            case MULTIPLY -> {
                result += "* ";
            }
            case DIVIDE -> {
                result += "/ ";
            }
        }
        result += rightExpression.toString();
        return result;
    }
}
