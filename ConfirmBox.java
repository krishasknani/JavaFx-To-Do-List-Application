import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * Class that constructs a custom ConfirmBox asking if the user really wants to close the program.
 * @version 1.0
 * @author kasknani3
 */
public class ConfirmBox {

    private static boolean answer;

    /**
     * Displays a ConfirmBox.
     * @param title String representing the title of the ConfirmBox
     * @param message String representing the message the ConfirmBox contains.
     * @return boolean conveying what the user selected on the ConfirmBox
     */
    public static boolean display(String title, String message) {
        Stage primaryStage = new Stage();

        primaryStage.initModality(Modality.APPLICATION_MODAL); // blocks user interaction
        primaryStage.setTitle(title);
        primaryStage.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        // Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            primaryStage.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            primaryStage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

        return answer;
    }

}
