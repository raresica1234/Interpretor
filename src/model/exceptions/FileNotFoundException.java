package model.exceptions;

public class FileNotFoundException extends InterpretorException {
    public FileNotFoundException(String path) {
        super("File " + path + " could not be found!");
    }
}
