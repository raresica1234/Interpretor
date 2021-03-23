package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.exceptions.FileHasAlreadyBeenOpenedException;
import model.exceptions.FileNotFoundException;
import model.exceptions.InterpretorException;
import model.exceptions.OperandMustBeStringException;
import model.expressions.Expression;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenReadableFileStatement implements Statement {
    private static final StringType stringType = new StringType();
    private final Expression expression;


    public OpenReadableFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        Value val = expression.evaluate(state.getSymbolTable(), ProgramState.getHeap());

        StringValue fileName = (StringValue)val;
        IDictionary<StringValue, BufferedReader> fileTable = ProgramState.getFileTable();
        if (fileTable.isDefined(fileName)) {
            throw new FileHasAlreadyBeenOpenedException(fileName.getValue());
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName.getValue()));
            fileTable.add(fileName, reader);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(fileName.getValue());
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
        return "openRFile(" + expression.toString() + ")";
    }
}
