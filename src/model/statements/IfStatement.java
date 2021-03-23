package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.adt.IStack;
import model.exceptions.InterpretorException;
import model.exceptions.OperandMustBeBooleanException;
import model.expressions.Expression;
import model.types.BooleanType;
import model.types.Type;
import model.values.BooleanValue;
import model.values.Value;

public class IfStatement implements Statement {
    static final BooleanType booleanType = new BooleanType();
    Expression comparison;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression comparison, Statement thenStatement, Statement elseStatement) {
        this.comparison = comparison;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IStack<Statement> executionStack = state.getExecutionStack();
        IHeap<Value> heap = ProgramState.getHeap();

        Value value = comparison.evaluate(state.getSymbolTable(), heap);

        BooleanValue booleanValue = (BooleanValue) value;
        boolean comparisonValue = booleanValue.getValue();

        if (comparisonValue)
            executionStack.push(thenStatement);
        else
            executionStack.push(elseStatement);

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!comparison.typeCheck(typeEnvironment).equals(booleanType))
            throw new InterpretorException("Comparison must be of boolean type");
        return thenStatement.typeCheck(elseStatement.typeCheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return "IF (" + comparison.toString() + ") then (" + thenStatement.toString() + ") else (" + elseStatement.toString() + ")";
    }
}
