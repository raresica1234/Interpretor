package model.exceptions;

public class OperandTypeMismatchException extends InterpretorException {
    public OperandTypeMismatchException() {
        super("Operand type mismatch");
    }
}
