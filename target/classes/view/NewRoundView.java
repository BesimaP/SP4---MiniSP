package view;

import controller.NewRoundController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

    // NewRoundView viser skærmen hvor patienten kan starte en ny IVF-runde
    public class NewRoundView {

        // Controller der håndterer oprettelse af ny runde i databasen
        private NewRoundController controller = new NewRoundController();

        public void show(Stage stage) {

            // Felt til rundenummer — brugeren skriver fx 1, 2 eller 3
            Label roundNumberLabel = new Label("Round number:");
            TextField roundNumberField = new TextField();

            // Startdato vises automatisk — altid dagens dato
            Label dateLabel = new Label("Start date: " + LocalDate.now());

            // Beskedlabel — viser fejl eller bekræftelse
            Label messageLabel = new Label("");

            // Knapper
            Button saveButton = new Button("Start Round");
            Button backButton = new Button("Back to Dashboard");

            // Hvad sker der når brugeren klikker Start Round
            saveButton.setOnAction(e -> {

                // Validering — tjek at rundenummer er udfyldt
                if (roundNumberField.getText().isEmpty()) {
                    messageLabel.setText("Please fill in round number!");
                    return;
                }

                // Konverter tekst til heltal og gem runden i databasen
                int roundNumber = Integer.parseInt(roundNumberField.getText());
                controller.handleStartRound(roundNumber);
                messageLabel.setText("Round started!");
            });

            // Hvad sker der når brugeren klikker Back
            backButton.setOnAction(e -> {
                DashboardView dashboard = new DashboardView();
                dashboard.show(stage, Session.getCurrentPatient());
            });

            // VBox — lodret layout der samler alle elementer
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            layout.getChildren().addAll(
                    roundNumberLabel, roundNumberField,
                    dateLabel,
                    saveButton,
                    messageLabel,
                    backButton
            );

            // Opret og vis skærmen — sizeToScene tilpasser vinduet til indholdet
            Scene scene = new Scene(layout);
            stage.setTitle("Simpl — Start New Round");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }
    }