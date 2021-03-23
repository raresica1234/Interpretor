package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.exceptions.InterpretorException;
import model.types.Type;

public class NopStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        return typeEnvironment;
    }
}
