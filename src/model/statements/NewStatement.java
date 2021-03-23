package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.InterpretorException;
import model.exceptions.OperandTypeMismatchException;
import model.exceptions.VariableDoesNotExistException;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class NewStatement implements Statement {
    private String variable;
    private Expression expression;

    public NewStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = ProgramState.getHeap();

        Value var = symbolTable.lookup(variable);

        ReferenceValue variableVal = (ReferenceValue) var;

        Value res = expression.evaluate(symbolTable, heap);

        int address = heap.allocate(res);

        symbolTable.update(variable, new ReferenceValue(address, variableVal.getLocationType()));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!typeEnvironment.isDefined(variable))
            throw new VariableDoesNotExistException(variable);

        Type varType = typeEnvironment.lookup(variable);
        Type expressionType = expression.typeCheck(typeEnvironment);
        if (!varType.equals(new ReferenceType(expressionType)))
            throw new OperandTypeMismatchException();

        return typeEnvironment;
    }


    @Override
    public String toString() {
        return "new(" + variable + ", " + expression.toString() + ")";
    }
}
