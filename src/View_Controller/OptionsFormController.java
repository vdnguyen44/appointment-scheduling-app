package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The controller class for the options form
 */


public class OptionsFormController {


    /**
     * <p>This method changes the scene and displays the appointments form.</p>
     * @param event When the appointments button is pressed
     * @throws IOException Exception thrown if the Appointments form fxml cannot be located
     */

    @FXML
    void displayAppointmentsForm(ActionEvent event) throws IOException {
        Parent AppointmentsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsForm.fxml"));
        Scene AppointmentsScene = new Scene(AppointmentsFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(AppointmentsScene);
        window.show();
    }

    /**
     * <p>This method changes the scene and displays the customers form.</p>
     * @param event When the customers button is pressed
     * @throws IOException Exception thrown if the Customers form fxml cannot be located
     */
    @FXML
    void displayCustomersForm(ActionEvent event) throws IOException {
        Parent customersFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/CustomersForm.fxml"));
        Scene customersScene = new Scene(customersFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customersScene);
        window.show();
    }

    /**
     * <p>This method changes the scene and displays the reports form.</p>
     * @param event When the reports button is pressed
     * @throws IOException Exception thrown if the Reports form fxml cannot be located
     */
    @FXML
    void displayReportsForm(ActionEvent event) throws IOException {
        Parent reportsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/ReportsForm.fxml"));
        Scene reportsScene = new Scene(reportsFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(reportsScene);
        window.show();
    }



}

