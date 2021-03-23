import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ProgramState;
import model.adt.DictionaryImplementation;
import model.adt.StackImplementation;
import model.exceptions.InterpretorException;
import model.expressions.*;
import model.statements.*;
import model.types.BooleanType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BooleanValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.*;
import view.gui.Gui;
import view.textMenu.Command;
import view.textMenu.ExitCommand;
import view.textMenu.RunExample;
import view.textMenu.TextMenu;

public class Main{
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        Statement[] programs = new Statement[30];
        try {
            programs[0] = new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new AssignStatement("v", new ValueExpression(new IntValue(2))),
                    new PrintStatement(new VariableExpression("v"))
            );

            programs[1] = new CompoundStatement(
                    new VariableDeclarationStatement("a", new IntType()),
                    new VariableDeclarationStatement("b", new IntType()),
                    new AssignStatement("a", new ArithmeticExpression(
                            new ValueExpression(new IntValue(2)),
                            new ArithmeticExpression(
                                    new ValueExpression(new IntValue(3)),
                                    new ValueExpression(new IntValue(5)),
                                    ArithmeticOperation.MULTIPLY
                            ),
                            ArithmeticOperation.ADD
                    )),
                    new AssignStatement("b", new ArithmeticExpression(
                            new VariableExpression("a"),
                            new ValueExpression(new IntValue(1)),
                            ArithmeticOperation.ADD
                    )),
                    new PrintStatement(new VariableExpression("b"))
            );

            programs[2] = new CompoundStatement(
                    new VariableDeclarationStatement("a", new BooleanType()),
                    new VariableDeclarationStatement("v", new IntType()),
                    new AssignStatement("a", new ValueExpression(new BooleanValue(true))),
                    new IfStatement(
                            new VariableExpression("a"),
                            new AssignStatement("v", new ValueExpression(new IntValue(2))),
                            new AssignStatement("v", new ValueExpression(new IntValue(3)))
                    ),
                    new PrintStatement(new VariableExpression("v"))
            );

            programs[3] = new CompoundStatement(
                    new VariableDeclarationStatement("varf", new StringType()),
                    new AssignStatement("varf", new ValueExpression(new StringValue("res/test.in"))),
                    new OpenReadableFileStatement(new VariableExpression("varf")),
                    new VariableDeclarationStatement("varc", new IntType()),
                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                    new PrintStatement(new VariableExpression("varc")),
                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                    new PrintStatement(new VariableExpression("varc")),
                    new CloseReadableFileStatement(new VariableExpression("varf"))
            );

