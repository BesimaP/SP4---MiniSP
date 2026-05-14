package view;

import controller.JourneyTypeController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        Label subtitleLabel = new Label("Select your journey");
        subtitleLabel.getStyleClass().add("subtitle-label");
        subtitleLabel.setMaxWidth(Double.MAX_VALUE);
        subtitleLabel.setAlignment(Pos.CENTER);

        // ToggleGroup så kun én kan vælges
        ToggleGroup group = new ToggleGroup();

        // Radioknapper med ikon og tekst
        ToggleButton fertilityButton = createOptionButton("🌱  Fertility", group);
        ToggleButton cancerButton = createOptionButton("🎗  Cancer", group);
        ToggleButton rehabilitationButton = createOptionButton("💪  Rehabilitation", group);
        ToggleButton psychiatryButton = createOptionButton("🧠  Psychiatry", group);
        ToggleButton otherButton = createOptionButton("📋  Other", group);

        // Besked og knapper
        Label messageLabel = new Label("");
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("primary-button");
        continueButton.setMaxWidth(Double.MAX_VALUE);

        Button backButton = new Button("Back to login");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);

        backButton.setOnAction(e -> {
            new StartSystemView().show(stage);
        });

        // Continue knap
        continueButton.setOnAction(e -> {
            // Tjek om en knap er valgt
            ToggleButton selected = (ToggleButton) group.getSelectedToggle();
            if (selected == null) {
                messageLabel.setText("Please select a journey type!");
                return;
            }

            // Hent teksten uden emojis
            String type = selected.getText().trim().replaceAll("[^a-zA-Z]", "");

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
        VBox layout = new VBox(8);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(380);
        layout.setPrefWidth(380);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(
                titleLabel,
                subtitleLabel,
                fertilityButton,
                cancerButton,
                rehabilitationButton,
                psychiatryButton,
                otherButton,
                continueButton,
                backButton,
                messageLabel
        );

        // StackPane med gradient baggrund
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Vis skærmen
        Scene scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
        stage.setTitle("Simpl — Select Journey Type");
        stage.setScene(scene);
        stage.show();
    }

    // Hjælpemetode der opretter en valgknap med styling
    private ToggleButton createOptionButton(String text, ToggleGroup group) {
        ToggleButton button = new ToggleButton(text);
        button.setToggleGroup(group);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 12 16;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-alignment: center-left;"
        );

        // Skift farve når knappen er valgt
        button.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                button.setStyle(
                        "-fx-background-color: #f1f8e9;" +
                                "-fx-text-fill: #2e7d32;" +
                                "-fx-font-size: 13px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-padding: 12 16;" +
                                "-fx-background-radius: 10;" +
                                "-fx-border-color: #4caf50;" +
                                "-fx-border-width: 1.5;" +
                                "-fx-border-radius: 10;" +
                                "-fx-alignment: center-left;"
                );
            } else {
                button.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-text-fill: #333333;" +
                                "-fx-font-size: 13px;" +
                                "-fx-padding: 12 16;" +
                                "-fx-background-radius: 10;" +
                                "-fx-border-color: #e0e0e0;" +
                                "-fx-border-radius: 10;" +
                                "-fx-alignment: center-left;"
                );
            }
        });

        return button;
    }
}