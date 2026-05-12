package view;

// Vi importerer de klasser vi skal bruge
// StartSystemController håndterer login-logikken
// Patient er den klasse der repræsenterer en patient
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
import view.ProfileView;
import view.DashboardView;

public class StartSystemView {

    // Vi opretter et controller-objekt som vi bruger til at håndtere login
    // Controller ved hvordan man tjekker brugernavn og adgangskode i databasen
    private StartSystemController controller = new StartSystemController();

    // show() metoden viser login-skærmen
    // stage er vinduet vi viser skærmen i
    public void show(Stage stage) {

        // Label er tekst der vises på skærmen — brugeren kan ikke skrive i den
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        // messageLabel bruges til at vise fejlbeskeder eller velkomstbeskeder
        // Den starter tom — der vises ingenting
        Label messageLabel = new Label("");

        // TextField er et felt brugeren kan skrive i
        TextField usernameField = new TextField();

        // PasswordField er ligesom TextField men viser stjerner i stedet for bogstaver
        PasswordField passwordField = new PasswordField();

        // Button er en knap brugeren kan klikke på
        Button loginButton = new Button("Log in");
        Button createButton = new Button("Create new profile");

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

        // Når brugeren klikker Create new profile åbner vi ProfileView
        // Vi sender stage med så ProfileView kan vises i samme vindue
        createButton.setOnAction(e -> {
            ProfileView profileView = new ProfileView();
            profileView.show(stage);
        });

        // VBox er et lodret layout — elementerne stables oven på hinanden
        // 10 er afstanden mellem elementerne i pixels
        VBox layout = new VBox(10);

        // Insets(20) giver 20 pixels luft rundt om alle elementerne
        layout.setPadding(new Insets(20));

        // Vi tilføjer alle elementer til layoutet i den rækkefølge de skal vises
        layout.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton,
                createButton,
                messageLabel
        );

        // Scene er selve indholdet af vinduet
        // 300 er bredden og 300 er højden i pixels
        Scene scene = new Scene(layout, 300, 300);

        // Sæt titlen på vinduet — det der vises øverst i vinduesrammen
        stage.setTitle("Simpl — Log in");

        // Sæt scenen på vinduet
        stage.setScene(scene);

        // Vis vinduet
        stage.show();
    }
}