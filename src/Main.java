import javafx.application.Application;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.DatabaseInitializer;
import view.StartSystemView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Opret forbindelse til databasen
        DatabaseConnection.getConnection();

        // Opret tabellerne hvis de ikke allerede eksisterer
        DatabaseInitializer.initialize();

        // Vis login-skærmen
        StartSystemView view = new StartSystemView();
        view.show(stage);
    }

    public static void main(String[] args) {
        // Start JavaFX
        launch(args);
    }
}