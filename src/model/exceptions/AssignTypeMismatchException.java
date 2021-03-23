package model.exceptions;

import model.types.Type;

public class AssignTypeMismatchException extends InterpretorException {
    public AssignTypeMismatchException(String id, Type type) {
        super("Variable '" + id + "' can not be assigned value of type " + type.toString());
    }
}
