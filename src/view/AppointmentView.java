package view;

import controller.AppointmentController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentView {

    private AppointmentController controller = new AppointmentController();

    public void show(Stage stage) {

        // Felter
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();
        Label typeLabel = new Label("Type:");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(
                "Scanning",
                "Consultation",
                "Egg Retrieval",
                "Transfer",
                "Blood Test",
                "Other"
        );
        typeBox.setPromptText("Choose type:");
        Label locationLabel = new Label("Location:");
        TextField locationField = new TextField();

        // Besked og knapper
        Label messageLabel = new Label("");
        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to dashboard");

        // Liste over kommende aftaler
        Label upcomingLabel = new Label("Upcoming appointments:");
        ListView<String> appointmentList = new ListView<>();
        ArrayList<String> appointments = controller.getUpcomingAppointments();

        if (appointments.isEmpty()) {
            appointmentList.getItems().add("No upcoming appointments");
        } else {
            for (String appointment : appointments) {
                appointmentList.getItems().add(appointment);
            }
        }

        // Gem knap
        saveButton.setOnAction(e -> {
            // Validering
            if (datePicker.getValue() == null) {
                messageLabel.setText("Please select a date!");
                return;
            }
            if (typeBox.getValue() == null) {
                messageLabel.setText("Please select a type!");
                return;
            }
            if (locationField.getText().isEmpty()) {
                messageLabel.setText("Please fill in location!");
                return;
            }

            LocalDate date = datePicker.getValue();
            String type = typeBox.getValue();
            String location = locationField.getText();

            controller.handleSave(date, type, location);
            messageLabel.setText("Appointment saved: " + type + " on " + date + " at " + location);

            // Opdater listen
            appointmentList.getItems().clear();
            ArrayList<String> updatedAppointments = controller.getUpcomingAppointments();
            if (updatedAppointments.isEmpty()) {
                appointmentList.getItems().add("No upcoming appointments");
            } else {
                for (String appointment : updatedAppointments) {
                    appointmentList.getItems().add(appointment);
                }
            }
        });

        // Back knap
        backButton.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                dateLabel, datePicker,
                typeLabel, typeBox,
                locationLabel, locationField,
                saveButton,
                messageLabel,
                upcomingLabel,
                appointmentList,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout, 350, 550);
        stage.setTitle("Simpl — Add Appointment");
        stage.setScene(scene);
        stage.show();
    }
}