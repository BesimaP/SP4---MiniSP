package view;

import controller.HormoneLogController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
        ComboBox<String> hormoneBox= new ComboBox<>();
        hormoneBox.getItems().addAll(
                "Oestradiol",
                "Progesteron",
                "AMH",
                "FSH",
                "LH",
                "HCG"
        );
        hormoneBox.setPromptText("Choose hormon:");
        TextField valueField = new TextField();
        TextField unitField = new TextField();


        // Besked der vises efter gem
        Label messageLabel = new Label("");

        //Knapper
        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to dashboard");

        saveButton.setOnAction(e -> {

            // getText() henter hvad brugeren har skrevet i feltet
            LocalDate date = LocalDate.parse(dateField.getText());
            String hormone = hormoneBox.getValue();
            Double value = Double.parseDouble(valueField.getText());
            String unit = unitField.getText();

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
                unitLabel, unitField,
                saveButton,
                messageLabel,
                backButton);

        Scene scene = new Scene(layout, 350,350);
        stage.setTitle("Simpl - log hormone values: ");
        stage.setScene(scene);
        stage.show();
    }
}
