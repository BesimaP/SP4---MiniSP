package view;

import controller.HormoneLogController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import model.Session;

// HormoneLogView viser skærmen hvor patienten kan logge hormonværdier
public class HormoneLogView {

    // Opretter controller objekt som håndterer gemning i databasen
    private HormoneLogController controller = new HormoneLogController();

    // show() metoden viser hormonlog skærmen
    public void show(Stage stage) {

        // Labels og felter
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label hormoneLabel = new Label("Hormone:");
        // Dropdown med standard hormoner brugt i IVF forløb
        ComboBox<String> hormoneBox = new ComboBox<>();
        hormoneBox.getItems().addAll(
                "Oestradiol",
                "Progesteron",
                "AMH",
                "FSH",
                "LH",
                "hCG"
        );
        hormoneBox.setPromptText("Choose hormone:");

        Label valueLabel = new Label("Value:");
        // Felt til den målte hormonværdi
        TextField valueField = new TextField();

        Label unitLabel = new Label("Unit:");
        // Dropdown med standard enheder
        ComboBox<String> unitBox = new ComboBox<>();
        unitBox.getItems().addAll(
                "pmol/L",
                "IU/L",
                "nmol/L"
        );
        unitBox.setPromptText("Choose unit:");

        // Besked der vises efter gem eller ved fejl
        Label messageLabel = new Label("");

        // Knapper
        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to dashboard");

        // Hvad sker der når brugeren klikker Save
        saveButton.setOnAction(e -> {

            // Hent hvad brugeren har valgt og skrevet
            LocalDate date = datePicker.getValue();
            String hormone = hormoneBox.getValue();
            String unit = unitBox.getValue();

            // Validering — tjek at alle felter er udfyldt
            if (date == null) {
                messageLabel.setText("Please select a date!");
                return;
            }
            if (hormone == null) {
                messageLabel.setText("Please select a hormone!");
                return;
            }
            if (valueField.getText().isEmpty()) {
                messageLabel.setText("Please fill in a value!");
                return;
            }
            if (unit == null) {
                messageLabel.setText("Please select a unit!");
                return;
            }

            // Konverter tekst til tal og gem i databasen
            Double value = Double.parseDouble(valueField.getText());
            controller.handleSave(date, hormone, value, unit);
            messageLabel.setText("Hormone value saved!");
        });

        // Hvad sker der når brugeren klikker Back
        backButton.setOnAction(e -> {
            // Gå tilbage til dashboard med den aktive patient
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        // VBox — lodret layout med 10 pixels mellem elementer
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                dateLabel, datePicker,
                hormoneLabel, hormoneBox,
                valueLabel, valueField,
                unitLabel, unitBox,
                saveButton,
                messageLabel,
                backButton
        );

        // Opret og vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — Log Hormone Value");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}