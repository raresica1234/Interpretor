package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.adt.IStack;
import model.exceptions.AssignTypeMismatchException;
import model.exceptions.InterpretorException;
import model.exceptions.VariableDoesNotExistException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class AssignStatement implements Statement {
    String id;
    Expression expression;

    public AssignStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = ProgramState.getHeap();

        Value value = expression.evaluate(symbolTable, heap);
        symbolTable.update(id, value);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!typeEnvironment.isDefined(id))
            throw new VariableDoesNotExistException(id);

        Type varType = typeEnvironment.lookup(id);
        Type expressionType = expression.typeCheck(typeEnvironment);
        if (!varType.equals(expressionType))
            throw new AssignTypeMismatchException(id, expressionType);

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }
}
