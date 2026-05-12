package view;

import controller.StartSystemController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartSystemView {

    // Controller der håndterer login
    private StartSystemController controller = new StartSystemController();

    // Vis login-skærmen
    public void show(Stage stage) {

        // Labels — tekst over felterne
        Label usernameLabel = new Label("Brugernavn:");
        Label passwordLabel = new Label("Adgangskode:");

        // Tekstfelter — brugeren skriver her
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Knap
        Button loginButton = new Button("Log ind");

        // Hvad sker der når brugeren klikker Log ind
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            controller.handleLogin(username, password);
        });

        // VBox — elementerne stables lodret
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton
        );

        // Opret og vis skærmen
        Scene scene = new Scene(layout, 300, 250);
        stage.setTitle("Simpl — Log ind");
        stage.setScene(scene);
        stage.show();
    }
}