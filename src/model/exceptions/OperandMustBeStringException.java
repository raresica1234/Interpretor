package model.exceptions;

public class OperandMustBeStringException extends InterpretorException {
    public OperandMustBeStringException() {
        super("Operand must be a string");
    }
}
