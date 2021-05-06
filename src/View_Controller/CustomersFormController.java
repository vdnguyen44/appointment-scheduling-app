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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import utils.Alerts;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller class for the customers form
 */

public class CustomersFormController implements Initializable {

    /**
     * The table view that displays all customers in the database
     */
    @FXML
    private TableView<Customer> customersTbl;

    /**
     * The column that represents a customer's ID
     */
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    /**
     * The column that represents a customer's name
     */
    @FXML
    private TableColumn<Customer, String> customerNameCol;

    /**
     * The column that represents a customer's address
     */
    @FXML
    private TableColumn<Customer, String> addressCol;

    /**
     * The column that represents a customer's postal code
     */
    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    /**
     * The column that represents the first level division of a customer
     */
    @FXML
    private TableColumn<Customer, String> divisionCol;

    /**
     * The column that represents the country of a customer
     */
    @FXML
    private TableColumn<Customer, String> countryCol;

    /**
     * The column that represents a customer's phone number
     */
    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button modifyCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    /**
     * <p>This method shows an error alert if a customer is not selected in the tableview. If a customer is selected,
     * the user is prompted to confirm whether they would like to delete the customer. An sql query is executed to
     * delete the customer from the database. The tableview is then refreshed and the user receives confirmation that
     * the customer has been deleted.</p>
     * @param event When the delete button is pressed
     * @throws SQLException Exception thrown if there is an error accessing the database
     */
    @FXML
    void deleteCustomer(ActionEvent event) throws SQLException {

        if (customersTbl.getSelectionModel().isEmpty()) {
            Alerts.showNoCustomerSelectedAlert();
        }
        else {
            Customer selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
            int selectedCustomerID = selectedCustomer.getCustomerID();
            Optional<ButtonType> result = Alerts.showCustomerDeleteConfirmAlert();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                String deleteCustomerAppt = "DELETE from appointments WHERE Customer_ID=?";
                PreparedStatement deleteCustomerApptPS = DBConnection.getConnection().prepareStatement(deleteCustomerAppt);
                deleteCustomerApptPS.setInt(1, selectedCustomerID);
                deleteCustomerApptPS.executeUpdate();

                String deleteQuery = "DELETE from customers WHERE Customer_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteQuery);
                preparedStatement.setInt(1, selectedCustomerID);
                preparedStatement.executeUpdate();

                customersTbl.setItems(CustomersDAO.getAllCustomers());

                Alerts.showCustomerDeleteConfirmation();

            }
        }
        customersTbl.getSelectionModel().clearSelection();
    }

    /**
     * <p>This method changes the scene and displays the add customer form.</p>
     * @param event When the add customer button is pressed
     * @throws IOException Exception thrown if the add customer form fxml cannot be located
     */
    @FXML
    void displayAddCustomerForm(ActionEvent event) throws IOException {
        Parent addCustomerFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AddCustomerForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomerFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    /**
     * <p>This method changes the scene and displays the modify customers form. The selected customer's information
     * is sent to the modify customer form's controller. An error alert is shown if no customer is selected.</p>
     * @param event when the modify customer button is pressed
     * @throws IOException Exception thrown if the modify customer form fxml cannot be located
     * @throws SQLException Exception thrown if the customer is not found in the database
     */
    @FXML
    void displayModifyCustomerForm(ActionEvent event) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyCustomerForm.fxml"));
            Parent customersTableParent = loader.load();
            Scene modifyCustomerScene = new Scene(customersTableParent);

            ModifyCustomerFormController ModCustomerController = loader.getController();
            ModCustomerController.initializeCustomerData(customersTbl.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(modifyCustomerScene);
            window.show();
        }
        catch (NullPointerException e)
        {
            Alerts.showNoCustomerSelectedAlert();
        }
    }

    /**
     * <p>This method changes the scene and displays the options form.</p>
     * @param event when the cancel button is pressed
     * @throws IOException Exception thrown if the options form fxml cannot be located
     */
    @FXML
    void returnOptionsForm(ActionEvent event) throws IOException {
        Parent optionsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/OptionsForm.fxml"));
        Scene optionsScene = new Scene(optionsFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(optionsScene);
        window.show();
    }

    /**
     * <p>This method initializes the customers table with existing customers in the database and reformats the
     * table view's cells.</p>
     * @param url Location
     * @param rb Resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // init table with objects
        customersTbl.setItems(CustomersDAO.getAllCustomers());

        // init columns with object attributes
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostal"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));


        // set editable columns
        customerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerNameCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCustomerName(e.getNewValue());
            int customerID = e.getRowValue().getCustomerID();
            try{
                String updateQuery = "UPDATE customers SET Customer_Name=? WHERE Customer_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateQuery);
                preparedStatement.setString(1, e.getNewValue());
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            } catch (SQLException error) {
                error.printStackTrace();
            }
        });

        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCustomerName(e.getNewValue());
            int customerID = e.getRowValue().getCustomerID();
            try{
                String updateQuery = "UPDATE customers SET Address=? WHERE Customer_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateQuery);
                preparedStatement.setString(1, e.getNewValue());
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            } catch (SQLException error) {
                error.printStackTrace();
            }
        });

        postalCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        postalCodeCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCustomerName(e.getNewValue());
            int customerID = e.getRowValue().getCustomerID();
            try{
                String updateQuery = "UPDATE customers SET Postal_Code=? WHERE Customer_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateQuery);
                preparedStatement.setString(1, e.getNewValue());
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            } catch (SQLException error) {
                error.printStackTrace();
            }
        });

        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCustomerName(e.getNewValue());
            int customerID = e.getRowValue().getCustomerID();
            try{
                String updateQuery = "UPDATE customers SET Phone=? WHERE Customer_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateQuery);
                preparedStatement.setString(1, e.getNewValue());
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            } catch (SQLException error) {
                error.printStackTrace();
            }
        });

    }

}
