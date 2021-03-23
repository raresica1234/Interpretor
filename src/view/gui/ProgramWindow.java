package view.gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.adt.IList;
import model.adt.IStack;
import model.exceptions.InterpretorException;
import model.statements.Statement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramWindow {

    static TableView<Pair<Integer, Value>> heapTable;
    static TableView<Pair<Integer, Integer>> latchTable;
    static ListView<StringValue> fileTable;
    static ListView<Value> outputList;
    static TableView<Pair<String, Value>> symbolTableTable;
    static ListView<Statement> stackList;
    static ListView<Integer> programStatesList;

    static VBox heapTableLayout;
    static VBox latchTableLayout;
    static VBox fileTableLayout;
    static VBox outputListLayout;
    static VBox symbolTableLayout;
    static VBox stackListLayout;
    static VBox programStatesLayout;

    static int selectedProgramState = 0;

    public static void display(Controller controller) {
        controller.start();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Interpretor");
        window.setResizable(false);

        setup();

        updateData(controller);

        programStatesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        programStatesList.setOnMouseClicked(e -> {
            int index = programStatesList.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                selectedProgramState = index;
                updateData(controller);
            }
        });

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);

        GridPane.setConstraints(heapTableLayout, 0, 0);
        GridPane.setConstraints(fileTableLayout, 1, 0);
        GridPane.setConstraints(outputListLayout, 2, 0);
        GridPane.setConstraints(programStatesLayout, 0, 1);
        GridPane.setConstraints(symbolTableLayout, 1, 1);
        GridPane.setConstraints(stackListLayout, 2, 1);
        GridPane.setConstraints(latchTableLayout, 1, 2);

        pane.getChildren().addAll(heapTableLayout, fileTableLayout, outputListLayout, programStatesLayout, symbolTableLayout, stackListLayout, latchTableLayout);

        Button oneStep = new Button();
        oneStep.setText("Run one step");
        oneStep.setOnMouseClicked(e -> {
            try {
                if (!controller.isFinished()) {
                    controller.oneStepForAllPrograms();
                    controller.garbageCollectionCall();
                    if (controller.getProgramStates().size() <= selectedProgramState) {
                        selectedProgramState = 0;
                    }
                    updateData(controller);
                    if (controller.isFinished())
                        oneStep.setDisable(true);
                }
            } catch (InterpretorException interpretorException) {
                System.out.println(interpretorException.getMessage());
            }
        });

        VBox layout = new VBox(2);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(oneStep, pane);
        layout.setPadding(new Insets(10, 0, 0, 0));

        Scene scene = new Scene(layout, 800, 800);
        window.setScene(scene);
        window.showAndWait();
    }

    static void setup() {
        setupHeapTable();
        setupFileTable();
        setupOutput();
        setupSymbolTable();
        setupExecutionStackList();
        setupProgramStatesList();
        setupLatchTable();
    }

    static void setupHeapTable() {
        TableColumn<Pair<Integer, Value>, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(70);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair<Integer, Value>, Value> valueColumn = new TableColumn<>("Value");
        valueColumn.setMinWidth(150);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        heapTable = new TableView<>();
        heapTable.getColumns().addAll(addressColumn, valueColumn);

        heapTableLayout = new VBox(2);
        Label label = new Label();
        label.setText("Heap table:");
        heapTableLayout.setAlignment(Pos.TOP_CENTER);
        heapTableLayout.getChildren().addAll(label, heapTable);
    }

    static void setupLatchTable() {
        TableColumn<Pair<Integer, Integer>, Integer> addressColumn = new TableColumn<>("Location");
        addressColumn.setMinWidth(70);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair<Integer, Integer>, Integer> valueColumn = new TableColumn<>("Value");
        valueColumn.setMinWidth(150);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        latchTable = new TableView<>();
        latchTable.getColumns().addAll(addressColumn, valueColumn);

        latchTableLayout = new VBox(2);
        Label label = new Label();
        label.setText("Latch table:");
        latchTableLayout.setAlignment(Pos.TOP_CENTER);
        latchTableLayout.getChildren().addAll(label, latchTable);
    }

    static void setupFileTable() {
        fileTable = new ListView<>();
        fileTableLayout = new VBox(2);
        Label label = new Label();
        label.setText("File table:");
        fileTableLayout.setAlignment(Pos.TOP_CENTER);
        fileTableLayout.getChildren().addAll(label, fileTable);
    }

    static void setupOutput() {
        outputList = new ListView<>();
        outputListLayout = new VBox(2);
        Label label = new Label();
        label.setText("Output:");
        outputListLayout.setAlignment(Pos.TOP_CENTER);
        outputListLayout.getChildren().addAll(label, outputList);
    }

    static void setupSymbolTable() {
        TableColumn<Pair<String, Value>, String> variableColumn = new TableColumn<>("Variable");
        variableColumn.setMinWidth(70);
        variableColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair<String, Value>, Value> valueColumn = new TableColumn<>("Value");
        valueColumn.setMinWidth(150);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        symbolTableTable = new TableView<>();
        symbolTableTable.getColumns().addAll(variableColumn, valueColumn);

        symbolTableLayout = new VBox(2);
        Label label = new Label();
        label.setText("Symbol table:");
        symbolTableLayout.setAlignment(Pos.TOP_CENTER);
        symbolTableLayout.getChildren().addAll(label, symbolTableTable);
    }

    static void setupExecutionStackList() {
        stackList = new ListView<>();
        stackListLayout = new VBox(2);
        Label label = new Label();
        label.setText("Execution Stack:");
        stackListLayout.setAlignment(Pos.TOP_CENTER);
        stackListLayout.getChildren().addAll(label, stackList);
    }

    static void setupProgramStatesList() {
        programStatesList = new ListView<>();
        programStatesLayout = new VBox(2);
        Label label = new Label();
        label.setText("Program states:");
        programStatesLayout.setAlignment(Pos.TOP_CENTER);
        programStatesLayout.getChildren().addAll(label, programStatesList);
    }

    static void updateHeapTable() {
        IHeap<Value> heap = ProgramState.getHeap();
        ObservableList<Pair<Integer, Value>> heapData = FXCollections.observableArrayList();
        heap.getContent().keySet().forEach(key -> {
            heapData.add(new Pair<>(key, heap.lookup(key)));
        });
        heapTable.setItems(heapData);
    }

    static void updateLatchTable() {
        var latch = ProgramState.getLatchTable();
        ObservableList<Pair<Integer, Integer>> latchData = FXCollections.observableArrayList();
        latch.getContent().keySet().forEach(key -> {
            latchData.add(new Pair<>(key, latch.lookup(key)));
        });
        latchTable.setItems(latchData);
    }

    static void updateFileTable() {
        IDictionary<StringValue, BufferedReader> files = ProgramState.getFileTable();
        fileTable.getItems().clear();
        files.getContent().forEach((key, value) -> fileTable.getItems().add(key));
    }

    static void updateOutput() {
        IList<Value> output = ProgramState.getOutput();
        outputList.getItems().clear();
        for (int i = 0; i < output.getSize(); i++) {
            outputList.getItems().add(i, output.get(i));
        }
    }

    static void updateProgramList(Controller controller) {
        List<ProgramState> programStates = controller.getProgramStates();
        programStatesList.getItems().clear();
        for (int i = 0; i < programStates.size(); i++) {
            programStatesList.getItems().add(i, programStates.get(i).getId());
        }
        programStatesList.getSelectionModel().select(selectedProgramState);
    }

    static void updateExecutionStack(Controller controller) {
        if (controller.isFinished()) {
            stackList.getItems().clear();
            return;
        }
        IStack<Statement> stack = controller.getProgramStates().get(selectedProgramState).getExecutionStack().deepCopy();
        stackList.getItems().clear();
        while (!stack.isEmpty())
            stackList.getItems().add(stack.pop());
    }

    static void updateSymbolTable(Controller controller) {
        if (controller.isFinished()) {
            symbolTableTable.getItems().clear();
            return;
        }

        IDictionary<String, Value> symbolTable = controller.getProgramStates().get(selectedProgramState).getSymbolTable();
        ObservableList<Pair<String, Value>> symbolTableData = FXCollections.observableArrayList();
        symbolTable.getContent().keySet().forEach(key -> {
            symbolTableData.add(new Pair<>(key, symbolTable.lookup(key)));
        });
        symbolTableTable.setItems(symbolTableData);
    }

    static void updateData(Controller controller) {
        updateHeapTable();
        updateFileTable();
        updateOutput();
        updateProgramList(controller);
        updateExecutionStack(controller);
        updateSymbolTable(controller);
        updateLatchTable();
    }
}
