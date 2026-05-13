package view;

import controller.HormoneLogController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import model.Session;

public class HormoneLogView {
    private HormoneLogController controller = new HormoneLogController();


    public void show(Stage stage){
        Label dateLabel = new Label("Date (yyyy-mm-dd):");
        Label hormoneLabel = new Label("Hormone:");
        Label valueLabel = new Label("Value:");
        Label unitLabel = new Label("Unit:");
        TextField dateField = new TextField();
        TextField hormoneField = new TextField();
        TextField valueField = new TextField();
        TextField unitField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {

            // getText() henter hvad brugeren har skrevet i feltet
            LocalDate date = LocalDate.parse(dateField.getText());
            String hormone = hormoneField.getText();
            Double value = Double.parseDouble(valueField.getText());
            String unit = unitField.getText();

            // Vi sender brugernavn og adgangskode til controlleren
            // Controlleren tjekker om de findes i databasen
            // Hvis de findes returnerer den et Patient-objekt — ellers null
            controller.handleSave(Session.getCurrentJourneyId(), date, hormone, value, unit);


        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                dateLabel, dateField,
                hormoneLabel, hormoneField,
                valueLabel, valueField,
                unitLabel, unitField,
                saveButton);

        Scene scene = new Scene(layout, 300,300);
        stage.setTitle("Simpl - log hormone values: ");
        stage.setScene(scene);
        stage.show();
    }
}
