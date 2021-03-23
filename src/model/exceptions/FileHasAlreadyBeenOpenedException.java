package model.exceptions;

public class FileHasAlreadyBeenOpenedException extends InterpretorException{
    public FileHasAlreadyBeenOpenedException(String filename) {
        super("File " + filename + " has already been opened");
    }
}
