package view;

// Vi importerer de JavaFX klasser vi skal bruge
// Scene er selve indholdet af vinduet
// Stage er vinduet
// VBox er et lodret layout
// Button er en knap
// Label er tekst
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import model.Patient;

public class DashboardView {

    // show() metoden viser dashboardet på skærmen
    // Den tager to ting med sig:
    // stage = vinduet vi viser skærmen i
    // patient = den patient der er logget ind
    public void show(Stage stage, Patient patient) {

        model.Session.setCurrentPatient(patient);
        // Label er bare tekst der vises på skærmen
        // Vi bruger patient.getName() til at hente patientens navn
        // Så vises "Welcome Anna!" hvis patienten hedder Anna
        Label welcomeLabel = new Label("Welcome " + patient.getName() + "!");

        // Button er en knap brugeren kan klikke på
        // Teksten i parentesen er det der står på knappen
        Button hormoneButton = new Button("Log hormone value");
        hormoneButton.setOnAction(e -> {
            HormoneLogView hormoneLogView = new HormoneLogView();
            hormoneLogView.show(stage);
        });
        Button medicationButton = new Button("Log medication");
        Button appointmentButton = new Button("Add appointment");
        Button diaryButton = new Button("Diary");
        diaryButton.setOnAction(e ->{
            DiaryView diaryView = new DiaryView();
            diaryView.show(stage);
        });
        Button timelineButton = new Button("Timeline");
        Button historyButton = new Button("Round history");
        Button newRoundButton = new Button("Start new round");
        Button endRoundButton = new Button("End round");

        // VBox er et lodret layout — elementerne stables oven på hinanden
        // 10 er afstanden mellem elementerne i pixels
        VBox layout = new VBox(10);

        // setPadding giver luft rundt om elementerne
        // Insets(20) betyder 20 pixels luft på alle sider
        layout.setPadding(new Insets(20));

        // getChildren().addAll() tilføjer alle elementer til layoutet
        // De vises i den rækkefølge vi tilføjer dem
        layout.getChildren().addAll(
                welcomeLabel,
                hormoneButton,
                medicationButton,
                appointmentButton,
                diaryButton,
                timelineButton,
                historyButton,
                newRoundButton,
                endRoundButton
        );

        // Scene er selve indholdet af vinduet
        // 300 er bredden og 400 er højden i pixels
        Scene scene = new Scene(layout, 300, 400);

        // Sæt titlen på vinduet
        stage.setTitle("Simpl — Dashboard");

        // Sæt scenen på vinduet
        stage.setScene(scene);

        // Vis vinduet
        stage.show();
    }
}