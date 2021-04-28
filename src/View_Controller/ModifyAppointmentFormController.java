package View_Controller;

import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.AppointmentTimes;
import utils.DBConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ModifyAppointmentFormController {

    @FXML
    private TextField apptIDTxt;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptDescriptionTxt;

    @FXML
    private TextField apptLocationTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void modifyAppointment(ActionEvent event) {

    }

    @FXML
    void returnAppointmentForm(ActionEvent event) throws IOException {

        Parent appointmentsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsForm.fxml"));
        Scene appointmentsScene = new Scene(appointmentsFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(appointmentsScene);
        window.show();

    }

    public void initializeAppointmentData(Appointment appointment) throws SQLException {
        // set text fields and date picker/combo boxes
        apptIDTxt.setText(String.valueOf(appointment.getApptID()));
        apptTitleTxt.setText(appointment.getApptTitle());
        apptDescriptionTxt.setText(appointment.getApptDescription());
        apptLocationTxt.setText(appointment.getApptLocation());
        apptTypeTxt.setText(appointment.getApptType());
        startDatePicker.setValue(appointment.getApptStart().toLocalDate());
        startTimeComboBox.setValue(appointment.getApptStart().toLocalTime());
        endDatePicker.setValue(appointment.getApptEnd().toLocalDate());
        endTimeComboBox.setValue(appointment.getApptEnd().toLocalTime());


        // set contact combo box
        contactComboBox.setItems(ContactsDAO.getAllContacts());
        int selectedContactID = appointment.getContactID();


        String selectedContactQuery = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement selectedContactPS = DBConnection.getConnection().prepareStatement(selectedContactQuery);
        selectedContactPS.setInt(1, selectedContactID);
        ResultSet selectedContactRS = selectedContactPS.executeQuery();
        selectedContactRS.next();
        int contactID = selectedContactRS.getInt("Contact_ID");
        String contactName = selectedContactRS.getString("Contact_Name");
        String contactEmail = selectedContactRS.getString("Email");
        Contact selectedContact = new Contact(contactID, contactName, contactEmail);
        contactComboBox.setValue(selectedContact);

        // set customer combo box
        customerComboBox.setItems(CustomersDAO.getAllCustomers());
        int selectedCustomerID = appointment.getCustomerID();


        String selectedCustomerQuery = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Create_Date, customers.Created_By,\n" +
                "customers.Last_Update, customers.Last_Updated_By, countries.Country_ID, countries.Country, first_level_divisions.Division_ID, first_level_divisions.Division FROM countries \n" +
                "JOIN first_level_divisions ON countries.Country_ID=first_level_divisions.Country_ID \n" +
                "JOIN customers ON first_level_divisions.Division_ID=customers.Division_ID \n" +
                "WHERE customers.Customer_ID=?;";
        PreparedStatement selectedCustomerPS = DBConnection.getConnection().prepareStatement(selectedCustomerQuery);
        selectedCustomerPS.setInt(1, selectedCustomerID);
        ResultSet selectedCustomerRS = selectedCustomerPS.executeQuery();
        selectedCustomerRS.next();
        int customerID = selectedCustomerRS.getInt("Customer_ID");
        String customerName = selectedCustomerRS.getString("Customer_Name");
        String customerAddress = selectedCustomerRS.getString("Address");
        String customerPostal = selectedCustomerRS.getString("Postal_Code");
        String customerPhone = selectedCustomerRS.getString("Phone");
        LocalDateTime customerDateCreated = selectedCustomerRS.getTimestamp("Create_Date").toLocalDateTime();
        String customerCreatedBy = selectedCustomerRS.getString("Created_By");
        LocalDateTime customerLastUpdated = selectedCustomerRS.getTimestamp("Last_Update").toLocalDateTime();
        String customerLastUpdatedBy = selectedCustomerRS.getString("Last_Updated_By");
        int countryID = selectedCustomerRS.getInt("Country_ID");
        String countryName = selectedCustomerRS.getString("Country");
        int divisionID = selectedCustomerRS.getInt("Division_ID");
        String divisionName = selectedCustomerRS.getString("Division");
        Customer selectedCustomer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, customerDateCreated, customerCreatedBy, customerLastUpdated, customerLastUpdatedBy, countryID, countryName, divisionID, divisionName);
        customerComboBox.setValue(selectedCustomer);

        // set user combo box
        userComboBox.setItems(UsersDAO.getAllUsers());
        int selectedUserID = appointment.getUserID();

        String selectedUserQuery = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement selectedUserPS = DBConnection.getConnection().prepareStatement(selectedUserQuery);
        selectedUserPS.setInt(1, selectedUserID);
        ResultSet selectedUserRS = selectedUserPS.executeQuery();
        selectedUserRS.next();
        int userID = selectedUserRS.getInt("User_ID");
        String userName = selectedUserRS.getString("User_Name");
        String password = selectedUserRS.getString("Password");
        LocalDateTime userDateCreated = selectedUserRS.getTimestamp("Create_Date").toLocalDateTime();
        String userCreatedBy = selectedUserRS.getString("Created_By");
        LocalDateTime userLastUpdated = selectedUserRS.getTimestamp("Last_Update").toLocalDateTime();
        String userLastUpdatedBy = selectedUserRS.getString("Last_Updated_By");

        User selectedUser = new User(userID, userName, password, userDateCreated, userCreatedBy, userLastUpdated, userLastUpdatedBy);
        userComboBox.setValue(selectedUser);


        FilteredList<LocalTime> startTimes = new FilteredList<>(AppointmentTimes.getAppointmentTimes(), time -> time.isBefore(LocalTime.of(22, 0)));
        startTimeComboBox.setItems(startTimes);


        startTimeComboBox.setOnAction((event) -> {
            LocalTime startTime = startTimeComboBox.getValue();
            FilteredList<LocalTime> endTimes = new FilteredList<>(AppointmentTimes.getAppointmentTimes(), time -> time.isAfter(startTime));

            endTimeComboBox.setItems(endTimes);
        });




    }

}
