package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IStack;
import model.exceptions.InterpretorException;
import model.types.Type;

import java.util.Arrays;

public class CompoundStatement implements Statement {
    Statement left;
    Statement right;

    public CompoundStatement(Statement left, Statement right) {
        this.left = left;
        this.right = right;
    }

    public CompoundStatement(Statement... statements) throws InterpretorException {
        if (statements.length < 2)
            throw new InterpretorException("A compound statement must formed from at least 2 statements.");

        this.left = statements[0];
        if (statements.length == 2)
            this.right = statements[1];
        else
            this.right = new CompoundStatement(Arrays.stream(statements).skip(1).toArray(Statement[]::new));
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        IStack<Statement> executionStack = state.getExecutionStack();
        executionStack.push(right);
        executionStack.push(left);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws InterpretorException {
        return right.typeCheck(left.typeCheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "; " + right.toString() + ")";
    }
}
