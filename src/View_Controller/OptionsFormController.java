package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionsFormController {

    @FXML
    private Button customerBtn;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    void displayAppointmentsForm(ActionEvent event) {

    }

    @FXML
    void displayCustomersForm(ActionEvent event) throws IOException {
        Parent customersFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/CustomersForm.fxml"));
        Scene customersScene = new Scene(customersFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customersScene);
        window.show();
    }

    @FXML
    void displayReportsForm(ActionEvent event) {

    }

}

