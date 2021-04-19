package main;

import utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/LoginForm.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/LoginForm.fxml"));
        loader.setResources(ResourceBundle.getBundle("utils/Nat", Locale.getDefault()));
        Parent root = loader.load();
        primaryStage.setTitle("Appointment Scheduling App");
        primaryStage.setScene(new Scene(root, 600, 297));
        primaryStage.show();
    }


    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
