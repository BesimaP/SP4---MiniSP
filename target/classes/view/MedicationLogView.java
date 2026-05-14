package view;

import controller.MedicationLogController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

public class MedicationLogView {

    private MedicationLogController controller = new MedicationLogController();

    public void show(Stage stage) {

        // Felter
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();
        Label medicationLabel = new Label("Medication:");
        ComboBox<String> medicationBox = new ComboBox<>();
        medicationBox.getItems().addAll(
                "Gonal-F",
                "Menopur",
                "Orgalutran",
                "Ovitrelle",
                "Progestan"
        );
        medicationBox.setPromptText("Choose medication:");

        Label doseLabel = new Label("Dose:");
        ComboBox<String> doseBox = new ComboBox<>();
        doseBox.getItems().addAll(
                "75 IU",
                "150 IU",
                "300 IU"
        );
        doseBox.setPromptText("Choose dosage:");

        CheckBox takenCheckBox = new CheckBox("Medication taken today");

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
            if (medicationBox.getValue() == null) {
                messageLabel.setText("Please select a medication!");
                return;
            }
            if (doseBox.getValue() == null) {
                messageLabel.setText("Please select a dose!");
                return;
            }

            LocalDate date = datePicker.getValue();
            String medication = medicationBox.getValue();
            String dose = doseBox.getValue();
            boolean taken = takenCheckBox.isSelected();

            controller.handleSave(date, medication, dose, taken);
            messageLabel.setText("Medication saved!");
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
                medicationLabel, medicationBox,
                doseLabel, doseBox,
                takenCheckBox,
                saveButton,
                messageLabel,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — Log Medication");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}