package view;

import controller.ManageProfileController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileView {

    // Controller
    private ManageProfileController controller = new ManageProfileController();

    // Vis opret-profil skærmen
    public void show(Stage stage) {

        // Felter
        TextField nameField = new TextField();
        TextField dateOfBirthField = new TextField();
        TextField diagnosisField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button saveButton = new Button("Save");

        // Hvad sker der når brugeren klikker Gem
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String dateOfBirth = dateOfBirthField.getText();
            String diagnosis = diagnosisField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Kald controller
            controller.handleCreatePatient(
                    name,
                    java.time.LocalDate.parse(dateOfBirth),
                    diagnosis,
                    username,
                    password
            );
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Date of birth (yyyy-mm-dd):"), dateOfBirthField,
                new Label("Diagnosis:"), diagnosisField,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                saveButton
        );

        // Vis skærmen
        Scene scene = new Scene(layout, 350, 400);
        stage.setTitle("Simpl — Create Profile");
        stage.setScene(scene);
        stage.show();
    }
}