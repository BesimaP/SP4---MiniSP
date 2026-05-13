package view;

import controller.DiaryController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

public class DiaryView {

    // Opretter controller objekt
    private DiaryController controller = new DiaryController();

    public void show(Stage stage) {

        // Felter og knapper
        TextField titleField = new TextField();
        TextField contentField = new TextField();
        Label messageLabel = new Label("");
        DatePicker datePicker = new DatePicker();

        // Mood dropdown
        ComboBox<String> moodBox = new ComboBox<>();
        moodBox.getItems().addAll(
                "😊 Happy",
                "😐 Neutral",
                "😢 Sad",
                "😰 Anxious",
                "😴 Tired"
        );
        moodBox.setPromptText("How are you feeling?");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to dashboard");

        // Hvad sker der når knappen klikkes
        saveButton.setOnAction(e -> {
            String title = titleField.getText();
            String content = contentField.getText();
            LocalDate date = datePicker.getValue();
            String mood = moodBox.getValue();

            // Validering
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

        // Back knap
        backButton.setOnAction(e -> {
            DashboardView dashboardView = new DashboardView();
            dashboardView.show(stage, Session.getCurrentPatient());
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Date:"), datePicker,
                new Label("How are you feeling?"), moodBox,
                new Label("Title:"), titleField,
                new Label("Content:"), contentField,
                saveButton,
                messageLabel,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout, 350, 450);
        stage.setTitle("Simpl — Diary");
        stage.setScene(scene);
        stage.show();
    }
}