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

    public void show(Stage stage){
        Label dateLabel = new Label("Date: ");
        DatePicker datePicker = new DatePicker();
        Label medicationLabel = new Label("Medication:");
        Label doseLabel = new Label("Dose:");
        Label takenLabel = new Label("Taken:");

        TextField dateField = new TextField();
        ComboBox<String> medicationBox= new ComboBox<>();
        medicationBox.getItems().addAll(
                "Gonal-F",
                "Menopur",
                "Orgalutran",
                "Ovitrelle",
                "Progestan"
        );
        medicationBox.setPromptText("Choose medication:");

        ComboBox<String> doseBox= new ComboBox<>();
        doseBox.getItems().addAll(
                "75 IU",
                "150 IU",
                "300 IU"
        );
        doseBox.setPromptText("Choose dosage:");

        CheckBox takenCheckBox = new CheckBox("Medication taken today:");

        Label messageLabel = new Label("");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to dashboard");

        saveButton.setOnAction(e -> {

            // getText() henter hvad brugeren har skrevet i feltet
            LocalDate date = datePicker.getValue();
            String medication = medicationBox.getValue();
            String unit = doseBox.getValue();
            boolean taken = takenCheckBox.isSelected();

            // Vi sender brugernavn og adgangskode til controlleren
            // Controlleren tjekker om de findes i databasen
            // Hvis de findes returnerer den et Patient-objekt — ellers null
            controller.handleSave(date, medication, unit, taken);

            messageLabel.setText("Medication saved!");

        });
        backButton.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                dateLabel, datePicker,
                messageLabel, medicationBox,
                doseLabel, doseBox,
                takenLabel, takenCheckBox,
                saveButton,
                messageLabel,
                backButton);

        Scene scene = new Scene(layout);
        stage.sizeToScene();
        stage.setTitle("Simpl - log medication: ");
        stage.setScene(scene);
        stage.show();


    }
}
