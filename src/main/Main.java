package main;

import utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Vincent Nguyen
 */

public class Main extends Application {

    /**
     * <p>The main class loads the fxml markup of the main form</p>
     * @param primaryStage The main stage where scenes are set.
     * @throws Exception Exception thrown if login form fxml cannot be located.
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/LoginForm.fxml"));
        loader.setResources(ResourceBundle.getBundle("utils/Nat", Locale.getDefault()));
        Parent root = loader.load();
        primaryStage.setTitle("Appointment Scheduling App");
        primaryStage.setScene(new Scene(root, 600, 297));
        primaryStage.show();
    }

    /**
     * <p>Initializes the javafx application, database connection, and closes the connection when the application is closed.</p>
     * @param args Array of string objects
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
