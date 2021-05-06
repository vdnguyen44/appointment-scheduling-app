package View_Controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import Model.*;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Alerts;
import utils.AppointmentTimes;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;


/**
 * The controller class for the add appointment form
 */
public class AddAppointmentFormController implements Initializable {

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
     * The combo box representing the contact of the appointment
     */
    @FXML
    private ComboBox<Contact> contactComboBox;

    /**
     * The combo box representing the customer of the appointment
     */
    @FXML
    private ComboBox<Customer> customerComboBox;

    /**
     * The combo box representing the user of the appointment
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
     * <p>This method collects all the values from the text fields, date pickers, and combo boxes. The start and end
     * date/times are then compared with the office's business hours to ensure the times fall within business hours. The
     * start and end date/times are also compared with existing appointments in the database to check if they overlap
     * with any existing appointments. If the times fall within business hours and do not conflict with other appointments,
     * an sql query is executed to insert the collected values as a record into the database. The user is then sent back
     * to the appointments form.</p>
     * @param event when the save button is pressed
     * @throws IOException Exception thrown if the appointments form fxml cannot be located
     * @throws SQLException Exception thrown if there is an error accessing the database
     */
    @FXML
    void addAppointment(ActionEvent event) throws IOException, SQLException {

        String apptTitle = apptTitleTxt.getText();
        String apptDescription = apptDescriptionTxt.getText();
        String apptLocation = apptLocationTxt.getText();
        String apptType = apptTypeTxt.getText();
        LocalDateTime startLDT = LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getSelectionModel().getSelectedItem());
        LocalDateTime endLDT = LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getSelectionModel().getSelectedItem());

        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();
        String createdBy = UsersDAO.getCurrentUser().getUserName();

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

        if (!AppointmentsDAO.hasOverlapAppts(customerID, startLDT, endLDT) && AppointmentTimes.isWithinBusinessHrs(convertedStartLT, convertedEndLT)){
            Timestamp startTimeStamp = Timestamp.valueOf(startLDT);
            Timestamp endTimeStamp = Timestamp.valueOf(endLDT);

            try {
                String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                        "VALUES(?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(insertStatement);
                preparedStatement.setString(1, apptTitle);
                preparedStatement.setString(2, apptDescription);
                preparedStatement.setString(3, apptLocation);
                preparedStatement.setString(4, apptType);
                preparedStatement.setTimestamp(5, startTimeStamp);
                preparedStatement.setTimestamp(6, endTimeStamp);
                preparedStatement.setString(7, createdBy);
                preparedStatement.setString(8, createdBy);
                preparedStatement.setInt(9, customerID);
                preparedStatement.setInt(10, userID);
                preparedStatement.setInt(11, contactID);
                preparedStatement.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Parent AppointmentsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsForm.fxml"));
            Scene appointmentsScene = new Scene(AppointmentsFormLoader);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(appointmentsScene);
            window.show();
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
     * <p>This method initializes the combo boxes with text and items. A lambda expression is used to populate the
     * end time combo box with times after the time specified in the start time combo box. The lambda expression is
     * more efficient, removes the need to create another function and prevents the logic error of an end time being
     * earlier than the start time.</p>
     * @param url Location
     * @param rb Resource
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        contactComboBox.setItems(ContactsDAO.getAllContacts());
        contactComboBox.setPromptText("Select a contact");
        customerComboBox.setItems(CustomersDAO.getAllCustomers());
        customerComboBox.setPromptText("Select a customer");
        userComboBox.setItems(UsersDAO.getAllUsers());
        userComboBox.setPromptText("Select a user");

        startDatePicker.setPromptText("Select a date");
        endDatePicker.setPromptText("Select a date");

        startTimeComboBox.setPromptText("Select a time");
        endTimeComboBox.setPromptText("Select a time");

        startTimeComboBox.setItems(AppointmentTimes.getAppointmentTimes());

        startTimeComboBox.setOnAction((event) -> {
            LocalTime startTime = startTimeComboBox.getValue();
            FilteredList<LocalTime> endTimes = new FilteredList<>(AppointmentTimes.getAppointmentTimes(), time -> time.isAfter(startTime));

            endTimeComboBox.setItems(endTimes);
        });

    }

}

