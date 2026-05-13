package view;

import controller.ManageProfileController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ProfileView {

    // Vi opretter et controller-objekt som vi bruger til at gemme patienten i databasen
    private ManageProfileController controller = new ManageProfileController();

    // show() metoden viser opret-profil skærmen
    public void show(Stage stage) {

        // Felter brugeren kan skrive i
        TextField nameField = new TextField();
        DatePicker dateOfBirthPicker = new DatePicker();
        TextField diagnosisField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Besked og knapper
        Label messageLabel = new Label("");
        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to login");

        // Hvad sker der når brugeren klikker Save
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            LocalDate dateOfBirth = dateOfBirthPicker.getValue();
            String diagnosis = diagnosisField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validering — tjek at felterne ikke er tomme
            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields!");
                return;
            }
            if (dateOfBirth == null) {
                messageLabel.setText("Please select a date of birth!");
                return;
            }

            // Gem patienten i databasen
            controller.handleCreatePatient(name, dateOfBirth, diagnosis, username, password);
            messageLabel.setText("Profile created! You can now log in.");
        });

        // Hvad sker der når brugeren klikker Back
        backButton.setOnAction(e -> {
            StartSystemView startSystemView = new StartSystemView();
            startSystemView.show(stage);
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Date of birth:"), dateOfBirthPicker,
                new Label("Diagnosis:"), diagnosisField,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                saveButton,
                messageLabel,
                backButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — Create Profile");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}