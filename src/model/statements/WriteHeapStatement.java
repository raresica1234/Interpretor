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

public class WriteHeapStatement implements Statement {
    private String variable;
    private Expression expression;

    public WriteHeapStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = ProgramState.getHeap();

        Value varValue = symbolTable.lookup(variable);

        ReferenceValue var = (ReferenceValue)varValue;

        if(!heap.isDefined(var.getAddress()))
            throw new InterpretorException("Address not allocated in heap.");

        Value result = expression.evaluate(symbolTable, heap);

        if (!((ReferenceType)var.getType()).getInner().equals(result.getType()))
            throw new OperandTypeMismatchException();

        heap.update(var.getAddress(), result);

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!typeEnvironment.isDefined(variable))
            throw new VariableDoesNotExistException(variable);

        if(!(typeEnvironment.lookup(variable) instanceof ReferenceType))
            throw new InterpretorException("Variable type must be ReferenceType");

        if (!((ReferenceType)typeEnvironment.lookup(variable)).getInner().equals(expression.typeCheck(typeEnvironment)))
            throw new OperandTypeMismatchException();

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "writeHeap(" + variable + ", " + expression.toString() + ")";
    }
}
