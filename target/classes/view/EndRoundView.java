package view;

import controller.EndRoundController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;

public class EndRoundView {

    private EndRoundController controller = new EndRoundController();

    public void show(Stage stage) {

        // Header
        Label titleLabel = new Label("End round");
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label subtitleLabel = new Label("End your current treatment round");
        subtitleLabel.getStyleClass().add("subtitle-label");

        VBox header = new VBox(4, titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 16, 0));
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // Felter
        Label resultLabel = new Label("RESULT");
        resultLabel.getStyleClass().add("field-label");
        ComboBox<String> resultBox = new ComboBox<>();
        resultBox.getItems().addAll(
                "POSITIVE",
                "NEGATIVE",
                "PENDING"
        );
        resultBox.setPromptText("Choose result:");
        resultBox.setStyle("-fx-font-size: 10px;");
        resultBox.getStyleClass().add("modern-field");
        resultBox.setMaxWidth(Double.MAX_VALUE);

        // Besked
        Label messageLabel = new Label("");
        messageLabel.getStyleClass().add("subtitle-label");

        // Ryd beskeden når brugeren vælger
        resultBox.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));

        // Knapper
        Button endButton = new Button("End round");
        endButton.getStyleClass().add("primary-button");
        endButton.setMaxWidth(Double.MAX_VALUE);

        Button backButton = new Button("Back to dashboard");
        backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);

        // End round MED bekræftelse
        endButton.setOnAction(e -> {
            String result = resultBox.getValue();

            if (result == null) {
                messageLabel.setText("Please choose a result!");
                return;
            }

            // Bekræftelses-dialog så brugeren ikke ved et uheld afslutter runden
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm");
            confirm.setHeaderText("End this round?");
            confirm.setContentText("Result: " + result + "\nThis cannot be undone.");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    controller.handleEndRound(Session.getCurrentJourneyId(), result);
                    messageLabel.setText("Round ended with result: " + result + " 🎉");
                }
            });
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
                resultLabel,
                resultBox,
                endButton,
                messageLabel,
                backButton
        );

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Samme størrelse som dashboard
        Scene scene = new Scene(root, 650, 750);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());

        stage.setTitle("Simpl — End Round");
        stage.setScene(scene);
        stage.show();
    }
}