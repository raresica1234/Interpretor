package model.exceptions;

public class FileNotOpenException extends InterpretorException{
    public FileNotOpenException(String fileName) {
        super("File " + fileName + " is not currently open.");
    }
}
