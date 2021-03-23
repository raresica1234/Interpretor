package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.exceptions.InterpretorException;
import model.exceptions.VariableAlreadyDeclaredException;
import model.exceptions.VariableDoesNotExistException;
import model.types.Type;
import model.values.Value;

public class VariableDeclarationStatement implements Statement {
    String variable;
    Type type;

    public VariableDeclarationStatement(String variable, Type type) {
        this.variable = variable;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        state.getSymbolTable().add(variable, (Value)type.getDefaultValue());
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (typeEnvironment.isDefined(variable))
            throw new VariableAlreadyDeclaredException(variable);
        typeEnvironment.add(variable, type);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return type.toString() + " " + variable;
    }
}
