package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.exceptions.InterpretorException;
import model.types.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws InterpretorException;
    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException;
}
