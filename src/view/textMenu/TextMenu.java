package view.textMenu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    private void printMenu() {
        String[] rows = new String[commands.values().size()];
        int n = 0;
        for (Command command : commands.values()) {
            rows[n++] = String.format("%4s: %s", command.getKey(), command.getDescription());
        }
        Arrays.sort(rows);
        Arrays.stream(rows).forEach(System.out::println);
    }

    public void show(boolean once) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("> ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid command");
                continue;
            }
            command.execute();
            if (once)
                break;
        }
    }

    public void executeCommand(String id) {
        commands.get(id).execute();
    }
}

