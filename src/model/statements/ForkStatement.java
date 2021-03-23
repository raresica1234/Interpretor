package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.StackImplementation;
import model.exceptions.InterpretorException;
import model.types.Type;
import model.values.Value;

public class ForkStatement implements Statement {
    Statement inner;

    public ForkStatement(Statement inner) {
        this.inner = inner;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IDictionary<String, Value> newSymbolTable = state.getSymbolTable().deepCopy();
        return new ProgramState(new StackImplementation<>(), newSymbolTable, inner);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        inner.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork(" + inner.toString() + ")";
    }
}
