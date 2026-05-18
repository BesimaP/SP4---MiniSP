package view;

import controller.AppointmentController;
import controller.DiaryController;
import controller.HormoneLogController;
import controller.RoundHistoryController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;
import model.Session;
import java.time.LocalDate;
import java.util.ArrayList;

// DashboardView viser hovedskærmen med statistik og navigation til alle views
public class DashboardView {

    public void show(Stage stage, Patient patient) {

        // Gem patienten i Session så andre views kan hente den
        Session.setCurrentPatient(patient);

        // Tjek om patienten har kommende aftaler og vis en pæn popup
        // isReminderShown() sikrer at popup kun vises én gang per login
        AppointmentController appointmentController = new AppointmentController();
        ArrayList<String> appointments = appointmentController.getUpcomingAppointments();

        if (!appointments.isEmpty() && !Session.isReminderShown()) {
            Session.setReminderShown(true); // husk at popup er vist

            // Opret et nyt vindue til påmindelsen
            Stage reminderStage = new Stage();
            reminderStage.setTitle("Reminder");

            // Titel
            Label titleLabel = new Label("📅  Upcoming appointments");
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

            // Separator under titlen
            Separator sep = new Separator();

            // Liste over aftaler — én label per aftale
            VBox appointmentList = new VBox(8);
            for (String appt : appointments) {
                Label apptLabel = new Label("• " + appt);
                apptLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
                apptLabel.setWrapText(true);
                appointmentList.getChildren().add(apptLabel);
            }

            // Luk knap
            Button closeButton = new Button("OK, got it!");
            closeButton.getStyleClass().add("primary-button");
            closeButton.setMaxWidth(Double.MAX_VALUE);
            closeButton.setOnAction(e -> reminderStage.close());

            // Layout
            VBox popupLayout = new VBox(12);
            popupLayout.getStyleClass().add("card");
            popupLayout.setPadding(new Insets(24));
            popupLayout.setMaxWidth(340);
            popupLayout.setPrefWidth(340);
            popupLayout.getChildren().addAll(titleLabel, sep, appointmentList, closeButton);

            // Baggrund med gradient
            StackPane popupRoot = new StackPane(popupLayout);
            popupRoot.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
            popupRoot.setPadding(new Insets(20));

            // Vis popup med styles
            Scene reminderScene = new Scene(popupRoot);
            reminderScene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
            reminderStage.setScene(reminderScene);
            reminderStage.setResizable(false);
            reminderStage.show();
        }

        // Labels der viser patientens information øverst
        Label welcomeLabel = new Label("Welcome " + patient.getName() + "!");
        welcomeLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 20px; -fx-font-weight: bold;");
        Label dateLabel = new Label("Today: " + LocalDate.now());
        dateLabel.getStyleClass().add("subtitle-label");
        Label diagnosisLabel = new Label("Diagnosis: " + patient.getDiagnosis());
        diagnosisLabel.getStyleClass().add("subtitle-label");

        // Hent statistik data fra databasen via controllers
        HormoneLogController hormoneController = new HormoneLogController();
        DiaryController diaryController = new DiaryController();
        RoundHistoryController roundController = new RoundHistoryController();
        AppointmentController statsApptController = new AppointmentController();

        String latestHormone = hormoneController.getLatestHormoneValue();
        int diaryCount = diaryController.countDiaryEntries();
        int roundCount = roundController.initialize().size();
        String nextAppt = statsApptController.getUpcomingAppointments().isEmpty()
                ? "-"
                : statsApptController.getUpcomingAppointments().get(0);

        // ---- STATISTIK KORT 1: Seneste hormonværdi ----
        // Parser hormonværdien så den vises pænt — fx "Oestradiol: 450 pmol/L"
        // bliver til titel: "Oestradiol", værdi: "450", kontekst: "pmol/L"
        String hormoneTitle = "Latest hormone";
        String hormoneValueStr = "-";
        String hormoneContext = "No data yet";

        if (!latestHormone.equals("-") && latestHormone.contains(":")) {
            String[] parts = latestHormone.split(":");
            hormoneTitle = parts[0].trim();
            String rest = parts[1].trim();
            int spaceIdx = rest.indexOf(" ");
            if (spaceIdx > 0) {
                hormoneValueStr = rest.substring(0, spaceIdx);
                hormoneContext = rest.substring(spaceIdx + 1);
            } else {
                hormoneValueStr = rest;
                hormoneContext = "";
            }
        }

        Label hormoneTitleLbl = new Label(hormoneTitle);
        hormoneTitleLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");
        Label hormoneValueLbl = new Label(hormoneValueStr);
        hormoneValueLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        Label hormoneContextLbl = new Label(hormoneContext);
        hormoneContextLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: #888;");
        VBox hormoneBox = new VBox(2, hormoneTitleLbl, hormoneValueLbl, hormoneContextLbl);
        hormoneBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 12;");

        // ---- STATISTIK KORT 2: Næste aftale ----
        // Parser aftalen så datoen vises pænt — fx "15. May"
        String apptTitle = "Next appointment";
        String apptValueStr = "-";
        String apptContext = "No appointments";

        if (!nextAppt.equals("-") && nextAppt.contains("—")) {
            String[] parts = nextAppt.split("—");
            if (parts.length >= 2) {
                String type = parts[0].trim();
                String date = parts[1].trim();
                apptContext = type;
                try {
                    LocalDate apptDate = LocalDate.parse(date);
                    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                    apptValueStr = apptDate.getDayOfMonth() + ". " + months[apptDate.getMonthValue() - 1];
                } catch (Exception ex) {
                    apptValueStr = date;
                }
            }
        }

        Label apptTitleLbl = new Label(apptTitle);
        apptTitleLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");
        Label apptValueLbl = new Label(apptValueStr);
        apptValueLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        Label apptContextLbl = new Label(apptContext);
        apptContextLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: #888;");
        VBox apptBox = new VBox(2, apptTitleLbl, apptValueLbl, apptContextLbl);
        apptBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 12;");

        // ---- STATISTIK KORT 3: Antal runder ----
        Label roundTitleLbl = new Label("Total rounds");
        roundTitleLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");
        Label roundValueLbl = new Label(roundCount == 0 ? "-" : String.valueOf(roundCount));
        roundValueLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        Label roundContextLbl = new Label("Fertility");
        roundContextLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: #888;");
        VBox roundBox = new VBox(2, roundTitleLbl, roundValueLbl, roundContextLbl);
        roundBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 12;");

        // ---- STATISTIK KORT 4: Antal dagbogsnoter ----
        Label diaryTitleLbl = new Label("Diary entries");
        diaryTitleLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");
        Label diaryValueLbl = new Label(diaryCount == 0 ? "-" : String.valueOf(diaryCount));
        diaryValueLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        Label diaryContextLbl = new Label("This round");
        diaryContextLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: #888;");
        VBox diaryBox = new VBox(2, diaryTitleLbl, diaryValueLbl, diaryContextLbl);
        diaryBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 12;");

        // GridPane med 4 statistik kort side om side — hvert kort fylder 25%
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(10);
        ColumnConstraints statCol = new ColumnConstraints();
        statCol.setPercentWidth(25);
        statsGrid.getColumnConstraints().addAll(statCol, statCol, statCol, statCol);
        statsGrid.add(hormoneBox, 0, 0);
        statsGrid.add(apptBox, 1, 0);
        statsGrid.add(roundBox, 2, 0);
        statsGrid.add(diaryBox, 3, 0);

        // Separator er en vandret linje der adskiller sektioner
        Separator separator1 = new Separator();

        // Overskrifter til de tre sektioner
        Label loggingLabel = new Label("LOG DATA");
        loggingLabel.getStyleClass().add("section-label");
        Label planningLabel = new Label("PLANNING");
        planningLabel.getStyleClass().add("section-label");
        Label historyLabel = new Label("ROUND");
        historyLabel.getStyleClass().add("section-label");

        // Knapper til LOG DATA sektionen — grønlige kort
        Button hormoneButton = new Button("Log hormone value");
        hormoneButton.getStyleClass().add("card-green");
        hormoneButton.setMaxWidth(Double.MAX_VALUE);
        hormoneButton.setGraphic(createIcon("design/appointments.png"));
        hormoneButton.setContentDisplay(ContentDisplay.TOP);

        Button medicationButton = new Button("Log medication");
        medicationButton.getStyleClass().add("card-green");
        medicationButton.setMaxWidth(Double.MAX_VALUE);
        medicationButton.setGraphic(createIcon("design/medication.png"));
        medicationButton.setContentDisplay(ContentDisplay.TOP);

        // Knapper til PLANNING sektionen — blålige kort
        Button appointmentButton = new Button("Appoint.");
        appointmentButton.getStyleClass().add("card-blue");
        appointmentButton.setMaxWidth(Double.MAX_VALUE);
        appointmentButton.setGraphic(createIcon("design/appointments.png"));
        appointmentButton.setContentDisplay(ContentDisplay.TOP);

        Button diaryButton = new Button("Diary");
        diaryButton.getStyleClass().add("card-blue");
        diaryButton.setMaxWidth(Double.MAX_VALUE);
        diaryButton.setGraphic(createIcon("design/diary.png"));
        diaryButton.setContentDisplay(ContentDisplay.TOP);

        Button timelineButton = new Button("Timeline");
        timelineButton.getStyleClass().add("card-blue");
        timelineButton.setMaxWidth(Double.MAX_VALUE);
        timelineButton.setGraphic(createIcon("design/timeline.png"));
        timelineButton.setContentDisplay(ContentDisplay.TOP);

        // Knapper til ROUND sektionen — lyserøde kort
        Button historyButton = new Button("History");
        historyButton.getStyleClass().add("card-pink");
        historyButton.setMaxWidth(Double.MAX_VALUE);
        historyButton.setGraphic(createIcon("design/roundHistory.png"));
        historyButton.setContentDisplay(ContentDisplay.TOP);

        Button newRoundButton = new Button("New round");
        newRoundButton.getStyleClass().add("card-pink");
        newRoundButton.setMaxWidth(Double.MAX_VALUE);
        newRoundButton.setGraphic(createIcon("design/start.png"));
        newRoundButton.setContentDisplay(ContentDisplay.TOP);

        Button endRoundButton = new Button("End round");
        endRoundButton.getStyleClass().add("card-pink");
        endRoundButton.setMaxWidth(Double.MAX_VALUE);
        endRoundButton.setGraphic(createIcon("design/stop.png"));
        endRoundButton.setContentDisplay(ContentDisplay.TOP);

        // Log ud knap
        Button logoutButton = new Button("Log out");
        logoutButton.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
        logoutButton.getStyleClass().add("secondary-button");
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        // Kobl knapper til de rigtige views
        hormoneButton.setOnAction(e -> new HormoneLogView().show(stage));
        medicationButton.setOnAction(e -> new MedicationLogView().show(stage));
        appointmentButton.setOnAction(e -> new AppointmentView().show(stage));
        diaryButton.setOnAction(e -> new DiaryView().show(stage));
        timelineButton.setOnAction(e -> new TimelineView().show(stage));
        historyButton.setOnAction(e -> new RoundHistoryView().show(stage));
        newRoundButton.setOnAction(e -> new NewRoundView().show(stage));
        endRoundButton.setOnAction(e -> new EndRoundView().show(stage));

        // Log ud — vis bekræftelsesdialog inden brugeren logges ud
        logoutButton.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm");
            confirm.setHeaderText("Log out?");
            confirm.setContentText("You will need to log in again.");

            // showAndWait venter på brugerens svar inden koden fortsætter
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Ryd Session og gå tilbage til login
                    Session.setCurrentPatient(null);
                    Session.setCurrentJourneyId(0);
                    Session.setReminderShown(false); // nulstil så næste login viser popup igen
                    new StartSystemView().show(stage);
                }
            });
        });

        // LOG DATA grid — 2 kolonner side om side
        GridPane logGrid = new GridPane();
        logGrid.setHgap(10);
        logGrid.setVgap(10);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50); // hver kolonne fylder 50%
        logGrid.getColumnConstraints().addAll(col2, col2);
        logGrid.add(hormoneButton, 0, 0);
        logGrid.add(medicationButton, 1, 0);

        // PLANNING grid — 3 kolonner side om side
        GridPane planGrid = new GridPane();
        planGrid.setHgap(10);
        planGrid.setVgap(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33.33); // hver kolonne fylder 33%
        planGrid.getColumnConstraints().addAll(col3, col3, col3);
        planGrid.add(appointmentButton, 0, 0);
        planGrid.add(diaryButton, 1, 0);
        planGrid.add(timelineButton, 2, 0);

        // ROUND grid — 3 kolonner side om side
        GridPane roundGrid = new GridPane();
        roundGrid.setHgap(10);
        roundGrid.setVgap(10);
        roundGrid.getColumnConstraints().addAll(col3, col3, col3);
        roundGrid.add(historyButton, 0, 0);
        roundGrid.add(newRoundButton, 1, 0);
        roundGrid.add(endRoundButton, 2, 0);

        // VBox — lodret layout der samler alle elementer
        VBox layout = new VBox(10);
        layout.getStyleClass().add("card");
        layout.setMaxWidth(600);
        layout.setPrefWidth(600);
        layout.setPadding(new Insets(24));
        layout.getChildren().addAll(
                welcomeLabel, dateLabel, diagnosisLabel,
                statsGrid,
                separator1,
                loggingLabel, logGrid,
                planningLabel, planGrid,
                historyLabel, roundGrid,
                logoutButton
        );

        // StackPane centrerer kortet på skærmen med gradient baggrund
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);");
        root.setPadding(new Insets(40));

        // Opret og vis skærmen
        Scene scene = new Scene(root, 650, 750);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("design/styles.css").toExternalForm());
        stage.setTitle("Simpl — Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    // Hjælpemetode der opretter et ikon fra en billedfil
    // path er stien til billedet fx "design/appointments.png"
    private ImageView createIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(path)));
        icon.setFitWidth(24);  // bredde i pixels
        icon.setFitHeight(24); // højde i pixels
        icon.setPreserveRatio(true); // behold billedets proportioner
        return icon;
    }
}