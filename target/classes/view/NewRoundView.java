package view;

import controller.NewRoundController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

public class NewRoundView {

    private NewRoundController controller = new NewRoundController();

    public void show(Stage stage) {

        // Felt til rundenummer
        TextField roundNumberField = new TextField();

        // Startdato vises automatisk
        Label dateLabel = new Label("Start date: " + LocalDate.now());

        // Besked og knapper
        Label messageLabel = new Label("");
        Button saveButton = new Button("Start Round");
        Button backButton = new Button("Back to Dashboard");

        // Gem knap
        saveButton.setOnAction(e -> {
            // Validering FØRST
            if (roundNumberField.getText().isEmpty()) {
                messageLabel.setText("Please fill in round number!");
                return;
            }

            // Derefter parse og gem
            int roundNumber = Integer.parseInt(roundNumberField.getText());
            controller.handleStartRound(roundNumber);
            messageLabel.setText("Round started!");
        });

        // Back knap
        backButton.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Round number:"), roundNumberField,
                dateLabel,
                saveButton,
                messageLabel,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — Start New Round");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}