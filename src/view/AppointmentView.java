package view;

import controller.AppointmentController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

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
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout, 350, 400);
        stage.setTitle("Simpl — Add Appointment");
        stage.setScene(scene);
        stage.show();
    }
}