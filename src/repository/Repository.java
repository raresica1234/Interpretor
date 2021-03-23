package repository;

import model.ProgramState;
import model.exceptions.FileNotFoundException;
import model.exceptions.InterpretorException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {
    List<ProgramState> programStates;
    String filePath;
    boolean firstFileCall = true;
    PrintWriter logFile;

    public Repository(ProgramState state, String filePath) {
        programStates = new LinkedList<>();
        programStates.add(state);
        this.filePath = filePath;
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return programStates;
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramStatesExecution() throws InterpretorException {
        if (firstFileCall) {
            clearFile();
            try {
                logFile = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
            } catch (IOException e) {
                throw new FileNotFoundException(filePath);
            }
            firstFileCall = false;
        }

        logFile.write("=======================\n");
        programStates.forEach(p -> {
            logFile.write("\tProgram id: " + p.getId() + "\n");
            logFile.write("Execution Stack: \n" + p.getExecutionStack().toString() + "\n");
            logFile.write("Symbol table: " + p.getSymbolTable() + "\n");
        });
        logFile.write("Heap Table:\n" + ProgramState.getHeap().toString() + "\n");
        logFile.write("File Table:\n" + ProgramState.getFileTable().toString() + "\n");
        logFile.write("Latch Table:\n" + ProgramState.getLatchTable().toString() + "\n");
        logFile.write("Output:\n" + ProgramState.getOutput().toString() + "\n");
        logFile.write("=======================\n\n");
    }

    public void logGarbageCollection() throws InterpretorException {
        logFile.write("=======================\n");
        logFile.write("Garbage Collection:\n");
        programStates.forEach(p -> {
            logFile.write("Program id: " + p.getId() + "\n");
            logFile.write("Symbol table: " + p.getSymbolTable() + "\n");
        });
        logFile.write("Heap Table:\n" + ProgramState.getHeap().toString() + "\n");
        logFile.write("=======================\n\n");
    }

    @Override
    public void finishLogging() {
        logFile.close();
        firstFileCall = true;
    }

    @Override
    public void addAll(List<ProgramState> programStates) {
        this.programStates.addAll(programStates);
    }

    @Override
    public int getSize() {
        return programStates.size();
    }

    private void clearFile() throws InterpretorException {
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));
            logFile.write("");
            logFile.close();
        } catch (IOException e) {
            throw new FileNotFoundException(filePath);
        }
    }

    public ProgramState getFirst() {
        return programStates.get(0);
    }
}
