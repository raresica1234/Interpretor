package view.textMenu;

import controller.Controller;
import model.exceptions.InterpretorException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (InterpretorException e) {
            System.out.println(e.getMessage());
        }
    }
}
