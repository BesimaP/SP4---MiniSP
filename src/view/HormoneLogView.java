package view;

import controller.HormoneLogController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

        //Header
        Label titleLabel = new Label("Log hormonevalue");
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label("Fill out all fields");
        subtitleLabel.getStyleClass().add("subtitle-label");

        VBox header = new VBox(4, titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 16, 0));
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // Labels og felter
        Label dateLabel = new Label("DATE");
        dateLabel.getStyleClass().add("field-label"); //css
        DatePicker datePicker = new DatePicker();
        datePicker.getStyleClass().add("modern-label"); //css
        datePicker.setMaxWidth(Double.MAX_VALUE); //css

        Label hormoneLabel = new Label("HORMONE");
        hormoneLabel.getStyleClass().add("field-label"); //css
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
        hormoneBox.setStyle("-fx-font-size: 10px;");
        hormoneBox.getStyleClass().add("modern-field"); //css
        hormoneBox.setMaxWidth(Double.MAX_VALUE); //css

        Label valueLabel = new Label("VALUE");
        valueLabel.getStyleClass().add("field-label"); //css
        // Felt til den målte hormonværdi
        TextField valueField = new TextField();
        valueField.getStyleClass().add("modern-field"); //css
        valueField.setMaxWidth(Double.MAX_VALUE); //css

        Label unitLabel = new Label("UNIT");
        unitLabel.getStyleClass().add("field-label"); //css
        // Dropdown med standard enheder
        ComboBox<String> unitBox = new ComboBox<>();
        unitBox.getItems().addAll(
                "pmol/L",
                "IU/L",
                "nmol/L"
        );
        unitBox.setPromptText("Choose unit:");
        unitBox.setStyle("-fx-font-size: 10px;");
        unitBox.getStyleClass().add("modern-field"); //css
        unitBox.setMaxWidth(Double.MAX_VALUE); //css

        //Værdi og enhed side om side
        VBox valueBox = new VBox(4, valueLabel, valueField);
        VBox unitBoxContainer = new VBox(4, unitLabel, unitBox);
        GridPane valueGrid = new GridPane();
        valueGrid.setHgap(12);
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        valueGrid.getColumnConstraints().addAll(col, col);
        valueGrid.add(valueBox, 0, 0);
        valueGrid.add(unitBoxContainer, 1, 0);

        // Besked der vises efter gem eller ved fejl
        Label messageLabel = new Label("");
        messageLabel.getStyleClass().add("subtitle-label"); //css

        // Knapper
        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("primary-button"); //css
        saveButton.setMaxWidth(Double.MAX_VALUE);//css
        Button backButton = new Button("Back to dashboard");
        backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
        backButton.getStyleClass().add("secondary-button"); //css
        backButton.setMaxWidth(Double.MAX_VALUE); //css

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

        //Layout
        // VBox — lodret layout med 10 pixels mellem elementer
        VBox layout = new VBox(12);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(400);
        layout.setPrefWidth(400);
        layout.setPadding(new Insets(28));
        layout.getChildren().addAll(
                header,
                dateLabel, datePicker,
                hormoneLabel, hormoneBox,
                valueGrid,
                saveButton,
                messageLabel,
                backButton
        );

        // Opret og vis skærmen
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/design/styles.css").toExternalForm());

        stage.setTitle("Simpl — Log Hormone Value");
        stage.setScene(scene);
        stage.show();
    }
}