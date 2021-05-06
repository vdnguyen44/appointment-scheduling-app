package View_Controller;

import DAO.AppointmentsDAO;
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
import utils.Alerts;
import utils.AppointmentTimes;
import utils.DBConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**
 * The controller class for the modify appointment form
 */
public class ModifyAppointmentFormController {

    /**
     * The text field representing the ID of an appointment
     */
    @FXML
    private TextField apptIDTxt;

    /**
     * The text field representing the title of an appointment
     */
    @FXML
    private TextField apptTitleTxt;

    /**
     * The text field representing the description of an appointment
     */
    @FXML
    private TextField apptDescriptionTxt;

    /**
     * The text field representing the location of an appointment
     */
    @FXML
    private TextField apptLocationTxt;

    /**
     * The text field representing the type of appointment
     */
    @FXML
    private TextField apptTypeTxt;

    /**
     * The date picker representing the start date of the appointment
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * The date picker representing the end date of the appointment
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * The combo box representing the contact associated with the appointment
     */
    @FXML
    private ComboBox<Contact> contactComboBox;

    /**
     * The combo box representing the customer associated with the appointment
     */
    @FXML
    private ComboBox<Customer> customerComboBox;

    /**
     * The combo box representing the user associated with the appointment
     */
    @FXML
    private ComboBox<User> userComboBox;

    /**
     * The combo box representing the start time of the appointment
     */
    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    /**
     * The combo box representing the end time of the appointment
     */
    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    /**
     * The ID of the selected appointment
     */
    private int apptID;

    /**
     * <p>This method collects all the modified values from the text fields, date pickers, and combo boxes. The start
     * and end date/times are then compared with the office's business hours to ensure the times fall within business hours.
     * The start and end date/times are also compared with existing appointments in the database to check if they overlap
     * with any existing appointments. If the times fall within business hours and do not conflict with other appointments,
     * an sql query is executed to insert the collected values as a record into the database. The user is then sent back
     * to the appointments form.</p>
     * @param event When the save button is pressed
     * @throws SQLException Exception thrown if there is an error accessing the database
     */
    @FXML
    void modifyAppointment(ActionEvent event) throws SQLException {
        String apptTitle = apptTitleTxt.getText();
        String apptDescription = apptDescriptionTxt.getText();
        String apptLocation = apptLocationTxt.getText();
        String apptType = apptTypeTxt.getText();
        LocalDateTime startLDT = LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getSelectionModel().getSelectedItem());
        LocalDateTime endLDT = LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getSelectionModel().getSelectedItem());
//        Timestamp startDateTime = Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getSelectionModel().getSelectedItem()));
//        Timestamp endDateTime = Timestamp.valueOf(LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getSelectionModel().getSelectedItem()));
        String lastUpdatedBy = UsersDAO.getCurrentUser().getUserName();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();

        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime zonedOriginStart = ZonedDateTime.of(startLDT, localZoneID);
        ZonedDateTime zonedTargetStart = zonedOriginStart.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime convertedStartLT = zonedTargetStart.toLocalTime();

        ZonedDateTime zonedOriginEnd = ZonedDateTime.of(endLDT, localZoneID);
        ZonedDateTime zonedTargetEnd = zonedOriginEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime convertedEndLT = zonedTargetEnd.toLocalTime();

        if (AppointmentsDAO.hasOverlapAppts(customerID, startLDT, endLDT)) {
            Alerts.showOverlapError();        }


        if (!AppointmentTimes.isWithinBusinessHrs(convertedStartLT, convertedEndLT)) {
            Alerts.showApptTimesError();
        }


        if (!AppointmentsDAO.hasOverlapAppts(customerID, startLDT, endLDT) && AppointmentTimes.isWithinBusinessHrs(convertedStartLT, convertedEndLT)) {
            Timestamp startTimeStamp = Timestamp.valueOf(startLDT);
            Timestamp endTimeStamp = Timestamp.valueOf(endLDT);

            try {
                String updateQuery = "UPDATE appointments " +
                        "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=NOW(), Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? " +
                        "WHERE Appointment_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateQuery);
                preparedStatement.setString(1, apptTitle);
                preparedStatement.setString(2, apptDescription);
                preparedStatement.setString(3, apptLocation);
                preparedStatement.setString(4, apptType);
                preparedStatement.setTimestamp(5, startTimeStamp);
                preparedStatement.setTimestamp(6, endTimeStamp);
                preparedStatement.setString(7, lastUpdatedBy);
                preparedStatement.setInt(8, customerID);
                preparedStatement.setInt(9, userID);
                preparedStatement.setInt(10, contactID);
                preparedStatement.setInt(11, apptID);
                preparedStatement.executeUpdate();

                Parent appointmentsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsForm.fxml"));
                Scene appointmentsScene = new Scene(appointmentsFormLoader);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(appointmentsScene);
                window.show();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>This method changes the scene and displays the appointments form.</p>
     * @param event when the cancel button is pressed
     * @throws IOException Exception thrown if the appointments form fxml cannot be located
     */
    @FXML
    void returnAppointmentForm(ActionEvent event) throws IOException {
        Parent appointmentsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsForm.fxml"));
        Scene appointmentsScene = new Scene(appointmentsFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(appointmentsScene);
        window.show();
    }

    /**
     * <p>This method initializes the GUI elements with the values from the selected appointment. A lambda expression
     * is used to populate the end time combo box with times after the time specified in the start time combo box.
     * The lambda expression is more efficient, removes the need to create another function and prevents the logic error
     * of an end time being earlier than the start time.</p>
     * @param appointment the selected appointment from the appointments table view
     * @throws SQLException Exception thrown if there is an error accessing the database
     */
    public void initializeAppointmentData(Appointment appointment) throws SQLException {
        apptID = appointment.getApptID();
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


        startTimeComboBox.setItems(AppointmentTimes.getAppointmentTimes());

        // set end times based on initial start time
        LocalTime startTime = startTimeComboBox.getValue();
        FilteredList<LocalTime> endTimes = new FilteredList<>(AppointmentTimes.getAppointmentTimes(), time -> time.isAfter(startTime));
        endTimeComboBox.setItems(endTimes);

        // set end times based on changed start time
        startTimeComboBox.setOnAction((event) -> {
            LocalTime startTimeChange = startTimeComboBox.getValue();
            FilteredList<LocalTime> endTimesChange = new FilteredList<>(AppointmentTimes.getAppointmentTimes(), time -> time.isAfter(startTimeChange));
            endTimeComboBox.setItems(endTimesChange);
        });

    }

}
