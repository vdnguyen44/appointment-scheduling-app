package View_Controller;

import DAO.CountriesDAO;
import DAO.UsersDAO;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The controller class for the modify customer form
 */

public class ModifyCustomerFormController {

    /**
     * The text field for the customer's ID, cannot be edited
     */
    @FXML
    private TextField customerIDTxt;

    /**
     * The text field for the customer's name
     */

    @FXML
    private TextField customerNameTxt;

    /**
     * The text field for the customer's address
     */

    @FXML
    private TextField addressTxt;

    /**
     * The text field for the customer's postal code
     */

    @FXML
    private TextField postalCodeTxt;

    /**
     * The text field for the customer's phone number
     */

    @FXML
    private TextField phoneNumberTxt;

    /**
     * The combo box for the customer's country
     */

    @FXML
    private ComboBox<Country> countryComboBox;

    /**
     * The combo box for the customer's first level division
     */

    @FXML
    private ComboBox<FirstLevelDivision> divisionComboBox;

    /**
     * The selected customer's ID
     */
    private int customerID;

    /**
     *<p>This method retrieves the values from the text fields and combo boxes and sets them as the customer's new
     * attributes. The customers form is then displayed.</p>
     * @param event When the save button is pressed
     * @throws IOException Exception thrown if the customers form fxml cannot be located
     */

    @FXML
    void modifyCustomer(ActionEvent event) throws IOException {

        String customerName = customerNameTxt.getText();
        String address = addressTxt.getText();
        String postal = postalCodeTxt.getText();
        String phone = phoneNumberTxt.getText();
        String lastUpdatedBy = UsersDAO.getCurrentUser().getUserName();
        int divisionID = divisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();

        try {
            String updateQuery = "UPDATE customers " +
                    "SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=NOW(), Last_Updated_By=?, Division_ID=? " +
                    "WHERE Customer_ID=?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateQuery);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postal);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, lastUpdatedBy);
            preparedStatement.setInt(6, divisionID);
            preparedStatement.setInt(7, customerID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Parent CustomersFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/CustomersForm.fxml"));
        Scene customersScene = new Scene(CustomersFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customersScene);
        window.show();
    }

    /**
     * <p>This method changes the scene and displays the customers form.</p>
     * @param event When the cancel button is pressed
     * @throws IOException Exception thrown if the customers form cannot be located
     */
    @FXML
    void returnCustomerForm(ActionEvent event) throws IOException {
        Parent CustomersFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/CustomersForm.fxml"));
        Scene customersScene = new Scene(CustomersFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customersScene);
        window.show();

    }

    /**
     * <p>This method initializes the text fields and combo boxes with the customer's attributes. Then a
     * lambda expression is  used to change the first level division combo box's selection based on the item selected
     * from the country combo box. The lambda expression keeps the logic of the behavior of the combo boxes in the
     * appropriate method without having to create a separate function.</p>
     * @param customer Selected customer from the customers form
     * @throws SQLException Exception thrown if there is an error accessing the database
     */
    public void initializeCustomerData(Customer customer) throws SQLException {
        customerID = customer.getCustomerID();
        customerIDTxt.setText(String.valueOf(customerID));
        customerNameTxt.setText(customer.getCustomerName());
        addressTxt.setText(customer.getCustomerAddress());
        postalCodeTxt.setText(customer.getCustomerPostal());
        phoneNumberTxt.setText(customer.getCustomerPhone());

        int selectedDivisionID = customer.getDivisionID();
        FirstLevelDivision selectedDivision = null;
        ObservableList<FirstLevelDivision> divisionsSameCountry = FXCollections.observableArrayList();

        // prepopulate selected division
        String selectedDivisionQuery = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement selectedDivisionPS = DBConnection.getConnection().prepareStatement(selectedDivisionQuery);
        selectedDivisionPS.setInt(1, selectedDivisionID);
        ResultSet selectedDivisionRS = selectedDivisionPS.executeQuery();

        while (selectedDivisionRS.next()) {
            int divisionID = selectedDivisionRS.getInt("Division_ID");
            String divisionName = selectedDivisionRS.getString("Division");
            Timestamp dateCreated = selectedDivisionRS.getTimestamp("Create_Date");
            String createdBy = selectedDivisionRS.getString("Created_By");
            Timestamp lastUpdated = selectedDivisionRS.getTimestamp("Last_Update");
            String lastUpdatedBy = selectedDivisionRS.getString("Last_Updated_By");
            int countryID = selectedDivisionRS.getInt("COUNTRY_ID");

            selectedDivision = new FirstLevelDivision(divisionID, divisionName, dateCreated, createdBy, lastUpdated, lastUpdatedBy, countryID);            
        }
        divisionComboBox.setValue(selectedDivision);


        // prepopulate country based on selected division
        assert selectedDivision != null;
        int selectedCountryID = selectedDivision.getCountryID();
        Country selectedCountry = null;
        String selectedCountryQuery = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement selectedCountryPS = DBConnection.getConnection().prepareStatement(selectedCountryQuery);
        selectedCountryPS.setInt(1, selectedCountryID);
        ResultSet selectedCountryRS = selectedCountryPS.executeQuery();

        while (selectedCountryRS.next()) {
            int countryID = selectedCountryRS.getInt("Country_ID");
            String countryName = selectedCountryRS.getString("Country");
            Timestamp dateCreated = selectedCountryRS.getTimestamp("Create_Date");
            String createdBy = selectedCountryRS.getString("Created_By");
            Timestamp lastUpdated = selectedCountryRS.getTimestamp("Last_Update");
            String lastUpdatedBy = selectedCountryRS.getString("Last_Updated_By");

            selectedCountry = new Country(countryID, countryName, dateCreated, createdBy, lastUpdated, lastUpdatedBy);

        }
        countryComboBox.setValue(selectedCountry);
        countryComboBox.setItems(CountriesDAO.getAllCountries());


        // prepopulate division options based on selected division
        String sameDivisionsQuery = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement sameDivisionsPS = DBConnection.getConnection().prepareStatement(sameDivisionsQuery);
        sameDivisionsPS.setInt(1, selectedCountryID);
        ResultSet sameDivisionsRS = sameDivisionsPS.executeQuery();

        while (sameDivisionsRS.next()) {
            int divisionID = sameDivisionsRS.getInt("Division_ID");
            String divisionName = sameDivisionsRS.getString("Division");
            Timestamp dateCreated = sameDivisionsRS.getTimestamp("Create_Date");
            String createdBy = sameDivisionsRS.getString("Created_By");
            Timestamp lastUpdated = sameDivisionsRS.getTimestamp("Last_Update");
            String lastUpdatedBy = sameDivisionsRS.getString("Last_Updated_By");
            int countryID = sameDivisionsRS.getInt("COUNTRY_ID");

            FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, dateCreated, createdBy, lastUpdated, lastUpdatedBy, countryID);
            divisionsSameCountry.add(division);
        }
        divisionComboBox.setItems(divisionsSameCountry);

        // Changes division combo box selections based country combo box selection
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

        });
    }



}
