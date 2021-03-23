package view.gui;


import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.adt.DictionaryImplementation;
import model.adt.IList;
import model.adt.ListImplementation;
import model.exceptions.InterpretorException;


public class Gui extends Application {
    private static IList<Controller> programs = new ListImplementation<>();

    public static void show(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setResizable(false);
        window.setTitle("Interpretor - Select program");

        ListView<String> programList = new ListView<>();
        programList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        for (int i=0; i < programs.getSize(); i++) {
            try {
                programs.get(i).getInitialProgramState().getProgram().typeCheck(new DictionaryImplementation<>());
                programList.getItems().add(i, programs.get(i).getInitialProgramState().getProgram().toString());
            } catch (InterpretorException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Program failed typechecking");
                alert.setContentText(programs.get(i).getInitialProgramState().getProgram().toString() + "\n" + e.getMessage());
                alert.showAndWait();
            }
        }

        Label label = new Label();
        label.setText("Choose a program:");

        VBox programListLayout = new VBox(2);
        programListLayout.setAlignment(Pos.TOP_CENTER);
        programListLayout.getChildren().addAll(label, programList);


        Button run = new Button();
        run.setText("Run program");
        run.setOnMouseClicked(e -> {
            int select = programList.getSelectionModel().getSelectedIndex();
            if (select != -1) {
                ProgramWindow.display(programs.get(select));
            }
        });


        VBox layout = new VBox(2);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(programListLayout, run);

        Scene scene = new Scene(layout, 1300, 450);
        window.setScene(scene);
        window.show();
    }
    public static void addProgram(Controller controller) {
        programs.pushBack(controller);
    }

}
