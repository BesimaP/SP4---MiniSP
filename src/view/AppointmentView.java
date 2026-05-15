package view;

import controller.AppointmentController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentView {

    private AppointmentController controller = new AppointmentController();

    public void show(Stage stage) {

        // Header
        Label titleLabel = new Label("Add appointment");
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label subtitleLabel = new Label("Fill out all fields");
        subtitleLabel.getStyleClass().add("subtitle-label");

        VBox header = new VBox(4, titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 16, 0));
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // Felter
        Label dateLabel = new Label("DATE");
        dateLabel.getStyleClass().add("field-label");
        DatePicker datePicker = new DatePicker();
        datePicker.getStyleClass().add("modern-field");
        datePicker.setMaxWidth(Double.MAX_VALUE);

        Label typeLabel = new Label("TYPE");
        typeLabel.getStyleClass().add("field-label");
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
        typeBox.setStyle("-fx-font-size: 10px;");
        typeBox.getStyleClass().add("modern-field");
        typeBox.setMaxWidth(Double.MAX_VALUE);

        Label locationLabel = new Label("LOCATION");
        locationLabel.getStyleClass().add("field-label");
        TextField locationField = new TextField();
        locationField.setPromptText("Hospital or clinic name");
        locationField.getStyleClass().add("modern-field");
        locationField.setMaxWidth(Double.MAX_VALUE);

        // Besked
        Label messageLabel = new Label("");
        messageLabel.getStyleClass().add("subtitle-label");

        // Ryd beskeden når brugeren ændrer noget
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
        typeBox.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
        locationField.textProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));

        // Knapper
        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("primary-button");
        saveButton.setMaxWidth(Double.MAX_VALUE);

        Button backButton = new Button("Back to dashboard");
        backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);

        // Liste over kommende aftaler
        Label upcomingLabel = new Label("UPCOMING APPOINTMENTS");
        upcomingLabel.getStyleClass().add("section-label");

        ListView<String> appointmentList = new ListView<>();
        appointmentList.getStyleClass().add("modern-list");
        appointmentList.setPrefHeight(120);

        ArrayList<String> appointments = controller.getUpcomingAppointments();

        // Venlig tom-skærm besked
        if (appointments.isEmpty()) {
            appointmentList.getItems().add("No appointments yet — add one above to get started");
        } else {
            for (String appointment : appointments) {
                appointmentList.getItems().add(appointment);
            }
        }

        // Save
        saveButton.setOnAction(e -> {
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
                appointmentList.getItems().add("No appointments yet — add one above to get started");
            } else {
                for (String appointment : updatedAppointments) {
                    appointmentList.getItems().add(appointment);
                }
            }
        });

        // Back
        backButton.setOnAction(e -> {
            new DashboardView().show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(12);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(400);
        layout.setPrefWidth(400);
        layout.setPadding(new Insets(28));
        layout.getChildren().addAll(
                header,
                dateLabel, datePicker,
                typeLabel, typeBox,
                locationLabel, locationField,
                saveButton,
                messageLabel,
                upcomingLabel, appointmentList,
                backButton
        );

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Samme størrelse som dashboard
        Scene scene = new Scene(root, 650, 750);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());

        stage.setTitle("Simpl — Add Appointment");
        stage.setScene(scene);
        stage.show();
    }
}