package View_Controller;

import DAO.CustomersDAO;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class CustomersFormController implements Initializable {

    @FXML
    private TableView<Customer> customersTbl;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, Timestamp> dateCreatedCol;

    @FXML
    private TableColumn<Customer, String> createdByCol;

    @FXML
    private TableColumn<Customer, Timestamp> lastUpdatedCol;

    @FXML
    private TableColumn<Customer, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Customer, Integer> divisionIDCol;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button modifyCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    void deleteCustomer(ActionEvent event) {

    }

    @FXML
    void displayAddCustomerForm(ActionEvent event) throws IOException {
        Parent addCustomerFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AddCustomerForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomerFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    @FXML
    void displayModifyCustomerForm(ActionEvent event) {

    }

    @FXML
    void returnOptionsForm(ActionEvent event) throws IOException {
        Parent optionsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/OptionsForm.fxml"));
        Scene optionsScene = new Scene(optionsFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(optionsScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customersTbl.setItems(CustomersDAO.getAllCustomers());

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostal"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

}
