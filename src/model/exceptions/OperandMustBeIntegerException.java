package model.exceptions;

public class OperandMustBeIntegerException extends InterpretorException{
    public OperandMustBeIntegerException() {
        super("Operand must be an integer");
    }
}
