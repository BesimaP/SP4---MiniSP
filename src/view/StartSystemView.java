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

        // Titel øverst
        Label titleLabel = new Label("Welcome to Simpl 🌸");

        // Labels og felter
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("field-label"); //css
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("field-label"); //css

        // messageLabel bruges til at vise fejlbeskeder eller velkomstbeskeder
        // Den starter tom — der vises ingenting
        Label messageLabel = new Label("");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("modern-field"); //css
        usernameField.setMaxWidth(Double.MAX_VALUE); //css

        // PasswordField er ligesom TextField men viser stjerner i stedet for bogstaver
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("modern-field"); //css
        passwordField.setMaxWidth(Double.MAX_VALUE); //css

        // Knapper
        Button loginButton = new Button("Log in");
        loginButton.getStyleClass().add("primary-button"); //css
        loginButton.setMaxWidth(Double.MAX_VALUE); //css
        Button createButton = new Button("Create new profile");
        createButton.getStyleClass().add("secondary-button"); //css
        createButton.setMaxWidth(Double.MAX_VALUE); //css

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
        // VBox er et lodret layout — elementerne stables oven på hinanden
        // 12 er afstanden mellem elementerne i pixels
        VBox layout = new VBox(6);
        layout.getStyleClass().add("login-card");
        layout.setMaxWidth(380);
        layout.setPrefWidth(380);

        //Hjerte icon
        ImageView heartIcon = new ImageView(new Image(getClass().getResourceAsStream("/design/heart.png")));
        heartIcon.setFitWidth(200);
        heartIcon.setFitHeight(200);
        heartIcon.setPreserveRatio(true);
        VBox.setMargin(heartIcon, new Insets(-60, 0, -60, 0)); //top, right, bottom, left
        HBox heartBox = new HBox(heartIcon);
        heartBox.setAlignment(Pos.CENTER);
        heartBox.setPrefWidth(Double.MAX_VALUE);

        Label titleLabel = new Label("Simpl");
        titleLabel.getStyleClass().add("title-label");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);

        Label subtitleLabel = new Label("Din sundhedsplatform");
        subtitleLabel.getStyleClass().add("subtitle-label");
        subtitleLabel.setMaxWidth(Double.MAX_VALUE);
        subtitleLabel.setAlignment(Pos.CENTER);

        // Insets(20) giver 20 pixels luft rundt om alle elementerne
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                heartBox,
                titleLabel, subtitleLabel,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton,
                createButton,
                messageLabel
        );

        // Vis skærmen
        Scene scene = new Scene(layout);
        // Scene er selve indholdet af vinduet
        // 500 er bredden og 600 er højden i pixels
        StackPane root = new StackPane(layout);
        // baggrundfarven i konsollen med farve-fade
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));
        Scene scene = new Scene(root, 500, 700);
        // Henter css dokumentet der laver pretty stuff :D
        scene.getStylesheets().add(getClass().getResource("/design/styles.css").toExternalForm());

        // Sæt titlen på vinduet — det der vises øverst i vinduesrammen
        stage.setTitle("Simpl — Log in");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}