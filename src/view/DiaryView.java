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

    // Opretter controller objekt som håndterer gemning i databasen
    private DiaryController controller = new DiaryController();

    public void show(Stage stage) {

        // Header
        Label titleLabel = new Label("New diary entry");
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label("Fill out all fields");
        subtitleLabel.getStyleClass().add("subtitle-label");

        VBox header = new VBox(4, titleLabel, subtitleLabel);
        header.setPadding(new Insets(0, 0, 16, 0));
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // Labels og felter
        Label dateLabel = new Label("DATE");
        dateLabel.getStyleClass().add("field-label");
        DatePicker datePicker = new DatePicker();
        datePicker.getStyleClass().add("modern-field");
        datePicker.setMaxWidth(Double.MAX_VALUE);

        Label moodLabel = new Label("MOOD");
        moodLabel.getStyleClass().add("field-label");
        // Dropdown med mood
        ComboBox<String> moodBox = new ComboBox<>();
        moodBox.getItems().addAll(
                "😊 Happy",
                "😐 Neutral",
                "😢 Sad",
                "😰 Anxious",
                "😴 Tired"
        );
        moodBox.setPromptText("How are you feeling?");
        moodBox.setStyle("-fx-font-size: 10px;");
        moodBox.getStyleClass().add("modern-field");
        moodBox.setMaxWidth(Double.MAX_VALUE);

        Label titleFieldLabel = new Label("TITLE");
        titleFieldLabel.getStyleClass().add("field-label");
        // Felt til titel
        TextField titleField = new TextField();
        titleField.setPromptText("Title of your entry");
        titleField.getStyleClass().add("modern-field");
        titleField.setMaxWidth(Double.MAX_VALUE);

        Label contentLabel = new Label("CONTENT");
        contentLabel.getStyleClass().add("field-label");
        // Felt til indhold
        TextArea contentField = new TextArea();
        contentField.setPromptText("Write your thoughts here...");
        contentField.getStyleClass().add("modern-textarea");
        contentField.setWrapText(true);
        contentField.setMaxWidth(Double.MAX_VALUE);

        // Besked der vises efter gem eller ved fejl
        Label messageLabel = new Label("");
        messageLabel.getStyleClass().add("subtitle-label");

        // Knapper
        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("primary-button");
        saveButton.setMaxWidth(Double.MAX_VALUE);

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

            // Validering — tjek at alle felter er udfyldt
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

            // Gem noten med mood
            controller.handleSave(date, title, content + "\nMood: " + mood);
            messageLabel.setText("Diary entry saved!");
        });

        // Hvad sker der når brugeren klikker Back
        backButton.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, Session.getCurrentPatient());
        });

        // Layout — VBox med 12 pixels mellem elementer
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

        // Opret og vis skærmen med gradient baggrund
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 500, 720);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());

        stage.setTitle("Simpl — Diary");
        stage.setScene(scene);
        stage.show();
    }
}