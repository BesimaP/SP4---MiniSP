import model.DatabaseConnection;
import model.DatabaseInitializer;

public class Main {

    public static void main(String [] args){
        // Opret forbindelse til databasen
        DatabaseConnection.getConnection();

        // Opret tabellerne hvis de ikke allerede eksisterer
        DatabaseInitializer.initialize();
    }
}
