package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.exceptions.InterpretorException;
import model.exceptions.VariableDoesNotExistException;
import model.expressions.Expression;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class NewLatchStatement implements Statement {
    private static final IntType intType = new IntType();
    private String id;
    private Expression expression;

    public NewLatchStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        var symbolTable = state.getSymbolTable();
        var heap = ProgramState.getHeap();
        var latchTable = ProgramState.getLatchTable();
        IntValue num = (IntValue)expression.evaluate(symbolTable, heap);

        int address = latchTable.allocate(num.getValue());
        symbolTable.update(id, new IntValue(address));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if(!typeEnvironment.isDefined(id))
            throw new VariableDoesNotExistException(id);

        if(!typeEnvironment.lookup(id).equals(intType))
            throw new InterpretorException("Variable type must be integer for latch.");

        if(!expression.typeCheck(typeEnvironment).equals(intType))
            throw new InterpretorException("Expression for new latch must be int");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "newLatch(" + id + ", " + expression.toString()+ ")";
    }
}
