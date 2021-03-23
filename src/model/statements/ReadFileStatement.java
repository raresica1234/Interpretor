package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.exceptions.*;
import model.expressions.Expression;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement {
    private static final IntType intType = new IntType();
    private static final StringType stringType = new StringType();
    private Expression expression;
    private String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IDictionary<StringValue, BufferedReader> fileTable = ProgramState.getFileTable();
        IHeap<Value> heap = ProgramState.getHeap();

        Value expressionValue = expression.evaluate(symbolTable, heap);

        StringValue fileName = (StringValue)expressionValue;

        if (!fileTable.isDefined(fileName))
            throw new FileNotOpenException(fileName.getValue());

        BufferedReader reader = fileTable.lookup(fileName);

        try {
            String line = reader.readLine();
            if (line.equals(""))
                symbolTable.update(variableName, new IntValue(0));
            else
                symbolTable.update(variableName, new IntValue(Integer.parseInt(line)));
        } catch (IOException e) {
            throw new InterpretorException("Could not read line from file");
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        if (!typeEnvironment.isDefined(variableName))
            throw new VariableDoesNotExistException(variableName);

        if (!typeEnvironment.lookup(variableName).equals(intType))
            throw new OperandMustBeIntegerException();

        if (!expression.typeCheck(typeEnvironment).equals(stringType))
            throw new OperandMustBeStringException();

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + variableName + ")";
    }
}
