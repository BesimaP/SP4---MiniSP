package view;

import controller.AppointmentController;
import controller.DiaryController;
import controller.HormoneLogController;
import controller.RoundHistoryController;
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

        // Gem patienten i Session så andre views kan hente den
        Session.setCurrentPatient(patient);

        // Tjek om patienten har kommende aftaler og vis en popup
        AppointmentController appointmentController = new AppointmentController();
        ArrayList<String> appointments = appointmentController.getUpcomingAppointments();

        if (!appointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Påmindelse");
            alert.setHeaderText("Du har kommende aftaler!");
            alert.setContentText(String.join("\n", appointments));
            alert.show();
        }

        // Labels der viser patientens information
        Label welcomeLabel = new Label("Welcome " + patient.getName() + "!");
        welcomeLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 20px; -fx-font-weight: bold;");
        Label dateLabel = new Label("Today: " + LocalDate.now());
        dateLabel.getStyleClass().add("subtitle-label");
        Label diagnosisLabel = new Label("Diagnosis: " + patient.getDiagnosis());
        diagnosisLabel.getStyleClass().add("subtitle-label");

        // Hent statistik data fra databasen
        HormoneLogController hormoneController = new HormoneLogController();
        DiaryController diaryController = new DiaryController();
        RoundHistoryController roundController = new RoundHistoryController();

        String latestHormone = hormoneController.getLatestHormoneValue();
        int diaryCount = diaryController.countDiaryEntries();
        int roundCount = roundController.initialize().size();

        // Statistik kort — tre bokse side om side
        VBox hormoneBox = new VBox(4);
        hormoneBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 12;");
        hormoneBox.getChildren().addAll(
                new Label("Latest hormone") {{ setStyle("-fx-font-size: 11px; -fx-text-fill: #888;"); }},
                new Label(latestHormone) {{ setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;"); }}
        );

        VBox diaryBox = new VBox(4);
        diaryBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 12;");
        diaryBox.getChildren().addAll(
                new Label(diaryCount + "") {{ setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;"); }},
                new Label("Diary entries") {{ setStyle("-fx-font-size: 11px; -fx-text-fill: #888;"); }}
        );

        VBox roundBox = new VBox(4);
        roundBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 12;");
        roundBox.getChildren().addAll(
                new Label(roundCount + "") {{ setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;"); }},
                new Label("Total rounds") {{ setStyle("-fx-font-size: 11px; -fx-text-fill: #888;"); }}
        );

        // Grid med statistik kort — 3 kolonner
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(10);
        ColumnConstraints statCol = new ColumnConstraints();
        statCol.setPercentWidth(33.33);
        statsGrid.getColumnConstraints().addAll(statCol, statCol, statCol);
        statsGrid.add(hormoneBox, 0, 0);
        statsGrid.add(diaryBox, 1, 0);
        statsGrid.add(roundBox, 2, 0);



        // Separator er en vandret linje der adskiller sektioner
        Separator separator1 = new Separator();

        // Overskrifter til de tre sektioner
        Label loggingLabel = new Label("LOG DATA");
        loggingLabel.getStyleClass().add("section-label");
        Label planningLabel = new Label("PLANNING");
        planningLabel.getStyleClass().add("section-label");
        Label historyLabel = new Label("ROUND");
        historyLabel.getStyleClass().add("section-label");

        // Knapper til LOG DATA sektionen — uden / foran design
        Button hormoneButton = new Button("Log hormone value");
        hormoneButton.getStyleClass().add("card-green");
        hormoneButton.setMaxWidth(Double.MAX_VALUE);
        hormoneButton.setGraphic(createIcon("design/appointments.png"));
        hormoneButton.setContentDisplay(ContentDisplay.TOP);

        Button medicationButton = new Button("Log medication");
        medicationButton.getStyleClass().add("card-green");
        medicationButton.setMaxWidth(Double.MAX_VALUE);
        medicationButton.setGraphic(createIcon("design/medication.png"));
        medicationButton.setContentDisplay(ContentDisplay.TOP);

        // Knapper til PLANNING sektionen
        Button appointmentButton = new Button("Appoint.");
        appointmentButton.getStyleClass().add("card-blue");
        appointmentButton.setMaxWidth(Double.MAX_VALUE);
        appointmentButton.setGraphic(createIcon("design/appointments.png"));
        appointmentButton.setContentDisplay(ContentDisplay.TOP);

        Button diaryButton = new Button("Diary");
        diaryButton.getStyleClass().add("card-blue");
        diaryButton.setMaxWidth(Double.MAX_VALUE);
        diaryButton.setGraphic(createIcon("design/diary.png"));
        diaryButton.setContentDisplay(ContentDisplay.TOP);

        Button timelineButton = new Button("Timeline");
        timelineButton.getStyleClass().add("card-blue");
        timelineButton.setMaxWidth(Double.MAX_VALUE);
        timelineButton.setGraphic(createIcon("design/timeline.png"));
        timelineButton.setContentDisplay(ContentDisplay.TOP);

        // Knapper til ROUND sektionen
        Button historyButton = new Button("History");
        historyButton.getStyleClass().add("card-pink");
        historyButton.setMaxWidth(Double.MAX_VALUE);
        historyButton.setGraphic(createIcon("design/roundHistory.png"));
        historyButton.setContentDisplay(ContentDisplay.TOP);

        // start.png mangler — bruger stop.png i stedet
        Button newRoundButton = new Button("New round");
        newRoundButton.getStyleClass().add("card-pink");
        newRoundButton.setMaxWidth(Double.MAX_VALUE);
        newRoundButton.setGraphic(createIcon("design/stop.png"));
        newRoundButton.setContentDisplay(ContentDisplay.TOP);

        Button endRoundButton = new Button("End round");
        endRoundButton.getStyleClass().add("card-pink");
        endRoundButton.setMaxWidth(Double.MAX_VALUE);
        endRoundButton.setGraphic(createIcon("design/stop.png"));
        endRoundButton.setContentDisplay(ContentDisplay.TOP);

        // Log ud knap
        Button logoutButton = new Button("Log out");
        logoutButton.getStyleClass().add("secondary-button");
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        // Kobl knapper til de rigtige views
        hormoneButton.setOnAction(e -> new HormoneLogView().show(stage));
        medicationButton.setOnAction(e -> new MedicationLogView().show(stage));
        appointmentButton.setOnAction(e -> new AppointmentView().show(stage));
        diaryButton.setOnAction(e -> new DiaryView().show(stage));
        timelineButton.setOnAction(e -> new TimelineView().show(stage));
        historyButton.setOnAction(e -> new RoundHistoryView().show(stage));
        newRoundButton.setOnAction(e -> new NewRoundView().show(stage));
        endRoundButton.setOnAction(e -> new EndRoundView().show(stage));

        // Log ud — ryd Session og gå tilbage til login
        logoutButton.setOnAction(e -> {
            Session.setCurrentPatient(null);
            Session.setCurrentJourneyId(0);
            new StartSystemView().show(stage);
        });

        // LOG DATA grid — 2 kolonner side om side
        GridPane logGrid = new GridPane();
        logGrid.setHgap(10);
        logGrid.setVgap(10);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        logGrid.getColumnConstraints().addAll(col2, col2);
        logGrid.add(hormoneButton, 0, 0);
        logGrid.add(medicationButton, 1, 0);

        // PLANNING grid — 3 kolonner side om side
        GridPane planGrid = new GridPane();
        planGrid.setHgap(10);
        planGrid.setVgap(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33.33);
        planGrid.getColumnConstraints().addAll(col3, col3, col3);
        planGrid.add(appointmentButton, 0, 0);
        planGrid.add(diaryButton, 1, 0);
        planGrid.add(timelineButton, 2, 0);

        // ROUND grid — 3 kolonner side om side
        GridPane roundGrid = new GridPane();
        roundGrid.setHgap(10);
        roundGrid.setVgap(10);
        roundGrid.getColumnConstraints().addAll(col3, col3, col3);
        roundGrid.add(historyButton, 0, 0);
        roundGrid.add(newRoundButton, 1, 0);
        roundGrid.add(endRoundButton, 2, 0);

        // VBox — lodret layout der samler alle elementer
        VBox layout = new VBox(10);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(460);
        layout.setPrefWidth(460);
        layout.setPadding(new Insets(24));
        layout.getChildren().addAll(
                welcomeLabel, dateLabel, diagnosisLabel,
                statsGrid, // ← er den der?
                separator1,
                loggingLabel, logGrid,
                planningLabel, planGrid,
                historyLabel, roundGrid,
                logoutButton
        );

        // StackPane centrerer layoutet på skærmen med gradient baggrund
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Opret og vis skærmen — styles.css uden / foran design
        Scene scene = new Scene(root, 500, 700);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
        stage.setTitle("Simpl — Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    // Hjælpemetode der opretter et ikon fra en billedfil
    private ImageView createIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(path)));
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        icon.setPreserveRatio(true);
        return icon;
    }
}