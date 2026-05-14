package view;

import controller.ManageProfileController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ProfileView {

    // Controller der håndterer gemning af patient i databasen
    private ManageProfileController controller = new ManageProfileController();

    public void show(Stage stage) {

        // Titel øverst på skærmen
        // title-label er defineret i styles.css — grøn, stor og fed
        Label titleLabel = new Label("Create profile");
        titleLabel.getStyleClass().add("title-label");
        titleLabel.setMaxWidth(Double.MAX_VALUE); // fylder hele bredden
        titleLabel.setAlignment(Pos.CENTER); // centreret

        // Undertitel under titlen
        // subtitle-label er defineret i styles.css — grå og lille
        Label subtitleLabel = new Label("Fill in your details to get started");
        subtitleLabel.getStyleClass().add("subtitle-label");
        subtitleLabel.setMaxWidth(Double.MAX_VALUE);
        subtitleLabel.setAlignment(Pos.CENTER);

        // Labels over hvert felt
        // field-label er defineret i styles.css — grå og meget lille
        Label nameLabel = new Label("Name:");
        nameLabel.getStyleClass().add("field-label");
        Label dobLabel = new Label("Date of birth:");
        dobLabel.getStyleClass().add("field-label");
        Label diagnosisLabel = new Label("Diagnosis:");
        diagnosisLabel.getStyleClass().add("field-label");
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("field-label");
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("field-label");

        // Inputfelter brugeren kan skrive i
        // modern-field er defineret i styles.css — grå baggrund, afrundede hjørner
        // setPromptText() viser en gråtonet hjælpetekst inden brugeren skriver
        TextField nameField = new TextField();
        nameField.getStyleClass().add("modern-field");
        nameField.setMaxWidth(Double.MAX_VALUE);
        nameField.setPromptText("Full name");

        // DatePicker er en kalender-vælger til fødselsdato
        DatePicker dateOfBirthPicker = new DatePicker();
        dateOfBirthPicker.setMaxWidth(Double.MAX_VALUE);

        TextField diagnosisField = new TextField();
        diagnosisField.getStyleClass().add("modern-field");
        diagnosisField.setMaxWidth(Double.MAX_VALUE);
        diagnosisField.setPromptText("e.g. PCOS");

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("modern-field");
        usernameField.setMaxWidth(Double.MAX_VALUE);
        usernameField.setPromptText("Choose a username");

        // PasswordField viser stjerner i stedet for bogstaver
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("modern-field");
        passwordField.setMaxWidth(Double.MAX_VALUE);
        passwordField.setPromptText("Choose a password");

        // Beskedlabel — viser fejl eller bekræftelse
        Label messageLabel = new Label("");

        // Gem knap
        // primary-button er defineret i styles.css — grøn baggrund og hvid tekst
        Button saveButton = new Button("Create profile");
        saveButton.getStyleClass().add("primary-button");
        saveButton.setMaxWidth(Double.MAX_VALUE);

        // Tilbage knap
        // secondary-button er defineret i styles.css — hvid baggrund med grå kant
        Button backButton = new Button("Back to login");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);

        // Hvad sker der når brugeren klikker Gem
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

        // Hvad sker der når brugeren klikker Tilbage
        backButton.setOnAction(e -> {
            new StartSystemView().show(stage);
        });

        // VBox — lodret layout der stacker elementer oven på hinanden
        // 8 er afstanden i pixels mellem hvert element
        // login-card er defineret i styles.css — hvid baggrund med skygge og afrundede hjørner
        VBox layout = new VBox(8);
        layout.getStyleClass().add("login-card");
        layout.setMaxWidth(380); // maksimal bredde på kortet
        layout.setPrefWidth(380); // foretrukken bredde på kortet
        layout.setPadding(new Insets(40)); // 40 pixels luft indeni kortet
        layout.getChildren().addAll(
                titleLabel,
                subtitleLabel,
                nameLabel, nameField,
                dobLabel, dateOfBirthPicker,
                diagnosisLabel, diagnosisField,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                saveButton,
                backButton,
                messageLabel
        );

        // StackPane centrerer kortet på skærmen
        // setStyle() sætter gradient baggrunden direkte uden CSS fil
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40)); // 40 pixels luft rundt om kortet

        // Opret og vis skærmen
        // 500 er bredden og 700 er højden i pixels
        Scene scene = new Scene(root, 500, 700);

        // Indlæs styles.css filen — getClassLoader() finder filen fra projektets rod
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
        stage.setTitle("Simpl — Create Profile");
        stage.setScene(scene);
        stage.show();
    }
}