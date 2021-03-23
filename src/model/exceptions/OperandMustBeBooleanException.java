package model.exceptions;

public class OperandMustBeBooleanException extends InterpretorException {
    public OperandMustBeBooleanException() {
        super("Operand must be boolean");
    }
}