            programs[4] = new CompoundStatement(
                    new VariableDeclarationStatement("a", new IntType()),
                    new AssignStatement("a", new ValueExpression(new IntValue(5))),
                    new VariableDeclarationStatement("b", new IntType()),
                    new AssignStatement("b", new ValueExpression(new IntValue(6))),
                    new IfStatement(
                            new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), RelationalOperation.LESS_THAN),
                            new PrintStatement(new VariableExpression("a")),
                            new PrintStatement(new VariableExpression("b"))
                    )
            );

            programs[5] = new CompoundStatement(
                    new VariableDeclarationStatement("i", new IntType()),
                    new AssignStatement("i", new ValueExpression(new IntValue(4))),
                    new WhileStatement(
                            new RelationalExpression(
                                    new VariableExpression("i"),
                                    new ValueExpression(new IntValue(0)),
                                    RelationalOperation.GREATER_THAN
                            ),
                            new CompoundStatement(
                                    new PrintStatement(new VariableExpression("i")),
                                    new AssignStatement("i",
                                            new ArithmeticExpression(
                                                    new VariableExpression("i"),
                                                    new ValueExpression(new IntValue(1)),
                                                    ArithmeticOperation.SUBTRACT
                                            )
                                    )
                            )
                    ),
                    new PrintStatement(new VariableExpression("i"))
            );

            programs[6] = new CompoundStatement(
                    new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                    new NewStatement("a", new VariableExpression("v")),
                    new PrintStatement(new VariableExpression("v")),
                    new PrintStatement(new VariableExpression("a"))
            );

            programs[7] = new CompoundStatement(
                    new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                    new NewStatement("a", new VariableExpression("v")),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new PrintStatement(
                            new ArithmeticExpression(
                                    new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                    new ValueExpression(new IntValue(5)),
                                    ArithmeticOperation.ADD
                            )
                    )
            );

            programs[8] = new CompoundStatement(
                    new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                    new PrintStatement(
                            new ArithmeticExpression(
                                    new ReadHeapExpression(new VariableExpression("v")),
                                    new ValueExpression(new IntValue(5)),
                                    ArithmeticOperation.ADD
                            )
                    )
            );

            programs[9] = new CompoundStatement(
                    new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                    new NewStatement("a", new VariableExpression("v")),
                    new VariableDeclarationStatement("b", new ReferenceType(new ReferenceType(new ReferenceType(new IntType())))),
                    new NewStatement("b", new VariableExpression("a")),
                    new NewStatement("v", new ValueExpression(new IntValue(3))),
                    new NewStatement("a", new VariableExpression("v"))
            );

            programs[10] = new CompoundStatement(
                    new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                    new NewStatement("a", new VariableExpression("v")),
                    new NewStatement("v", new ValueExpression(new IntValue(30))),
                    new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
            );
            programs[11] = new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                    new AssignStatement("v", new ValueExpression(new IntValue(10))),
                    new NewStatement("a", new ValueExpression(new IntValue(22))),
                    new ForkStatement(new CompoundStatement(
                            new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                            new AssignStatement("v", new ValueExpression(new IntValue(32))),
                            new PrintStatement(new VariableExpression("v")),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                    )),
                    new PrintStatement(new VariableExpression("v")),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
            );
            /*programs[12] = new CompoundStatement(
                    new VariableDeclarationStatement("a", new IntType()),
                    new AssignStatement("a", new ValueExpression(new BooleanValue(false)))
            );*/

            programs[13] = new CompoundStatement(
                    new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                    new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
                    new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
                    new VariableDeclarationStatement("cnt", new IntType()),
                    new NewStatement("v1", new ValueExpression(new IntValue(2))),
                    new NewStatement("v2", new ValueExpression(new IntValue(3))),
                    new NewStatement("v3", new ValueExpression(new IntValue(4))),
                    new NewLatchStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                    new ForkStatement(new CompoundStatement(
                            new WriteHeapStatement("v1", new ArithmeticExpression(
                                    new ReadHeapExpression(new VariableExpression("v1")),
                                    new ValueExpression(new IntValue(10)),
                                    ArithmeticOperation.MULTIPLY
                            )),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                            new CountdownStatement("cnt")
                    )),
                    new ForkStatement(new CompoundStatement(
                            new WriteHeapStatement("v2", new ArithmeticExpression(
                                    new ReadHeapExpression(new VariableExpression("v2")),
                                    new ValueExpression(new IntValue(10)),
                                    ArithmeticOperation.MULTIPLY
                            )),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                            new CountdownStatement("cnt")
                    )),
                    new ForkStatement(new CompoundStatement(
                            new WriteHeapStatement("v3", new ArithmeticExpression(
                                    new ReadHeapExpression(new VariableExpression("v3")),
                                    new ValueExpression(new IntValue(10)),
                                    ArithmeticOperation.MULTIPLY
                            )),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))),
                            new CountdownStatement("cnt")
                    )),
                    new AwaitStatement("cnt"),
                    new PrintStatement(new ValueExpression(new IntValue(100))),
                    new CountdownStatement("cnt"),
                    new PrintStatement(new ValueExpression(new IntValue(100)))
            );

        } catch (InterpretorException e) {
            System.out.println(e.getMessage());
        }

        menu.addCommand(new ExitCommand("0", "exit"));

        for (int i =0; i < programs.length; i++) {
            if(programs[i] == null)
                continue;
            int currentNumber = i + 1;
            try {
                ProgramState programState = new ProgramState(new StackImplementation<>(), new DictionaryImplementation<>(), programs[i]);
                IRepository repository = new Repository(programState, "logs/log" + currentNumber + ".txt");
                Controller controller = new Controller(repository);
                Gui.addProgram(controller);
                programs[i].typeCheck(new DictionaryImplementation<>());
                menu.addCommand(new RunExample(Integer.toString(currentNumber), programs[i].toString(), controller));
            } catch (InterpretorException e) {
                System.out.println("Program didn't pass typechecking: ");
                System.out.println(programs[i].toString());
                System.out.println(e.getMessage());
            }
        }

        TextMenu chooseUI = new TextMenu();
        chooseUI.addCommand(new ExitCommand("0", "exit"));
        chooseUI.addCommand(new Command("1", "textUi") {
            @Override
            public void execute() {
                View view = new TextView(menu);
                view.run();
            }
        });
        chooseUI.addCommand(new Command("2", "gui") {
            @Override
            public void execute() {
                Gui.show(args);
            }
        });
        // chooseUI.executeCommand("2");
        chooseUI.show(true);

    }
}
