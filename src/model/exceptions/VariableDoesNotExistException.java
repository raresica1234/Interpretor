package model.exceptions;

public class VariableDoesNotExistException extends InterpretorException {

    public VariableDoesNotExistException(String id) {
        super("Variable '" + id +  "' does not exist.");
    }
}
