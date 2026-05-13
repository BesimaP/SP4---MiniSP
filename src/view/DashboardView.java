package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;
import model.Session;

public class DashboardView {

    public void show(Stage stage, Patient patient) {

        // Gem patienten i Session
        Session.setCurrentPatient(patient);

        // Velkomstbesked
        Label welcomeLabel = new Label("Welcome " + patient.getName() + "!");

        // Knapper
        Button hormoneButton = new Button("Log hormone value");
        Button medicationButton = new Button("Log medication");
        Button appointmentButton = new Button("Add appointment");
        Button diaryButton = new Button("Diary");
        Button timelineButton = new Button("Timeline");
        Button historyButton = new Button("Round history");
        Button newRoundButton = new Button("Start new round");
        Button endRoundButton = new Button("End round");

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

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                welcomeLabel,
                hormoneButton,
                medicationButton,
                appointmentButton,
                diaryButton,
                timelineButton,
                historyButton,
                newRoundButton,
                endRoundButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout, 300, 400);
        stage.setTitle("Simpl — Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}