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

    // StartSystemView viser login skærmen — den første skærm brugeren ser
    public class StartSystemView {

        // Controller der håndterer login og tjek af aktivt forløb
        private StartSystemController controller = new StartSystemController();

        public void show(Stage stage) {

            // Labels over inputfelterne
            Label usernameLabel = new Label("Username:");
            usernameLabel.getStyleClass().add("field-label");
            Label passwordLabel = new Label("Password:");
            passwordLabel.getStyleClass().add("field-label");

            // Beskedlabel — viser fejl ved forkert login
            Label messageLabel = new Label("");

            // Inputfelt til brugernavn
            TextField usernameField = new TextField();
            usernameField.getStyleClass().add("modern-field");
            usernameField.setMaxWidth(Double.MAX_VALUE);

            // PasswordField viser stjerner i stedet for bogstaver
            PasswordField passwordField = new PasswordField();
            passwordField.getStyleClass().add("modern-field");
            passwordField.setMaxWidth(Double.MAX_VALUE);

            // Log ind knap — primary-button er grøn med hvid tekst
            Button loginButton = new Button("Log in");
            loginButton.getStyleClass().add("primary-button");
            loginButton.setMaxWidth(Double.MAX_VALUE);

            // Opret profil knap — secondary-button er hvid med grå kant
            Button createButton = new Button("Create new profile");
            createButton.getStyleClass().add("secondary-button");
            createButton.setMaxWidth(Double.MAX_VALUE);

            // Hvad sker der når brugeren klikker Log ind
            loginButton.setOnAction(e -> {
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Validering — tjek at begge felter er udfyldt
                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Please fill in all fields!");
                    return;
                }

                // Forsøg login via controller — returnerer Patient eller null
                Patient patient = controller.handleLogin(username, password);

                if (patient != null) {
                    // Gem patienten i Session
                    Session.setCurrentPatient(patient);

                    // Tjek om patienten allerede har et aktivt forløb
                    if (controller.hasActiveJourney(patient.getId())) {
                        // Aktivt forløb fundet — gå direkte til dashboard
                        DashboardView dashboard = new DashboardView();
                        dashboard.show(stage, patient);
                    } else {
                        // Ingen aktivt forløb — send til valg af forløbstype
                        JourneyTypeView journeyTypeView = new JourneyTypeView();
                        journeyTypeView.show(stage);
                    }
                } else {
                    // Forkert brugernavn eller adgangskode
                    messageLabel.setText("Wrong username or password!");
                }
            });

            // Enter i felterne fungerer som at klikke Log ind
            usernameField.setOnAction(e -> loginButton.fire());
            passwordField.setOnAction(e -> loginButton.fire());

            // Hvad sker der når brugeren klikker Opret profil
            createButton.setOnAction(e -> {
                ProfileView profileView = new ProfileView();
                profileView.show(stage);
            });

            // Hjertebillede øverst — hentes fra design mappen
            Image heartImage = new Image(
                    getClass().getClassLoader().getResourceAsStream("design/heart.png"),
                    200, 200, true, true
            );
            ImageView heartIcon = new ImageView(heartImage);
            heartIcon.setFitWidth(200);
            heartIcon.setFitHeight(200);
            heartIcon.setPreserveRatio(true);

            // Negativt margin trækker billedet tættere på titlen
            VBox.setMargin(heartIcon, new Insets(-60, 0, -60, 0));

            // HBox centrerer hjertet vandret
            HBox heartBox = new HBox(heartIcon);
            heartBox.setAlignment(Pos.CENTER);
            heartBox.setPrefWidth(Double.MAX_VALUE);

            // Titel og undertitel — begge centrerede
            Label titleLabel = new Label("Simpl");
            titleLabel.getStyleClass().add("title-label");
            titleLabel.setMaxWidth(Double.MAX_VALUE);
            titleLabel.setAlignment(Pos.CENTER);

            Label subtitleLabel = new Label("Your health companion");
            subtitleLabel.getStyleClass().add("subtitle-label");
            subtitleLabel.setMaxWidth(Double.MAX_VALUE);
            subtitleLabel.setAlignment(Pos.CENTER);

            // VBox — lodret layout der samler alle elementer
            // card er defineret i styles.css — hvid baggrund med skygge og afrundede hjørner
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

            // StackPane centrerer kortet på skærmen med gradient baggrund
            StackPane root = new StackPane(layout);
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
            root.setPadding(new Insets(40));

            // Opret og vis skærmen
            Scene scene = new Scene(root, 500, 700);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
            stage.setTitle("Simpl — Log in");
            stage.setScene(scene);
            stage.show();
        }
    }