package model.exceptions;

public class DivisionByZeroException extends InterpretorException {

    public DivisionByZeroException() {
        super("Division by zero");
    }
}
