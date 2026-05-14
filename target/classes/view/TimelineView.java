package view;

import controller.TimelineController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.Session;
import java.util.ArrayList;

public class TimelineView {

    private TimelineController controller = new TimelineController();

    public void show(Stage stage) {

        // Titel
        Label titleLabel = new Label("Your timeline:");

        // Liste der viser hændelser
        ListView<String> eventList = new ListView<>();

        // Hent alle hændelser fra databasen
        ArrayList<Event> events = controller.initialize();

        // Tilføj hver hændelse til listen
        for (Event event : events) {
            eventList.getItems().add(
                    event.getDate() + " — " + event.getType() + " — " + event.getDescription()
            );
        }

        // Hvis ingen hændelser
        if (events.isEmpty()) {
            eventList.getItems().add("No events found");
        }

        // Back knap
        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                eventList,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — Timeline");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
