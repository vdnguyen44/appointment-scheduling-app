package View_Controller;

import DAO.CountriesDAO;
import DAO.UsersDAO;
import Model.Country;
import Model.FirstLevelDivision;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private TextField customerIDTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField postalCodeTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivision> divisionComboBox;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void addCustomer(ActionEvent event) throws SQLException, IOException {
        String customerName = customerNameTxt.getText();
        String address = addressTxt.getText();
        String postal = postalCodeTxt.getText();
        String phone = phoneNumberTxt.getText();
        // Timestamp dateCreated = new java.sql.Timestamp(date.getTime());
        // java.util.Date date = new java.util.Date();
//        LocalDate currentDate = LocalDate.now();
//        LocalTime currentTime = LocalTime.now();
//        LocalDateTime currentDateTime = LocalDateTime.now();

        String createdBy = UsersDAO.getCurrentUser().getUserName();
        String lastUpdatedBy = UsersDAO.getCurrentUser().getUserName();
        int divisionID = divisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();

        try {
            String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES(?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(insertStatement);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postal);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, createdBy);
            preparedStatement.setString(6, lastUpdatedBy);
            preparedStatement.setInt(7, divisionID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        Parent CustomersFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/CustomersForm.fxml"));
        Scene customersScene = new Scene(CustomersFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customersScene);
        window.show();
    }

    @FXML
    void countrySelected(ActionEvent event) {
//        int countryID = countryComboBox.getSelectionModel().getSelectedItem().getCountryID();
//        if (divisionComboBox.getSelectionModel().isEmpty()) {
//            System.out.println("no selection");
//            divisionComboBox.setPromptText("Select a division");
//        }

    }

    @FXML
    void divisionSelected(ActionEvent event) {

    }

    @FXML
    void returnCustomerForm(ActionEvent event) throws IOException {
        Parent CustomersFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/CustomersForm.fxml"));
        Scene customersScene = new Scene(CustomersFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customersScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryComboBox.setPromptText("Select a country");
        countryComboBox.setVisibleRowCount(5);
        countryComboBox.setItems(CountriesDAO.getAllCountries());


        divisionComboBox.setPromptText("Select a division");
        divisionComboBox.setVisibleRowCount(10);


        countryComboBox.setOnAction((event) -> {

            int countryIDSelected = countryComboBox.getSelectionModel().getSelectedItem().getCountryID();
            ObservableList<FirstLevelDivision> firstLevelDivisionsList = FXCollections.observableArrayList();

            try {
                String sqlStatement = "SELECT * from first_level_divisions where country_id = ?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
                preparedStatement.setInt(1, countryIDSelected);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int divisionID = resultSet.getInt("Division_ID");
                    String divisionName = resultSet.getString("Division");
                    Timestamp dateCreated = resultSet.getTimestamp("Create_Date");
                    String createdBy = resultSet.getString("Created_By");
                    Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
                    String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                    int countryID = resultSet.getInt("COUNTRY_ID");

                    FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, dateCreated, createdBy, lastUpdated, lastUpdatedBy, countryID);
                    firstLevelDivisionsList.add(division);
                }

                divisionComboBox.setItems(firstLevelDivisionsList);
            } catch (SQLException e) {
                e.printStackTrace();
            }

//            if (divisionComboBox.getSelectionModel().isEmpty()) {
//                System.out.println("no selection");
//                divisionComboBox.setPromptText("Select a division");
//            }

        });

    }

}
