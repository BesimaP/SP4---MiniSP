package view;

// Vi importerer de klasser vi skal bruge fra JavaFX og vores eget projekt
import controller.TimelineController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.Session;
import java.util.ArrayList;

// TimelineView viser en kronologisk liste over alle hændelser i patientens forløb
public class TimelineView {

    // Vi opretter et controller-objekt som henter data fra databasen
    // Controlleren er "mellemmanden" mellem view og model (MVC arkitektur)
    private TimelineController controller = new TimelineController();

    // show-metoden bygger hele skærmen op og viser den
    // Stage er JavaFX-vinduet som skærmen vises i
    public void show(Stage stage) {

        // ============== HEADER (øverst i kortet) ==============

        // Stor grøn titel-tekst der står øverst
        Label titleLabel = new Label("Timeline:");
        // setStyle giver elementet CSS direkte i koden (i stedet for via styles.css)
        // -fx-text-fill = tekstfarve, -fx-font-size = størrelse, -fx-font-weight = fed/normal
        titleLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Lille grå undertekst under titlen
        Label subtitleLabel = new Label("All events in your journey");
        // Her bruger vi en CSS-klasse fra styles.css i stedet for setStyle
        subtitleLabel.getStyleClass().add("subtitle-label");

        // En VBox er en lodret container — den stabler elementer oven på hinanden
        // Tallet 4 = 4 pixels mellem hvert element
        VBox header = new VBox(4, titleLabel, subtitleLabel);
        // Padding = luft INDEN i boksen (top, højre, bund, venstre)
        // Her: 16 pixels luft i bunden så headeren ikke klistrer til resten
        header.setPadding(new Insets(0, 0, 16, 0));
        // Tilføjer en tynd grå streg UNDER headeren som adskillelse
        header.setStyle("-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-border-width: 1;");

        // ============== HENT DATA FRA DATABASEN ==============

        // controller.initialize() henter alle Event-objekter fra databasen
        // og returnerer dem som en ArrayList (en liste vi kan loop igennem)
        ArrayList<Event> events = controller.initialize();

        // Lille label der viser hvor mange hændelser der er total
        // events.size() returnerer antallet af elementer i listen
        Label totalLabel = new Label("TOTAL EVENTS: " + events.size());
        totalLabel.getStyleClass().add("section-label");

        // ============== LISTEN MED HÆNDELSER ==============

        // ListView er JavaFX's standard liste-element — vi siger den indeholder String
        ListView<String> eventList = new ListView<>();
        // Brug vores egen modern-list CSS-klasse fra styles.css (pænt grønt look)
        eventList.getStyleClass().add("modern-list");
        // Bestem hvor høj listen skal være (i pixels)
        eventList.setPrefHeight(350);

        // For-each loop: gennemløb hver Event i listen
        // For hvert event, lav en tekst-streng og tilføj den til ListView'en
        for (Event event : events) {
            eventList.getItems().add(
                    // Vi sammensætter en pæn tekst: dato — type — beskrivelse
                    event.getDate() + " — " + event.getType() + " — " + event.getDescription()
            );
        }

        // Hvis listen er tom (ingen hændelser endnu), så vis en besked
        // isEmpty() returnerer true hvis der ikke er nogen elementer
        if (events.isEmpty()) {
            eventList.getItems().add("No events found");
        }

        // ============== BACK KNAP ==============

        // Knap der sender brugeren tilbage til dashboardet
        Button backButton = new Button("Back to dashboard");
        // Grøn fed tekst på knappen
        backButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
        // secondary-button = hvid knap med kant (i modsætning til primary-button som er grøn)
        backButton.getStyleClass().add("secondary-button");
        // Få knappen til at fylde hele bredden af kortet
        backButton.setMaxWidth(Double.MAX_VALUE);

        // setOnAction = hvad sker der når brugeren klikker på knappen
        // e -> { ... } er et lambda-udtryk (en kort måde at skrive en metode på)
        backButton.setOnAction(e -> {
            // Lav et nyt DashboardView objekt og vis det
            DashboardView dashboard = new DashboardView();
            // Send den nuværende patient med, så dashboardet ved hvem der er logget ind
            dashboard.show(stage, Session.getCurrentPatient());
        });

        // ============== LAYOUT (saml alle elementerne) ==============

        // Endnu en VBox — denne gang containeren for HELE kortet
        // 12 = 12 pixels mellem hvert af de store sektioner
        VBox layout = new VBox(12);
        // card = hvidt baggrund, runde hjørner og skygge (defineret i styles.css)
        layout.getStyleClass().add("card");
        // Maks bredde af kortet = 500 pixels
        layout.setMaxWidth(500);
        layout.setPrefWidth(500);
        // 28 pixels luft inden i kortet hele vejen rundt
        layout.setPadding(new Insets(28));
        // Tilføj elementerne i den rækkefølge de skal vises (oppefra og ned)
        layout.getChildren().addAll(
                header,         // Først headeren med titel
                totalLabel,     // Så "TOTAL EVENTS: X"
                eventList,      // Så selve listen
                backButton      // Til sidst back-knappen
        );

        // ============== BAGGRUND (gradient bag kortet) ==============

        // StackPane = en container der lægger ting oven på hinanden (som "stak")
        // Vi lægger kortet (layout) OVENPÅ gradient-baggrunden
        StackPane root = new StackPane(layout);
        // linear-gradient = en farve der glider fra én farve til en anden
        // Her: lysegrøn øverst venstre, lyseblå nederst højre
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        // 40 pixels luft rundt om kortet (mellem kort og kant af vinduet)
        root.setPadding(new Insets(40));

        // ============== VIS SKÆRMEN ==============

        // Scene = "scenen" der vises i vinduet — bredde 600, højde 650 pixels
        Scene scene = new Scene(root, 600, 650);
        // Tilføj vores CSS-fil så alle CSS-klasserne virker
        // getClassLoader().getResource("design/styles.css") finder filen i src/design/
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());

        // Sæt titlen på vinduet (vises i toppen af vinduet og i Windows taskbar)
        stage.setTitle("Simpl — Timeline");
        // Sæt scenen på vinduet
        stage.setScene(scene);
        // Vis vinduet (uden denne linje ville skærmen ikke komme frem)
        stage.show();
    }
}