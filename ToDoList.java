import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Calendar;
import java.util.Date;

/**
 * Class that constructs a custom ToDoList using JavaFX.
 * @version 1.0
 * @author kasknani3
 */
public class ToDoList extends Application {

    private int tasksCompletedVar = 0;
    private int tasksLeftVar = 0;
    private ListView allTasks;
    private ObservableList tasks;
    private TextField taskName;
    private ComboBox hoursToComplete;
    private ComboBox typeOfTask;
    private Button enqueueTask;
    private Button dequeueTask;
    private Button closeProgram;
    private Stage primaryStage;

    /**
     * Driver method for the program.
     * @param args String[] that is the standard parameter for any main method
     */
    public static void main(String[] args) {
        launch(args); // method inside application class, sets up program as java fx
    }

    @Override
    public void start(Stage primaryStage) { // application calls this method
        // main javafx code here
        primaryStage.setTitle("To Do List");

        VBox topMenu = new VBox();
        HBox innerTopMenu = new HBox();
        Label tasksCompleted = new Label("Number of Tasks Completed:        0");
        Label tasksLeft = new Label("Number of Tasks Remaining:        0");
        Label title = new Label("To-Do List");
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 35);
        title.setFont(font);
        innerTopMenu.getChildren().addAll(tasksCompleted, tasksLeft);
        innerTopMenu.setPadding(new Insets(15, 30, 15, 30));
        innerTopMenu.setSpacing(30);
        topMenu.getChildren().addAll(title, innerTopMenu);

        tasks = FXCollections.observableArrayList();
        allTasks = new ListView<>(tasks);
        // allTasks.getItems().addAll("test1", "test2", "test3");

        HBox bottomMenu = new HBox();
        taskName = new TextField();
        taskName.setPromptText("Task Name");
        String[] typesOfTasks = {"Study", "Shop", "Cook", "Sleep"};
        typeOfTask = new ComboBox<>(FXCollections.observableArrayList(typesOfTasks));
        String[] hours = {"1", "2", "3", "4", "5"};
        hoursToComplete = new ComboBox<>(FXCollections.observableArrayList(hours));
        enqueueTask = new Button("Enqueue Task");
        dequeueTask = new Button("Dequeue Task");
        closeProgram = new Button("Close Program");
        bottomMenu.getChildren().addAll(taskName, typeOfTask, hoursToComplete, enqueueTask, dequeueTask, closeProgram);
        bottomMenu.setSpacing(15);
        bottomMenu.setPadding(new Insets(20, 0, 0, 0));

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setCenter(allTasks);
        borderPane.setBottom(bottomMenu);
        borderPane.setPadding(new Insets(10, 20, 40, 20));
        bottomMenu.setAlignment(Pos.CENTER);
        innerTopMenu.setAlignment(Pos.CENTER);
        topMenu.setAlignment(Pos.CENTER);

        enqueueTask.setOnAction((ActionEvent e) -> {
            if (!(taskName.getText().isEmpty()) && !(typeOfTask.getValue() == null)
                    && !(hoursToComplete.getValue() == null)) {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hoursToCompleteInt = Integer.parseInt(hoursToComplete.getValue().toString());
                calendar.add(Calendar.HOUR, hoursToCompleteInt);
                String toAdd = "Task: " + taskName.getText() + "     - Type: "
                        + typeOfTask.getValue() + " -     Complete by: " + calendar.getTime().toString();
                int result = 0;
                for (Object o : tasks) {
                    String oString = o.toString();
                    result = readString(oString);
                    if (result != 0) {
                        break;
                    }
                }
                if (result == 0) {
                    tasksLeftVar++;
                    tasksLeft.setText("Number of Tasks Remaining:        " + tasksLeftVar);
                    tasks.addAll(toAdd);
                    taskName.setText("");
                    hoursToComplete.valueProperty().set(null);
                    typeOfTask.valueProperty().set(null);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    String error = "This task already exists!";
                    alert.setContentText(error);
                    alert.show();
                    taskName.setText("");
                    hoursToComplete.valueProperty().set(null);
                    typeOfTask.valueProperty().set(null);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String error = "Please fill out all requested fields";
                alert.setContentText(error);
                alert.show();
            }
        }); // lambda expression demo

        dequeueTask.setOnAction(new EventHandler<ActionEvent>() { // anonymous inner class demo
            @Override
            public void handle(ActionEvent event) {
                if (tasks.size() > 0) {
                    tasksCompletedVar++;
                    tasksLeftVar--;
                    tasksLeft.setText("Number of Tasks Remaining:        " + tasksLeftVar);
                    tasksCompleted.setText("Number of Tasks Completed:        " + tasksCompletedVar);
                    allTasks.getItems().remove(0);
                }
            }
        });

        closeProgram.setOnAction(e -> closeProgram(primaryStage));
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(primaryStage);
        });

        Scene scene = new Scene(borderPane, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Helper method to see if the task is already in the list.
     * @param toRead String representing an individual entry in the list
     * @return int based on the result of the program
     */
    public int readString(String toRead) {
        if (toRead.contains(taskName.getText())) {
            if (toRead.contains(typeOfTask.getValue().toString())) {
                return 1;
            }
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Method that enacts procedure for when the program is closed.
     * @param primaryStage Stage parameter that is closed.
     */
    private void closeProgram(Stage primaryStage) {
        Boolean answer = ConfirmBox.display("Title", "Are you sure you want to exit?");
        if (answer) {
            primaryStage.close();
        }
    }
}