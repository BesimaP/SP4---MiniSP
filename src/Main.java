import controller.ManageProfileController;
import controller.StartSystemController;
import model.DatabaseConnection;
import model.DatabaseInitializer;

import java.time.LocalDate;

public class Main {

    public static void main(String [] args){
        // Opret forbindelse til databasen
        DatabaseConnection.getConnection();

        // Opret tabellerne hvis de ikke allerede eksisterer
        DatabaseInitializer.initialize();


        ManageProfileController manageProfileController = new ManageProfileController();
        manageProfileController.createUser("Tess", LocalDate.of(2001, 10,25), "pcos", "Tess123", "Kat123");
    }
}
