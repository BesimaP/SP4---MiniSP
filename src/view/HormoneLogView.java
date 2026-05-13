package view;

import controller.HormoneLogController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

import model.Session;

public class HormoneLogView {
    private HormoneLogController controller = new HormoneLogController();


    public void show(Stage stage){
        Label dateLabel = new Label("Date: ");
        DatePicker datePicker = new DatePicker();
        Label hormoneLabel = new Label("Hormone:");
        Label valueLabel = new Label("Value:");
        Label unitLabel = new Label("Unit:");


        TextField dateField = new TextField();
        ComboBox<String> hormoneBox= new ComboBox<>();
        hormoneBox.getItems().addAll(
                "Oestradiol",
                "Progesteron",
                "AMH",
                "FSH",
                "LH",
                "hCG"
        );
        hormoneBox.setPromptText("Choose hormon:");
        TextField valueField = new TextField();
        ComboBox<String> unitBox = new ComboBox<>();
        unitBox.getItems().addAll(
                "pmol/L",
                "IU/L",
                "nmol/L"
        );
        unitBox.setPromptText("Choose unit:");


        // Besked der vises efter gem
        Label messageLabel = new Label("");

        //Knapper
        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to dashboard");

        saveButton.setOnAction(e -> {

            // getText() henter hvad brugeren har skrevet i feltet
            LocalDate date = datePicker.getValue();
            String hormone = hormoneBox.getValue();
            Double value = Double.parseDouble(valueField.getText());
            String unit = unitBox.getValue();

            // Vi sender brugernavn og adgangskode til controlleren
            // Controlleren tjekker om de findes i databasen
            // Hvis de findes returnerer den et Patient-objekt — ellers null
            controller.handleSave(date, hormone, value, unit);

            messageLabel.setText("Hormone value saved!");

        });

        // Hvad sker der når brugeren klikker Back
        backButton.setOnAction(e -> {
            // Gå tilbage til dashboard
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                dateLabel, dateField,
                hormoneLabel, hormoneBox,
                valueLabel, valueField,
                unitLabel, unitBox,
                saveButton,
                messageLabel,
                backButton);

        Scene scene = new Scene(layout, 350,350);
        stage.setTitle("Simpl - log hormone values: ");
        stage.setScene(scene);
        stage.show();
    }
}
