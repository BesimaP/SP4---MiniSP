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

    // RoundHistoryView viser skærmen med historik over alle IVF-runder
    public class RoundHistoryView {

        // Controller der henter rundehistorik fra databasen
        private RoundHistoryController controller = new RoundHistoryController();

        public void show(Stage stage) {

            // Titel øverst på skærmen
            Label roundHistoryLabel = new Label("Your round history:");

            // Liste der viser alle runder — én linje per runde
            ListView<String> roundList = new ListView<>();

            // Hent alle runder fra databasen via controller
            ArrayList<String> rounds = controller.initialize();

            // Vis det samlede antal runder under titlen
            Label totalLabel = new Label("Total rounds: " + rounds.size());

            // Tilføj hver runde til listen
            for (String round : rounds) {
                roundList.getItems().add(round);
            }

            // Vis en venlig besked hvis ingen runder findes endnu
            if (rounds.isEmpty()) {
                roundList.getItems().add("No rounds found");
            }

            // Tilbage knap
            Button backButton = new Button("Back to Dashboard");
            backButton.setOnAction(e -> {
                DashboardView dashboard = new DashboardView();
                dashboard.show(stage, Session.getCurrentPatient());
            });

            // VBox — lodret layout der samler alle elementer
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            layout.getChildren().addAll(
                    roundHistoryLabel,
                    totalLabel,
                    roundList,
                    backButton
            );

            // Opret og vis skærmen — sizeToScene tilpasser vinduet til indholdet
            Scene scene = new Scene(layout);
            stage.setTitle("Simpl — Round History");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }
    }