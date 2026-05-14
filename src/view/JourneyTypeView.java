package view;

import controller.JourneyTypeController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;

public class JourneyTypeView {

    private JourneyTypeController controller = new JourneyTypeController();

    public void show(Stage stage) {

        // Titel
        Label titleLabel = new Label("Your journey");
        titleLabel.getStyleClass().add("title-label");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Undertitel
        Label subtitleLabel = new Label("Choose your treatment path");
        subtitleLabel.getStyleClass().add("subtitle-label");
        subtitleLabel.setMaxWidth(Double.MAX_VALUE);
        subtitleLabel.setAlignment(Pos.CENTER);

        // Dropdown
        Label journeyLabel = new Label("Journey type:");
        journeyLabel.getStyleClass().add("field-label");
        ComboBox<String> journeyBox = new ComboBox<>();
        journeyBox.getItems().addAll(
                "Fertility",
                "Cancer",
                "Rehabilitation",
                "Psychiatry",
                "Other"
        );
        journeyBox.setPromptText("Choose journey type:");
        journeyBox.getStyleClass().add("modern-field");
        journeyBox.setMaxWidth(Double.MAX_VALUE);

        // Besked og knapper
        Label messageLabel = new Label("");
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("primary-button");
        continueButton.setMaxWidth(Double.MAX_VALUE);

        // Continue knap
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
        layout.getStyleClass().add("login-card");
        layout.setMaxWidth(380);
        layout.setPrefWidth(380);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(
                titleLabel,
                subtitleLabel,
                journeyLabel,
                journeyBox,
                continueButton,
                messageLabel
        );

        // StackPane med gradient baggrund
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Vis skærmen
        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
        stage.setTitle("Simpl — Select Journey Type");
        stage.setScene(scene);
        stage.show();
    }
}