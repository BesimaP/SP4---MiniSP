import javafx.application.Application;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.DatabaseInitializer;
import view.ProfileView;
import view.StartSystemView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Opret forbindelse til databasen
        DatabaseConnection.getConnection();

        // Opret tabellerne hvis de ikke allerede eksisterer
        DatabaseInitializer.initialize();

        //Test - vis ProfileView
        ProfileView view2 = new ProfileView();
        view2.show(stage);

    }

    public static void main(String[] args) {
        // Start JavaFX
        launch(args);
    }
}