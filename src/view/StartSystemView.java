package view;

import controller.StartSystemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;
import model.Session;

public class StartSystemView {

    private StartSystemController controller = new StartSystemController();

    public void show(Stage stage) {

        // Labels
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("field-label");
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("field-label");
        Label messageLabel = new Label("");

        // Felter
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("modern-field");
        usernameField.setMaxWidth(Double.MAX_VALUE);
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("modern-field");
        passwordField.setMaxWidth(Double.MAX_VALUE);

        // Knapper
        Button loginButton = new Button("Log in");
        loginButton.getStyleClass().add("primary-button");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        Button createButton = new Button("Create new profile");
        createButton.getStyleClass().add("secondary-button");
        createButton.setMaxWidth(Double.MAX_VALUE);

        // Login knap med validering og hasActiveJourney check
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validering
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields!");
                return;
            }

            Patient patient = controller.handleLogin(username, password);

            if (patient != null) {
                Session.setCurrentPatient(patient);

                // Tjek om patienten har et aktivt forløb
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

        // Hjertebillede
        ImageView heartIcon = new ImageView(new Image(getClass().getResourceAsStream("/design/heart.png")));
        heartIcon.setFitWidth(200);
        heartIcon.setFitHeight(200);
        heartIcon.setPreserveRatio(true);
        VBox.setMargin(heartIcon, new Insets(-60, 0, -60, 0));
        HBox heartBox = new HBox(heartIcon);
        heartBox.setAlignment(Pos.CENTER);
        heartBox.setPrefWidth(Double.MAX_VALUE);

        // Titel og undertitel
        Label titleLabel = new Label("Simpl");
        titleLabel.getStyleClass().add("title-label");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);

        Label subtitleLabel = new Label("Din sundhedsplatform");
        subtitleLabel.getStyleClass().add("subtitle-label");
        subtitleLabel.setMaxWidth(Double.MAX_VALUE);
        subtitleLabel.setAlignment(Pos.CENTER);

        // Layout
        VBox layout = new VBox(6);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(380);
        layout.setPrefWidth(380);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                heartBox,
                titleLabel,
                subtitleLabel,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton,
                createButton,
                messageLabel
        );

        // StackPane med gradient baggrund
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Vis skærmen
        Scene scene = new Scene(root, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/design/styles.css").toExternalForm());
        stage.setTitle("Simpl — Log in");
        stage.setScene(scene);
        stage.show();
    }
}