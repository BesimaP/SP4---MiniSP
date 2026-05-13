package view;

import controller.AppointmentController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
        Label dateLabel = new Label("Today: " + LocalDate.now());
        Label diagnosisLabel = new Label("Diagnosis: " + patient.getDiagnosis());

        // Separator
        Separator separator1 = new Separator();
        Separator separator2 = new Separator();

        // Gruppe overskrifter
        Label loggingLabel = new Label("--- Logging ---");
        Label historyLabel = new Label("--- History ---");

        // Knapper
        Button hormoneButton = new Button("Log hormone value");
        Button medicationButton = new Button("Log medication");
        Button appointmentButton = new Button("Add appointment");
        Button diaryButton = new Button("Diary");
        Button timelineButton = new Button("Timeline");
        Button historyButton = new Button("Round history");
        Button newRoundButton = new Button("Start new round");
        Button endRoundButton = new Button("End round");
        Button logoutButton = new Button("Log out");

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

        logoutButton.setOnAction(e -> {
            Session.setCurrentPatient(null);
            Session.setCurrentJourneyId(0);
            StartSystemView startView = new StartSystemView();
            startView.show(stage);
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                welcomeLabel,
                dateLabel,
                diagnosisLabel,
                separator1,
                loggingLabel,
                hormoneButton,
                medicationButton,
                appointmentButton,
                diaryButton,
                separator2,
                historyLabel,
                timelineButton,
                historyButton,
                newRoundButton,
                endRoundButton,
                logoutButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — Dashboard");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}