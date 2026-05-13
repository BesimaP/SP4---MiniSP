package view;

import controller.AppointmentController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;
import model.Session;
import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardView {

    public void show(Stage stage, Patient patient) {

        // Gem patienten i Session
        Session.setCurrentPatient(patient);

        // Tjek for kommende aftaler
        AppointmentController appointmentController = new AppointmentController();
        ArrayList<String> appointments = appointmentController.getUpcomingAppointments();

        if (!appointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Påmindelse");
            alert.setHeaderText("Du har kommende aftaler!");
            alert.setContentText(String.join("\n", appointments));
            alert.show();
        }

        // Info labels
        Label welcomeLabel = new Label("Welcome " + patient.getName() + "!");
        welcomeLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 20px; -fx-font-weight: bold;"); //css
        Label dateLabel = new Label("Today: " + LocalDate.now());
        dateLabel.getStyleClass().add("subtitle-label"); //css
        Label diagnosisLabel = new Label("Diagnosis: " + patient.getDiagnosis());
        diagnosisLabel.getStyleClass().add("subtitle-label");

        // Separator
        Separator separator1 = new Separator();
        // Separator separator2 = new Separator();

        // Gruppe overskrifter
        Label loggingLabel = new Label("LOG DATA");
        loggingLabel.getStyleClass().add("section-label"); //css
        Label planningLabel = new Label("PLANNING");
        planningLabel.getStyleClass().add("section-label"); //css
        Label historyLabel = new Label("ROUND");
        historyLabel.getStyleClass().add("section-label"); //css

        // Knapper med styling
        Button hormoneButton = new Button("Log hormone value");
        hormoneButton.getStyleClass().add("card-green"); //css
        hormoneButton.setMaxWidth(Double.MAX_VALUE); //css
        hormoneButton.setGraphic(createIcon("/design/appointments.png")); //css Tilføj ikon
        hormoneButton.setContentDisplay(ContentDisplay.TOP);

        Button medicationButton = new Button("Log medication");
        medicationButton.getStyleClass().add("card-green"); //css
        medicationButton.setMaxWidth(Double.MAX_VALUE); //css
        medicationButton.setGraphic(createIcon("/design/medication.png")); //css Tilføj ikon
        medicationButton.setContentDisplay(ContentDisplay.TOP);

        Button appointmentButton = new Button("Appointments");
        appointmentButton.getStyleClass().add("card-blue"); //css
        appointmentButton.setMaxWidth(Double.MAX_VALUE); //css
        appointmentButton.setGraphic(createIcon("/design/appointments.png")); //css Tilføj ikon
        appointmentButton.setContentDisplay(ContentDisplay.TOP);

        Button diaryButton = new Button("Diary");
        diaryButton.getStyleClass().add("card-blue"); //css
        diaryButton.setMaxWidth(Double.MAX_VALUE); //css
        diaryButton.setGraphic(createIcon("/design/diary.png")); //css Tilføj ikon
        diaryButton.setContentDisplay(ContentDisplay.TOP);

        Button timelineButton = new Button("Timeline");
        timelineButton.getStyleClass().add("card-blue");
        timelineButton.setMaxWidth(Double.MAX_VALUE);
        timelineButton.setGraphic(createIcon("/design/timeline.png")); //css Tilføj ikon
        timelineButton.setContentDisplay(ContentDisplay.TOP);

        Button historyButton = new Button("Round history");
        historyButton.getStyleClass().add("card-pink");
        historyButton.setMaxWidth(Double.MAX_VALUE);
        historyButton.setGraphic(createIcon("/design/roundHistory.png")); //css Tilføj ikon
        historyButton.setContentDisplay(ContentDisplay.TOP);

        Button newRoundButton = new Button("New round");
        newRoundButton.getStyleClass().add("card-pink");
        newRoundButton.setMaxWidth(Double.MAX_VALUE);
        newRoundButton.setGraphic(createIcon("/design/start.png")); //css Tilføj ikon
        newRoundButton.setContentDisplay(ContentDisplay.TOP);

        Button endRoundButton = new Button("End round");
        endRoundButton.getStyleClass().add("card-pink");
        endRoundButton.setMaxWidth(Double.MAX_VALUE);
        endRoundButton.setGraphic(createIcon("/design/stop.png")); //css Tilføj ikon
        endRoundButton.setContentDisplay(ContentDisplay.TOP);

        // Kobl knapper til views
        hormoneButton.setOnAction(e -> {
            HormoneLogView view = new HormoneLogView();
            view.show(stage);
        });

        medicationButton.setOnAction(e -> {
            MedicationLogView view = new MedicationLogView();
            view.show(stage);
        });

        appointmentButton.setOnAction(e -> {
            AppointmentView view = new AppointmentView();
            view.show(stage);
        });

        diaryButton.setOnAction(e -> {
            DiaryView view = new DiaryView();
            view.show(stage);
        });

        timelineButton.setOnAction(e -> {
            TimelineView view = new TimelineView();
            view.show(stage);
        });

        historyButton.setOnAction(e -> {
            RoundHistoryView view = new RoundHistoryView();
            view.show(stage);
        });

        newRoundButton.setOnAction(e -> {
            NewRoundView view = new NewRoundView();
            view.show(stage);
        });

        endRoundButton.setOnAction(e -> {
            EndRoundView view = new EndRoundView();
            view.show(stage);
        });

        // LOG DATA grid - 2 kolonner
        GridPane logGrid = new GridPane();
        logGrid.setHgap(10);
        logGrid.setVgap(10);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        logGrid.getColumnConstraints().addAll(col2, col2);
        logGrid.add(hormoneButton, 0, 0);
        logGrid.add(medicationButton, 1, 0);

        // PLANLÆGNING grid - 3 kolonner
        GridPane planGrid = new GridPane();
        planGrid.setHgap(10);
        planGrid.setVgap(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33.33);
        planGrid.getColumnConstraints().addAll(col3, col3, col3);
        planGrid.add(appointmentButton, 0, 0);
        planGrid.add(diaryButton, 1, 0);
        planGrid.add(timelineButton, 2, 0);

        // RUNDE grid - 3 kolonner
        GridPane roundGrid = new GridPane();
        roundGrid.setHgap(10);
        roundGrid.setVgap(10);
        roundGrid.getColumnConstraints().addAll(col3, col3, col3);
        roundGrid.add(historyButton, 0, 0);
        roundGrid.add(newRoundButton, 1, 0);
        roundGrid.add(endRoundButton, 2, 0);

        // Layout
        VBox layout = new VBox(10);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(460);
        layout.setPrefWidth(460);
        layout.setPadding(new Insets(24));
        layout.getChildren().addAll(
                welcomeLabel, dateLabel, diagnosisLabel,
                separator1,
                loggingLabel, logGrid,
                planningLabel, planGrid,
               // separator2,
                historyLabel, roundGrid
        );

        // Vis skærmen

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/design/styles.css").toExternalForm());

        stage.setTitle("Simpl — Dashboard");
        stage.setScene(scene);
        stage.show();
    }
    //Hjælpemetode til ikoner
    private ImageView createIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        icon.setPreserveRatio(true);
        return icon;
    }
    private void styleButton(Button button, String imagePath) {
        button.setGraphic(createIcon(imagePath));
        button.setContentDisplay(ContentDisplay.TOP);
    }
}