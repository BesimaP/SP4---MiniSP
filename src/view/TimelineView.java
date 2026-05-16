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

    // TimelineView viser alle hændelser i patientens forløb kronologisk
    public class TimelineView {

        // Controller der henter alle hændelser fra databasen
        private TimelineController controller = new TimelineController();

        public void show(Stage stage) {

            // Header med titel og undertitel
            Label titleLabel = new Label("Timeline");
            titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
            Label subtitleLabel = new Label("All events in your journey");
            subtitleLabel.getStyleClass().add("subtitle-label");

            // VBox samler titel og undertitel med en bundkant som separator
            VBox header = new VBox(4, titleLabel, subtitleLabel);
            header.setPadding(new Insets(0, 0, 16, 0));
            header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

            // Hent alle hændelser fra databasen via controller
            ArrayList<Event> events = controller.initialize();

            // Vis det samlede antal hændelser
            Label totalLabel = new Label("TOTAL EVENTS: " + events.size());
            totalLabel.getStyleClass().add("section-label");

            // Liste der viser alle hændelser — én linje per hændelse
            ListView<String> eventList = new ListView<>();
            eventList.getStyleClass().add("modern-list");
            eventList.setPrefHeight(350);

            // Tilføj hver hændelse til listen — dato, type og beskrivelse
            for (Event event : events) {
                eventList.getItems().add(
                        event.getDate() + " — " + event.getType() + " — " + event.getDescription()
                );
            }

            // Vis en venlig besked hvis ingen hændelser findes endnu
            if (events.isEmpty()) {
                eventList.getItems().add("Your timeline is empty — log hormones, medication, or add appointments to see events here! ✨");
            }

            // Tilbage knap
            Button backButton = new Button("Back to dashboard");
            backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
            backButton.getStyleClass().add("secondary-button");
            backButton.setMaxWidth(Double.MAX_VALUE);

            // Hvad sker der når brugeren klikker Back
            backButton.setOnAction(e -> {
                new DashboardView().show(stage, Session.getCurrentPatient());
            });

            // VBox — lodret layout der samler alle elementer
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

            // StackPane centrerer kortet på skærmen med gradient baggrund
            StackPane root = new StackPane(layout);
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
            root.setPadding(new Insets(40));

            // Opret og vis skærmen
            Scene scene = new Scene(root, 650, 750);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
            stage.setTitle("Simpl — Timeline");
            stage.setScene(scene);
            stage.show();
        }
    }