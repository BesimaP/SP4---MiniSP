package view;

import controller.JourneyTypeController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;

public class JourneyTypeView {

    private JourneyTypeController controller = new JourneyTypeController();

    public void show(Stage stage) {

        Label journeyLabel = new Label("Select journey type:");
        ComboBox<String> journeyBox = new ComboBox<>();
        journeyBox.getItems().addAll(
                "Fertility",
                "Cancer",
                "Rehabilitation",
                "Psychiatry",
                "Other"
        );
        journeyBox.setPromptText("Choose journey type:");

        Label messageLabel = new Label("");
        Button continueButton = new Button("Continue");

        continueButton.setOnAction(e -> {
            String type = journeyBox.getValue();

            if (type == null) {
                messageLabel.setText("Please select a journey type!");
                return;
            }

            // Gem forløbet og hent det nye journey_id
            int journeyId = controller.handleSelectJourney(
                    Session.getCurrentPatient().getId(), type
            );

            // Gem journey_id i Session
            Session.setCurrentJourneyId(journeyId);

            // Gå til dashboard
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                journeyLabel,
                journeyBox,
                continueButton,
                messageLabel
        );

        // Vis skærmen
        Scene scene = new Scene(layout, 300, 200);
        stage.setTitle("Simpl — Select Journey Type");
        stage.setScene(scene);
        stage.show();
    }
}