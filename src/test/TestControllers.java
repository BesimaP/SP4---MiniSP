package test;

import controller.*;
import model.DatabaseConnection;
import model.DatabaseInitializer;
import java.time.LocalDate;

public class TestControllers {

    public static void main(String[] args) {

        // Opret forbindelse og tabeller
        DatabaseConnection.getConnection();
        DatabaseInitializer.initialize();

        System.out.println("=== TEST START ===");

        // Test 1 — Opret patient
        System.out.println("\n--- Test 1: Opret patient ---");
        ManageProfileController manageProfile = new ManageProfileController();
        manageProfile.handleCreatePatient("Anna Hansen", LocalDate.of(1990, 1, 1), "PCOS", "anna123", "password123");

        // Test 2 — Login
        System.out.println("\n--- Test 2: Login ---");
        StartSystemController startSystem = new StartSystemController();
        startSystem.handleLogin("anna123", "password123");

        // Test 3 — Opret forløb
        System.out.println("\n--- Test 3: Opret forløb ---");
        JourneyTypeController journeyType = new JourneyTypeController();
        journeyType.handleSelectJourney(1, "Fertilitet");

        // Test 4 — Start ny runde
        System.out.println("\n--- Test 4: Start ny runde ---");
        NewRoundController newRound = new NewRoundController();
        newRound.handleStartRound(1, 1);

        // Test 5 — Tilføj aftale
        System.out.println("\n--- Test 5: Tilføj aftale ---");
        AppointmentController appointment = new AppointmentController();
        appointment.handleSave(1, "2026-05-20", "SCANNING", "Vitanova");

        // Test 6 — Log hormonværdi
        System.out.println("\n--- Test 6: Log hormonværdi ---");
        HormoneLogController hormoneLog = new HormoneLogController();
        hormoneLog.handleSave(1, LocalDate.now(), "Oestradiol", 150.0, "pmol/L");

        // Test 7 — Log medicin
        System.out.println("\n--- Test 7: Log medicin ---");
        MedicationLogController medicationLog = new MedicationLogController();
        medicationLog.handleSave(1, LocalDate.now(), "Gonal-F", "150 IU", false);

        // Test 8 — Skriv dagbogsnote
        System.out.println("\n--- Test 8: Dagbogsnote ---");
        DiaryController diary = new DiaryController();
        diary.handleSave(1, LocalDate.now(), "Min første note", "Det går godt!");

        // Test 9 — Se rundehistorik
        System.out.println("\n--- Test 9: Rundehistorik ---");
        RoundHistoryController roundHistory = new RoundHistoryController();
        roundHistory.initialize(1);

        // Test 10 — Afslut runde
        System.out.println("\n--- Test 10: Afslut runde ---");
        EndRoundController endRound = new EndRoundController();
        endRound.handleEndRound(1, "POSITIVE");

        System.out.println("\n=== TEST SLUT ===");
    }
}
