package view;

import controller.MedicationLogController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

    // MedicationLogView viser skærmen hvor patienten kan logge daglig medicin
    public class MedicationLogView {

        // Controller der håndterer gemning af medicinindtastninger i databasen
        private MedicationLogController controller = new MedicationLogController();

        public void show(Stage stage) {

            // Header med titel og undertitel
            Label titleLabel = new Label("Log medication");
            titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
            Label subtitleLabel = new Label("Register your medication for today");
            subtitleLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

            // VBox samler titel og undertitel med en bundkant som separator
            VBox headerBox = new VBox(2, titleLabel, subtitleLabel);
            headerBox.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 12 0;");

            // Dato felt
            Label dateLabel = new Label("DATE");
            dateLabel.getStyleClass().add("field-label");
            DatePicker datePicker = new DatePicker();
            datePicker.getStyleClass().add("modern-datepicker");
            datePicker.setMaxWidth(Double.MAX_VALUE);

            // Medicintype dropdown med standard IVF-medicin
            Label medicationLabel = new Label("MEDICATION");
            medicationLabel.getStyleClass().add("field-label");
            ComboBox<String> medicationBox = new ComboBox<>();
            medicationBox.getItems().addAll(
                    "Gonal-F",
                    "Menopur",
                    "Orgalutran",
                    "Ovitrelle",
                    "Progestan"
            );
            medicationBox.setPromptText("Choose medication");
            medicationBox.getStyleClass().add("modern-combo");
            medicationBox.setMaxWidth(Double.MAX_VALUE);

            // Dosis dropdown med standard doser
            Label doseLabel = new Label("DOSE");
            doseLabel.getStyleClass().add("field-label");
            ComboBox<String> doseBox = new ComboBox<>();
            doseBox.getItems().addAll(
                    "75 IU",
                    "150 IU",
                    "300 IU"
            );
            doseBox.setPromptText("Choose dosage");
            doseBox.getStyleClass().add("modern-combo");
            doseBox.setMaxWidth(Double.MAX_VALUE);

            // CheckBox — patienten markerer om medicinen er taget i dag
            CheckBox takenCheckBox = new CheckBox("Medication taken today");
            takenCheckBox.setStyle("-fx-text-fill: #333333; -fx-font-size: 13px; -fx-padding: 8 0 0 0;");

            // Beskedlabel — viser fejl eller bekræftelse
            Label messageLabel = new Label("");
            messageLabel.setStyle("-fx-font-size: 12px;");

            // Gem knap
            Button saveButton = new Button("Save");
            saveButton.getStyleClass().add("primary-button");
            saveButton.setMaxWidth(Double.MAX_VALUE);

            // Tilbage knap
            Button backButton = new Button("Back to dashboard");
            backButton.getStyleClass().add("secondary-button");
            backButton.setMaxWidth(Double.MAX_VALUE);

            // Ryd beskeden automatisk når brugeren ændrer noget i felterne
            datePicker.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
            medicationBox.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
            doseBox.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
            takenCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));

            // Hvad sker der når brugeren klikker Save
            saveButton.setOnAction(e -> {

                // Validering — tjek at alle felter er udfyldt
                if (datePicker.getValue() == null) {
                    messageLabel.getStyleClass().setAll("message-error");
                    messageLabel.setText("Please select a date!");
                    return;
                }
                if (medicationBox.getValue() == null) {
                    messageLabel.getStyleClass().setAll("message-error");
                    messageLabel.setText("Please select a medication!");
                    return;
                }
                if (doseBox.getValue() == null) {
                    messageLabel.getStyleClass().setAll("message-error");
                    messageLabel.setText("Please select a dose!");
                    return;
                }

                // Hent værdierne fra felterne
                LocalDate date = datePicker.getValue();
                String medication = medicationBox.getValue();
                String dose = doseBox.getValue();
                boolean taken = takenCheckBox.isSelected(); // true hvis checkBox er markeret

                // Gem i databasen via controller
                controller.handleSave(date, medication, dose, taken);
                messageLabel.getStyleClass().setAll("message-success");
                messageLabel.setText("Medication saved!");
            });

            // Hvad sker der når brugeren klikker Back
            backButton.setOnAction(e -> {
                DashboardView dashboard = new DashboardView();
                dashboard.show(stage, Session.getCurrentPatient());
            });

            // VBox kort-layout der samler alle elementer
            VBox card = new VBox(8);
            card.getStyleClass().add("card");
            card.setMaxWidth(420);
            card.setPadding(new Insets(24));
            card.getChildren().addAll(
                    headerBox,
                    dateLabel, datePicker,
                    medicationLabel, medicationBox,
                    doseLabel, doseBox,
                    takenCheckBox,
                    saveButton,
                    messageLabel,
                    backButton
            );

            // VBox med gradient baggrund — centrerer kortet på skærmen
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER); // centrer kortet lodret og vandret
            root.setPadding(new Insets(20));
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
            root.getChildren().add(card);

            // Opret og vis skærmen
            Scene scene = new Scene(root, 650, 750);
            scene.getStylesheets().add(getClass().getResource("/design/styles.css").toExternalForm());
            stage.setTitle("Simpl — Log Medication");
            stage.setScene(scene);
            stage.show();
        }
    }