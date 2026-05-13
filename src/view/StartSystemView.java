package view;

// Vi importerer de klasser vi skal bruge
// StartSystemController håndterer login-logikken
// Patient er den klasse der repræsenterer en patient
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ProfileView;
import view.DashboardView;

public class StartSystemView {

    private static final Logger log = LoggerFactory.getLogger(StartSystemView.class);
    // Vi opretter et controller-objekt som vi bruger til at håndtere login
    // Controller ved hvordan man tjekker brugernavn og adgangskode i databasen
    private StartSystemController controller = new StartSystemController();

    // show() metoden viser login-skærmen
    // stage er vinduet vi viser skærmen i
    public void show(Stage stage) {

        // Label er tekst der vises på skærmen — brugeren kan ikke skrive i den
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("field-label"); //css
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("field-label"); //css

        // messageLabel bruges til at vise fejlbeskeder eller velkomstbeskeder
        // Den starter tom — der vises ingenting
        Label messageLabel = new Label("");

        // TextField er et felt brugeren kan skrive i
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("modern-field"); //css
        usernameField.setMaxWidth(Double.MAX_VALUE); //css

        // PasswordField er ligesom TextField men viser stjerner i stedet for bogstaver
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("modern-field"); //css
        passwordField.setMaxWidth(Double.MAX_VALUE); //css

        // Button er en knap brugeren kan klikke på
        Button loginButton = new Button("Log in");
        loginButton.getStyleClass().add("primary-button"); //css
        loginButton.setMaxWidth(Double.MAX_VALUE); //css
        Button createButton = new Button("Create new profile");
        createButton.getStyleClass().add("secondary-button"); //css
        createButton.setMaxWidth(Double.MAX_VALUE); //css

        // setOnAction bestemmer hvad der sker når brugeren klikker på knappen
        // e er hændelsen der sker når knappen klikkes — vi bruger den ikke men den skal være der
        loginButton.setOnAction(e -> {

            // getText() henter hvad brugeren har skrevet i feltet
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Vi sender brugernavn og adgangskode til controlleren
            // Controlleren tjekker om de findes i databasen
            // Hvis de findes returnerer den et Patient-objekt — ellers null
            Patient patient = controller.handleLogin(username, password);

            // Hvis patient ikke er null betyder det at login var korrekt
            if (patient != null) {
                Session.setCurrentPatient(patient);
                // Vi opretter et DashboardView og viser det
                // Vi sender stage og patient med så dashboardet ved hvem der er logget ind
                DashboardView dashboard = new DashboardView();
                dashboard.show(stage, patient);
            } else {
                // Hvis patient er null var login forkert
                // Vi viser en fejlbesked i messageLabel
                messageLabel.setText("Wrong username or password!");
            }
        });
        usernameField.setOnAction(e -> {
            loginButton.fire();
        });
        passwordField.setOnAction(e -> {
            loginButton.fire();
        });



        // Når brugeren klikker Create new profile åbner vi ProfileView
        // Vi sender stage med så ProfileView kan vises i samme vindue
        createButton.setOnAction(e -> {
            ProfileView profileView = new ProfileView();
            profileView.show(stage);
        });

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

        // Vi tilføjer alle elementer til layoutet i den rækkefølge de skal vises
        layout.getChildren().addAll(
                heartBox,
                titleLabel, subtitleLabel,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton,
                createButton,
                messageLabel
        );

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

        // Sæt scenen på vinduet
        stage.setScene(scene);

        // Vis vinduet
        stage.show();
    }
}