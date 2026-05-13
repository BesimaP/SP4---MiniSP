package view;

import controller.RoundHistoryController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.util.ArrayList;

public class RoundHistoryView {

    private RoundHistoryController controller = new RoundHistoryController();

    public void show(Stage stage) {

        // Titel
        Label roundHistoryLabel = new Label("Your round history:");

        // Liste der viser runder
        ListView<String> roundList = new ListView<>();

        // Hent data fra controller
        ArrayList<String> rounds = controller.initialize();

        // Vis antal runder ← tilføjet her efter rounds er hentet
        Label totalLabel = new Label("Total rounds: " + rounds.size());

        // Tilføj runder til listen
        for (String round : rounds) {
            roundList.getItems().add(round);
        }

        // Hvis ingen runder
        if (rounds.isEmpty()) {
            roundList.getItems().add("No rounds found");
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
                roundHistoryLabel,
                totalLabel, // ← tilføjet her
                roundList,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout, 400, 400);
        stage.setTitle("Simpl — Round History");
        stage.setScene(scene);
        stage.show();
    }
}