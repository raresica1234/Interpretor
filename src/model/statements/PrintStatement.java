package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.exceptions.InterpretorException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class PrintStatement implements Statement {
    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        Value value = expression.evaluate(state.getSymbolTable(), ProgramState.getHeap());
        ProgramState.getOutput().pushBack(value);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
