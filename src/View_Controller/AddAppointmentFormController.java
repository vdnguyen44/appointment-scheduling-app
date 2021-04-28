package View_Controller;

import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AddAppointmentFormController implements Initializable {

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
    void addAppointment(ActionEvent event) throws IOException {

        String apptTitle = apptTitleTxt.getText();
        String apptDescription = apptDescriptionTxt.getText();
        String apptLocation = apptLocationTxt.getText();
        String apptType = apptTypeTxt.getText();
        Timestamp startDateTime = Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getSelectionModel().getSelectedItem()));
        Timestamp endDateTime = Timestamp.valueOf(LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getSelectionModel().getSelectedItem()));
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();
        String createdBy = UsersDAO.getCurrentUser().getUserName();


        try {
            String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(insertStatement);
            preparedStatement.setString(1,apptTitle);
            preparedStatement.setString(2, apptDescription);
            preparedStatement.setString(3, apptLocation);
            preparedStatement.setString(4, apptType);
            preparedStatement.setTimestamp(5, startDateTime);
            preparedStatement.setTimestamp(6, endDateTime);
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

    @FXML
    void returnAppointmentForm(ActionEvent event) throws IOException {
        Parent appointmentsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsForm.fxml"));
        Scene appointmentsScene = new Scene(appointmentsFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(appointmentsScene);
        window.show();
    }

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

        FilteredList<LocalTime> startTimes = new FilteredList<>(AppointmentTimes.getAppointmentTimes(), time -> time.isBefore(LocalTime.of(22, 0)));
        startTimeComboBox.setItems(startTimes);


        startTimeComboBox.setOnAction((event) -> {
            LocalTime startTime = startTimeComboBox.getValue();
            FilteredList<LocalTime> endTimes = new FilteredList<>(AppointmentTimes.getAppointmentTimes(), time -> time.isAfter(startTime));

            endTimeComboBox.setItems(endTimes);
        });


    }

}

