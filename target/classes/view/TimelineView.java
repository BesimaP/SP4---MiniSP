package view;

import controller.TimelineController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.Session;
import java.util.ArrayList;

public class TimelineView {

    private TimelineController controller = new TimelineController();

    public void show(Stage stage) {

        // Header
        Label titleLabel = new Label("Timeline");
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label subtitleLabel = new Label("All events in your journey");
        subtitleLabel.getStyleClass().add("subtitle-label");

        VBox header = new VBox(4, titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 16, 0));
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // Hent data
        ArrayList<Event> events = controller.initialize();

        Label totalLabel = new Label("TOTAL EVENTS: " + events.size());
        totalLabel.getStyleClass().add("section-label");

        // Liste
        ListView<String> eventList = new ListView<>();
        eventList.getStyleClass().add("modern-list");
        eventList.setPrefHeight(350);

        for (Event event : events) {
            eventList.getItems().add(
                    event.getDate() + " — " + event.getType() + " — " + event.getDescription()
            );
        }

        // Venlig tom-skærm besked
        if (events.isEmpty()) {
            eventList.getItems().add("Your timeline is empty — log hormones, medication, or add appointments to see events here! ✨");
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
        layout.setMaxWidth(500);
        layout.setPrefWidth(500);
        layout.setPadding(new Insets(28));
        layout.getChildren().addAll(
                header,
                totalLabel,
                eventList,
                backButton
        );

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Samme størrelse som dashboard
        Scene scene = new Scene(root, 650, 750);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());

        stage.setTitle("Simpl — Timeline");
        stage.setScene(scene);
        stage.show();
    }
}