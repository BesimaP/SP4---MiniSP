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

    public void show(Stage stage) {

        // Titel
        Label titleLabel = new Label("End your current round:");

        // Dropdown til resultat
        Label resultLabel = new Label("Select result:");
        ComboBox<String> resultBox = new ComboBox<>();
        resultBox.getItems().addAll(
                "POSITIVE",
                "NEGATIVE",
                "PENDING"
        );
        resultBox.setPromptText("Choose result:");

        // Besked og knapper
        Label messageLabel = new Label("");
        Button endButton = new Button("End Round");
        Button backButton = new Button("Back to dashboard");

        // End Round knap
        endButton.setOnAction(e -> {
            String result = resultBox.getValue();

            // Validering
            if (result == null) {
                messageLabel.setText("Please choose a result!");
                return;
            }

            // Afslut runden
            controller.handleEndRound(Session.getCurrentJourneyId(), result);
            messageLabel.setText("Round ended with result: " + result + " 🎉");
        });

        // Back knap
        backButton.setOnAction(e -> {
            new DashboardView().show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                resultLabel,
                resultBox,
                endButton,
                messageLabel,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — End Round");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}