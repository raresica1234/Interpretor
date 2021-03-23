package model;

import model.adt.*;
import model.exceptions.InterpretorException;
import model.statements.Statement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ProgramState {
    static int id = 0;
    int currentId;
    IStack<Statement> executionStack;
    IDictionary<String, Value> symbolTable;
    static IDictionary<StringValue, BufferedReader> fileTable = new DictionaryImplementation<>();
    static IHeap<Value> heap = new HeapImplementation<>();
    static IList<Value> output = new ListImplementation<>();
    static ILatchTable latchTable = new LatchImplementation();
    Statement program;

    public ProgramState(IStack<Statement> executionStack, IDictionary<String, Value> symbolTable, Statement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.program = program;
        executionStack.push(program);
        currentId = id++;
    }

    public ProgramState oneStep() throws InterpretorException {
        if (executionStack.isEmpty())
            throw new InterpretorException("Execution stack is empty");
        Statement statement = executionStack.pop();
        return statement.execute(this);
    }

    public IStack<Statement> getExecutionStack() {
        return executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public static IList<Value> getOutput() {
        return output;
    }

    public static IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public static IHeap<Value> getHeap() {
        return heap;
    }

    public boolean isCompleted() {
        return executionStack.isEmpty();
    }

    public static ILatchTable getLatchTable() {
        return latchTable;
    }

    public int getId() {
        return currentId;
    }

    public Statement getProgram() {
        return program;
    }
}
