package repository;

import model.ProgramState;
import model.exceptions.InterpretorException;

import java.util.List;

public interface IRepository {

    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> programStates);
    void logProgramStatesExecution() throws InterpretorException;
    void logGarbageCollection() throws InterpretorException;
    void finishLogging();
    void addAll(List<ProgramState> programStates);
    int getSize();
    ProgramState getFirst();
}
