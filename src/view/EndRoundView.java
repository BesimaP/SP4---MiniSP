package view;

import controller.EndRoundController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;

public class EndRoundView {
    private EndRoundController controller = new EndRoundController();

    public void show(Stage stage){

        ComboBox<String> resultBox = new ComboBox<>();
        resultBox.getItems().addAll(
                "POSITIVE",
                "NEGATIVE",
                "PENDING"
        );
        resultBox.setPromptText("Choose result:");

        Label messageLabel = new Label();

        Button endButton = new Button();
        endButton.setOnAction(e -> {
            String result = resultBox.getValue();
            if (result == null) {
                messageLabel.setText("Please choose a result!");
            } else {
                controller.handleEndRound(Session.getCurrentJourneyId(), result);
                messageLabel.setText("Round ended!");
            }
        });

        Button backButton = new Button();
        backButton.setOnAction(e -> {
            new DashboardView().show(stage, Session.getCurrentPatient());
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                resultBox,
                endButton,
                backButton,
                messageLabel
        );

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("Simpl — End Round");
        stage.show();


    }

}
