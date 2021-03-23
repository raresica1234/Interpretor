package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.exceptions.InterpretorException;
import model.exceptions.VariableDoesNotExistException;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

public class CountdownStatement implements Statement {
    private static final IntType intType = new IntType();
    private String id;

    public CountdownStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        var symbolTable = state.getSymbolTable();
        var output = ProgramState.getOutput();
        var latchTable = ProgramState.getLatchTable();
        IntValue value = (IntValue) symbolTable.lookup(id);
        int address = value.getValue();
        if (!latchTable.isDefined(address))
            throw new InterpretorException("Variable does not exist in latch table");

        int val = latchTable.lookup(address);
        if(val > 0)
            latchTable.update(address, val - 1);
        output.pushBack(new IntValue(state.getId()));
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!typeEnvironment.isDefined(id))
            throw new VariableDoesNotExistException(id);

        if (!typeEnvironment.lookup(id).equals(intType))
            throw new InterpretorException("Variable must be type int for await");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "countDown(" + id + ")";
    }
}
