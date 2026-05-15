package view;

import controller.NewRoundController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

public class NewRoundView {

    private NewRoundController controller = new NewRoundController();

    public void show(Stage stage) {

        // Header
        Label titleLabel = new Label("Start new round");
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label subtitleLabel = new Label("Begin a new treatment round");
        subtitleLabel.getStyleClass().add("subtitle-label");

        VBox header = new VBox(4, titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 16, 0));
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // Felter
        Label roundNumberLabel = new Label("ROUND NUMBER");
        roundNumberLabel.getStyleClass().add("field-label");
        TextField roundNumberField = new TextField();
        roundNumberField.setPromptText("e.g. 1, 2, 3");
        roundNumberField.getStyleClass().add("modern-field");
        roundNumberField.setMaxWidth(Double.MAX_VALUE);

        Label dateLabel = new Label("START DATE");
        dateLabel.getStyleClass().add("field-label");
        Label dateValue = new Label(LocalDate.now().toString());
        dateValue.setStyle("-fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 11 15; -fx-background-color: #f5f5f5; -fx-background-radius: 10; -fx-border-color: #e0e0e0; -fx-border-radius: 10;");
        dateValue.setMaxWidth(Double.MAX_VALUE);

        // Besked
        Label messageLabel = new Label("");
        messageLabel.getStyleClass().add("subtitle-label");

        // Ryd beskeden når brugeren skriver
        roundNumberField.textProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));

        // Knapper
        Button saveButton = new Button("Start round");
        saveButton.getStyleClass().add("primary-button");
        saveButton.setMaxWidth(Double.MAX_VALUE);

        Button backButton = new Button("Back to dashboard");
        backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);

        // Save
        saveButton.setOnAction(e -> {
            if (roundNumberField.getText().isEmpty()) {
                messageLabel.setText("Please fill in round number!");
                return;
            }

            try {
                int roundNumber = Integer.parseInt(roundNumberField.getText());
                controller.handleStartRound(roundNumber);
                messageLabel.setText("Round started!");
            } catch (NumberFormatException ex) {
                messageLabel.setText("Round number must be a number!");
            }
        });

        // Back
        backButton.setOnAction(e -> {
            new DashboardView().show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(12);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(400);
        layout.setPrefWidth(400);
        layout.setPadding(new Insets(28));
        layout.getChildren().addAll(
                header,
                roundNumberLabel, roundNumberField,
                dateLabel, dateValue,
                saveButton,
                messageLabel,
                backButton
        );

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Samme størrelse som dashboard
        Scene scene = new Scene(root, 650, 750);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());

        stage.setTitle("Simpl — Start New Round");
        stage.setScene(scene);
        stage.show();
    }
}

