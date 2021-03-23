package model.exceptions;

public class VariableAlreadyDeclaredException extends InterpretorException {
    public VariableAlreadyDeclaredException(String id) {
        super("Variable '" + id + "'already declared");
    }
}
