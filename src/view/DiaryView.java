package view;

import controller.DiaryController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import java.time.LocalDate;

public class DiaryView {

    // Opretter controller objekt
    private DiaryController controller = new DiaryController();
    public void show(Stage stage) {

        // 1. Opret felter og knapper
        TextField titleField = new TextField();
        TextField contentField = new TextField();

        Label messageLabel = new Label("");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to dashboard");

        // 2. Hvad sker der når knappen klikkes
        saveButton.setOnAction(e -> {
            String title = titleField.getText();
            String content = contentField.getText();
            controller.handleSave(LocalDate.now(), title, content);
            messageLabel.setText("Note saved");
        });
        backButton.setOnAction(e-> {
            DashboardView dashboardView = new DashboardView();
            dashboardView.show(stage, Session.getCurrentPatient());
        });

        // 3. Layout — bygges op udenfor setOnAction
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Content:"), contentField,
                backButton,
                messageLabel,
                saveButton
        );

        // 4. Vis skærmen
        Scene scene = new Scene(layout, 350, 300);
        stage.setTitle("Simpl — Diary");
        stage.setScene(scene);
        stage.show();
    }
}