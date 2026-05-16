package view;

import controller.DiaryController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

    // DiaryView viser skærmen hvor patienten kan skrive en dagbogsnotat
    public class DiaryView {

        // Controller der håndterer gemning af dagbogsnoter
        private DiaryController controller = new DiaryController();

        public void show(Stage stage) {

            // Header med titel og undertitel
            Label titleLabel = new Label("New diary entry");
            titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");
            Label subtitleLabel = new Label("Fill out all fields");
            subtitleLabel.getStyleClass().add("subtitle-label");

            // VBox samler titel og undertitel med en bundkant som separator
            VBox header = new VBox(4, titleLabel, subtitleLabel);
            header.setPadding(new Insets(0, 0, 16, 0));
            header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

            // Dato felt
            Label dateLabel = new Label("DATE");
            dateLabel.getStyleClass().add("field-label");
            DatePicker datePicker = new DatePicker();
            datePicker.getStyleClass().add("modern-field");
            datePicker.setMaxWidth(Double.MAX_VALUE);

            // Humør dropdown
            Label moodLabel = new Label("MOOD");
            moodLabel.getStyleClass().add("field-label");
            ComboBox<String> moodBox = new ComboBox<>();
            moodBox.getItems().addAll(
                    "Happy",
                    "Neutral",
                    "Sad",
                    "Anxious",
                    "Tired"
            );
            moodBox.setPromptText("How are you feeling?");
            moodBox.setStyle("-fx-font-size: 10px;");
            moodBox.getStyleClass().add("modern-field");
            moodBox.setMaxWidth(Double.MAX_VALUE);

            // Titel felt
            Label titleFieldLabel = new Label("TITLE");
            titleFieldLabel.getStyleClass().add("field-label");
            TextField titleField = new TextField();
            titleField.setPromptText("Title of your entry");
            titleField.getStyleClass().add("modern-field");
            titleField.setMaxWidth(Double.MAX_VALUE);

            // Indhold felt — TextArea tillader flere linjer tekst
            Label contentLabel = new Label("CONTENT");
            contentLabel.getStyleClass().add("field-label");
            TextArea contentField = new TextArea();
            contentField.setPromptText("Write your thoughts here...");
            contentField.getStyleClass().add("modern-textarea");
            contentField.setWrapText(true); // teksten brydes automatisk ved linjeskift
            contentField.setMaxWidth(Double.MAX_VALUE);

            // Beskedlabel — viser fejl eller bekræftelse
            Label messageLabel = new Label("");
            messageLabel.getStyleClass().add("subtitle-label");

            // Ryd beskeden automatisk når brugeren ændrer noget i felterne
            datePicker.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
            moodBox.valueProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
            titleField.textProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));
            contentField.textProperty().addListener((obs, oldVal, newVal) -> messageLabel.setText(""));

            // Gem knap
            Button saveButton = new Button("Save");
            saveButton.getStyleClass().add("primary-button");
            saveButton.setMaxWidth(Double.MAX_VALUE);

            // Tilbage knap
            Button backButton = new Button("Back to dashboard");
            backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
            backButton.getStyleClass().add("secondary-button");
            backButton.setMaxWidth(Double.MAX_VALUE);

            // Hvad sker der når brugeren klikker Save
            saveButton.setOnAction(e -> {
                String title = titleField.getText();
                String content = contentField.getText();
                LocalDate date = datePicker.getValue();
                String mood = moodBox.getValue();

                // Validering — tjek at alle påkrævede felter er udfyldt
                if (title.isEmpty()) {
                    messageLabel.setText("Title cannot be empty!");
                    return;
                }
                if (content.isEmpty()) {
                    messageLabel.setText("Content cannot be empty!");
                    return;
                }
                if (date == null) {
                    messageLabel.setText("Please select a date!");
                    return;
                }

                // Gem noten i databasen — mood tilføjes til indholdet
                controller.handleSave(date, title, content + "\nMood: " + mood);
                messageLabel.setText("Diary entry saved!");
            });

            // Hvad sker der når brugeren klikker Back
            backButton.setOnAction(e -> {
                new DashboardView().show(stage, Session.getCurrentPatient());
            });

            // VBox — lodret layout der samler alle elementer
            VBox layout = new VBox(12);
            layout.getStyleClass().add("card");
            layout.setMaxWidth(400);
            layout.setPrefWidth(400);
            layout.setPadding(new Insets(28));
            layout.getChildren().addAll(
                    header,
                    dateLabel, datePicker,
                    moodLabel, moodBox,
                    titleFieldLabel, titleField,
                    contentLabel, contentField,
                    saveButton,
                    messageLabel,
                    backButton
            );

            // StackPane centrerer kortet på skærmen med gradient baggrund
            StackPane root = new StackPane(layout);
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
            root.setPadding(new Insets(40));

            // Opret og vis skærmen
            Scene scene = new Scene(root, 650, 750);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
            stage.setTitle("Simpl — Diary");
            stage.setScene(scene);
            stage.show();
        }
}