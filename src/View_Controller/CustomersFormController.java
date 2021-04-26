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
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
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
    void deleteCustomer(ActionEvent event) throws SQLException {
        Customer selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        int selectedCustomerID = selectedCustomer.getCustomerID();

        if (customersTbl.getSelectionModel().isEmpty()) {
            displayNoneSelectedAlert();
        }
        else {
            Optional<ButtonType> result = displayDeleteConfirmAlert();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String deleteQuery = "DELETE from customers WHERE Customer_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteQuery);
                preparedStatement.setInt(1, selectedCustomerID);
                preparedStatement.executeUpdate();

                customersTbl.setItems(CustomersDAO.getAllCustomers());
            }
        }



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
            displayNoneSelectedAlert();
        }
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
        // init table with objects
        customersTbl.setItems(CustomersDAO.getAllCustomers());

        // init columns with object attributes
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

    void displayNoneSelectedAlert() {
        Alert noneSelectedAlert = new Alert(Alert.AlertType.ERROR);
        noneSelectedAlert.setTitle("");
        noneSelectedAlert.setHeaderText("Customer Selection Error");
        noneSelectedAlert.setContentText("No customer is selected.");
        noneSelectedAlert.show();
    }

    Optional<ButtonType> displayDeleteConfirmAlert() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("");
        deleteAlert.setHeaderText("Deletion Confirmation");
        deleteAlert.setContentText("Are you sure you want to delete this customer?");
        return deleteAlert.showAndWait();
    }

}
