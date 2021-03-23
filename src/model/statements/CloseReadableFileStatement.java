package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.FileNotOpenException;
import model.exceptions.InterpretorException;
import model.exceptions.OperandMustBeStringException;
import model.expressions.Expression;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadableFileStatement implements Statement {
    private static final StringType stringType = new StringType();
    private Expression expression;

    public CloseReadableFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        Value val = expression.evaluate(state.getSymbolTable(), state.getHeap());

        StringValue fileName = (StringValue) val;
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName)) {
            throw new FileNotOpenException(fileName.getValue());
        }

        try {
            fileTable.lookup(fileName).close();
            fileTable.remove(fileName);
        } catch (IOException e) {
            throw new InterpretorException("Failed to close file.");
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!expression.typeCheck(typeEnvironment).equals(stringType))
            throw new OperandMustBeStringException();

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
