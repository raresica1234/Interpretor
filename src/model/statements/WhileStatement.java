package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.InterpretorException;
import model.exceptions.OperandMustBeBooleanException;
import model.expressions.Expression;
import model.types.BooleanType;
import model.types.Type;
import model.values.BooleanValue;
import model.values.Value;

public class WhileStatement implements Statement {
    private static final BooleanType booleanType = new BooleanType();
    private final Expression condition;
    private final Statement code;

    public WhileStatement(Expression condition, Statement code) {
        this.condition = condition;
        this.code = code;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = ProgramState.getHeap();
        Value val = condition.evaluate(symbolTable, heap);

        BooleanValue value = (BooleanValue)val;

        if (value.getValue()) {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(code);
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!condition.typeCheck(typeEnvironment).equals(booleanType))
            throw new OperandMustBeBooleanException();

        code.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "(while (" + condition.toString() + ") " + code.toString() + ")";
    }
}
