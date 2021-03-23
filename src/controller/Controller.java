package controller;

import model.ProgramState;
import model.adt.IHeap;
import model.adt.IList;
import model.adt.IStack;
import model.exceptions.InterpretorException;
import model.statements.Statement;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;
import repository.IRepository;
import repository.Repository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    List<Integer> getRecursiveAddresses(ReferenceValue value) {
        IHeap<Value> heap = ProgramState.getHeap();
        List<Integer> addresses = new LinkedList<>();
        addresses.add(value.getAddress());
        if (value.getLocationType() instanceof ReferenceType) {
            Value val = heap.lookup(value.getAddress());
            while (val instanceof ReferenceValue) {
                ReferenceValue current = (ReferenceValue)val;
                addresses.add(current.getAddress());
                val = heap.lookup(current.getAddress());
            }
        }
        return addresses;
    }


    List<Integer> getAddressFromSymbolTable(Collection<Value> symbolTableValues) {
        return symbolTableValues.stream()
                .filter(value -> value instanceof ReferenceValue)
                .map(value -> getRecursiveAddresses((ReferenceValue)value))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    Map<Integer,Value> getUsedAddresses(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(element -> symbolTableAddresses.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    void garbageCollection() {
        ProgramState.getHeap().setContent(
            repository.getProgramStateList().stream()
                    .map(programState ->
                            getUsedAddresses(getAddressFromSymbolTable(programState.getSymbolTable().getContent().values()), ProgramState.getHeap().getContent()).entrySet()
                    )
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }

    public void oneStepForAllPrograms() throws InterpretorException {
        try {
            List<ProgramState> programStates = repository.getProgramStateList();
            repository.logProgramStatesExecution();

            List<Callable<ProgramState>> callableList = programStates.stream()
                    .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                    .collect(Collectors.toList());

            List<ProgramState> newProgramStates = executor.invokeAll(callableList).stream()
                    .map(programStateFuture -> {
                        try {
                            return programStateFuture.get();
                        } catch (Exception e) {
                            System.out.println("One step failed: " + e.toString());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            repository.addAll(newProgramStates);
            if (newProgramStates.size() > 0)
                repository.logProgramStatesExecution();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    void removeCompletedProgramStates() {
        repository.setProgramStateList(repository.getProgramStateList().stream().filter(p -> !p.isCompleted()).collect(Collectors.toList()));
    }

    public void allStep() throws InterpretorException {
        executor = Executors.newFixedThreadPool(2);
        removeCompletedProgramStates();
        while (repository.getSize() > 0) {
            oneStepForAllPrograms();
            garbageCollection();
            repository.logGarbageCollection();
            removeCompletedProgramStates();
        }
        repository.logProgramStatesExecution();
        repository.finishLogging();
        executor.shutdownNow();
    }

    public void start() {
        executor = Executors.newFixedThreadPool(2);
        removeCompletedProgramStates();
    }

    public void finish() throws InterpretorException {
        repository.logProgramStatesExecution();
        repository.finishLogging();
        executor.shutdownNow();
    }

    public void garbageCollectionCall() throws InterpretorException {
        garbageCollection();
        repository.logGarbageCollection();
        removeCompletedProgramStates();
    }

    public ProgramState getInitialProgramState() {
        return repository.getFirst();
    }
    public List<ProgramState> getProgramStates() {
        return repository.getProgramStateList();
    }

    public boolean isFinished() {
        return repository.getSize() == 0;
    }
}
