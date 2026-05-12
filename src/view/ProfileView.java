package view;

// ManageProfileController håndterer oprettelse af en ny patient i databasen
// LocalDate bruges til at håndtere datoer
import controller.ManageProfileController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileView {

    // Vi opretter et controller-objekt som vi bruger til at gemme patienten i databasen
    private ManageProfileController controller = new ManageProfileController();

    // show() metoden viser opret-profil skærmen
    // stage er vinduet vi viser skærmen i
    public void show(Stage stage) {

        // TextField er felter brugeren kan skrive i
        TextField nameField = new TextField();
        TextField dateOfBirthField = new TextField();
        TextField diagnosisField = new TextField();
        TextField usernameField = new TextField();

        // PasswordField viser stjerner i stedet for bogstaver
        PasswordField passwordField = new PasswordField();

        // Save knappen gemmer patienten i databasen
        Button saveButton = new Button("Save");

        // setOnAction bestemmer hvad der sker når brugeren klikker Save
        saveButton.setOnAction(e -> {

            // getText() henter hvad brugeren har skrevet i hvert felt
            String name = nameField.getText();
            String dateOfBirth = dateOfBirthField.getText();
            String diagnosis = diagnosisField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Vi sender alle oplysninger til controlleren
            // LocalDate.parse() konverterer teksten "1990-01-01" til en dato
            // Controlleren gemmer patienten i databasen
            controller.handleCreatePatient(
                    name,
                    java.time.LocalDate.parse(dateOfBirth),
                    diagnosis,
                    username,
                    password
            );
        });

        // VBox er et lodret layout — elementerne stables oven på hinanden
        // 10 er afstanden mellem elementerne i pixels
        VBox layout = new VBox(10);

        // Insets(20) giver 20 pixels luft rundt om alle elementerne
        layout.setPadding(new Insets(20));

        // Vi tilføjer labels og felter til layoutet
        // new Label("Name:") opretter en tekst direkte uden at gemme den i en variabel
        layout.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Date of birth (yyyy-mm-dd):"), dateOfBirthField,
                new Label("Diagnosis:"), diagnosisField,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                saveButton
        );

        // Scene er selve indholdet af vinduet
        // 350 er bredden og 400 er højden i pixels
        Scene scene = new Scene(layout, 350, 400);

        // Sæt titlen på vinduet
        stage.setTitle("Simpl — Create Profile");

        // Sæt scenen og vis vinduet
        stage.setScene(scene);
        stage.show();
    }
}