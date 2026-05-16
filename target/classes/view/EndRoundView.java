package view;

import controller.EndRoundController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;

    // EndRoundView viser skærmen hvor patienten kan afslutte den aktive IVF-runde
    public class EndRoundView {

        // Controller der håndterer afslutning af runden i databasen
        private EndRoundController controller = new EndRoundController();

        public void show(Stage stage) {

            // Titel øverst på skærmen
            Label titleLabel = new Label("End your current round:");

            // Dropdown til at vælge rundens resultat
            Label resultLabel = new Label("Select result:");
            ComboBox<String> resultBox = new ComboBox<>();
            resultBox.getItems().addAll(
                    "POSITIVE",  // graviditetstest positiv
                    "NEGATIVE",  // graviditetstest negativ
                    "PENDING"    // afventer stadig svar
            );
            resultBox.setPromptText("Choose result:");

            // Beskedlabel — viser fejl eller bekræftelse
            Label messageLabel = new Label("");

            // Knapper
            Button endButton = new Button("End Round");
            Button backButton = new Button("Back to dashboard");

            // Hvad sker der når brugeren klikker End Round
            endButton.setOnAction(e -> {
                String result = resultBox.getValue();

                // Validering — tjek at brugeren har valgt et resultat
                if (result == null) {
                    messageLabel.setText("Please choose a result!");
                    return;
                }

                // Afslut runden via controller — opdaterer både
                // fertility_journey og journey tabellen i databasen
                controller.handleEndRound(Session.getCurrentJourneyId(), result);
                messageLabel.setText("Round ended with result: " + result + " 🎉");
            });

            // Hvad sker der når brugeren klikker Back
            backButton.setOnAction(e -> {
                new DashboardView().show(stage, Session.getCurrentPatient());
            });

            // VBox — lodret layout der samler alle elementer
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            layout.getChildren().addAll(
                    titleLabel,
                    resultLabel,
                    resultBox,
                    endButton,
                    messageLabel,
                    backButton
            );

            // Opret og vis skærmen — sizeToScene tilpasser vinduet til indholdet
            Scene scene = new Scene(layout);
            stage.setTitle("Simpl — End Round");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }
    }