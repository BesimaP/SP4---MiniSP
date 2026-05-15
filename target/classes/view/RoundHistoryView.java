package view;

import controller.RoundHistoryController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.util.ArrayList;

public class RoundHistoryView {

    private RoundHistoryController controller = new RoundHistoryController();

    public void show(Stage stage) {

        // Header
        Label titleLabel = new Label("Round history");
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label subtitleLabel = new Label("Your previous treatment rounds");
        subtitleLabel.getStyleClass().add("subtitle-label");

        VBox header = new VBox(4, titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 16, 0));
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // Hent data
        ArrayList<String> rounds = controller.initialize();

        Label totalLabel = new Label("TOTAL ROUNDS: " + rounds.size());
        totalLabel.getStyleClass().add("section-label");

        // Liste
        ListView<String> roundList = new ListView<>();
        roundList.getStyleClass().add("modern-list");
        roundList.setPrefHeight(300);

        for (String round : rounds) {
            roundList.getItems().add(round);
        }

        // Venlig tom-skærm besked
        if (rounds.isEmpty()) {
            roundList.getItems().add("No rounds yet — start your first round from the dashboard! 🌱");
        }

        // Back
        Button backButton = new Button("Back to dashboard");
        backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);

        backButton.setOnAction(e -> {
            new DashboardView().show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(12);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(450);
        layout.setPrefWidth(450);
        layout.setPadding(new Insets(28));
        layout.getChildren().addAll(
                header,
                totalLabel,
                roundList,
                backButton
        );

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Samme størrelse som dashboard
        Scene scene = new Scene(root, 650, 750);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());

        stage.setTitle("Simpl — Round History");
        stage.setScene(scene);
        stage.show();
    }
}