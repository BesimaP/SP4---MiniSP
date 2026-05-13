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
import model.Patient;
import model.Session;

public class StartSystemView {

    private StartSystemController controller = new StartSystemController();

    public void show(Stage stage) {

        // Titel øverst
        Label titleLabel = new Label("Welcome to Simpl 🌸");

        // Labels og felter
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label messageLabel = new Label("");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Knapper
        Button loginButton = new Button("Log in");
        Button createButton = new Button("Create new profile");

        // Log in knap
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validering — tjek at felterne ikke er tomme
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields!");
                return;
            }

            Patient patient = controller.handleLogin(username, password);

            if (patient != null) {
                Session.setCurrentPatient(patient);

                if (controller.hasActiveJourney(patient.getId())) {
                    DashboardView dashboard = new DashboardView();
                    dashboard.show(stage, patient);
                } else {
                    JourneyTypeView journeyTypeView = new JourneyTypeView();
                    journeyTypeView.show(stage);
                }
            } else {
                messageLabel.setText("Wrong username or password!");
            }
        });

        // Enter i felterne logger ind
        usernameField.setOnAction(e -> loginButton.fire());
        passwordField.setOnAction(e -> loginButton.fire());

        // Create new profile knap
        createButton.setOnAction(e -> {
            ProfileView profileView = new ProfileView();
            profileView.show(stage);
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton,
                createButton,
                messageLabel
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        stage.setTitle("Simpl — Log in");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}