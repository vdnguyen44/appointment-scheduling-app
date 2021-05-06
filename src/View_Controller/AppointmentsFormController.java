package View_Controller;

import DAO.AppointmentsDAO;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Alerts;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the appointments form
 */

public class AppointmentsFormController implements Initializable {

    /**
     * The table view that displays all appointments in the database
     */
    @FXML
    private TableView<Appointment> appointmentsTbl;

    /**
     * The column that represents an appointment's ID
     */
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    /**
     * The column that represents an appointment's title
     */
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    /**
     * The column that represents an appointment's description
     */
    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;

    /**
     * The column that represents an appointment's location
     */
    @FXML
    private TableColumn<Appointment, String> apptLocationCol;

    /**
     * The column that represents an appointment's type
     */
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    /**
     * The column that represents an appointment's start time
     */
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartCol;

    /**
     * The column that represents an appointment's end time
     */
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndCol;

    /**
     * The column that represents the contact associated with an appointment
     */
    @FXML
    private TableColumn<Appointment, String> apptContactCol;

    /**
     * The column that represents the customer associated with an appointment
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    /**
     * The combo box that selects the view of the appointments in the table view
     */
    @FXML
    private ComboBox<String> apptViewComboBox;

    /**
     *<p>This method handles the event when the user wishes to delete an appointment. If no appointment is selected,
     * an error alert is shown. If an appointment is selected, the user is prompted to confirm whether they would like to
     * delete the appointment. An sql query is then executed to delete the appointment from the database. The appointments
     * table view is refreshed to reflect the change.</p>
     * @param event When the delete button is pressed
     * @throws SQLException Exception thrown if there is an error accessing the database
     */
    @FXML
    void deleteAppointment(ActionEvent event) throws SQLException {

        if (appointmentsTbl.getSelectionModel().isEmpty()) {
            Alerts.showNoApptSelectedAlert();
        }
        else {
            Appointment selectedAppointment = appointmentsTbl.getSelectionModel().getSelectedItem();
            int selectedApptID = selectedAppointment.getApptID();
            String selectedApptType = selectedAppointment.getApptType();
            Optional<ButtonType> result = Alerts.showApptDeleteConfirmAlert();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String deleteQuery = "DELETE from appointments WHERE Appointment_ID=?";
                PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteQuery);
                preparedStatement.setInt(1, selectedApptID);
                preparedStatement.executeUpdate();

                appointmentsTbl.setItems(AppointmentsDAO.getAllAppointments());

                Alerts.showApptDeleteConfirmation(selectedApptID, selectedApptType);
            }
        }
        appointmentsTbl.getSelectionModel().clearSelection();
    }

    /**
     * <p>This method changes the scene and displays the add appointment form.</p>
     * @param event When the add appointment button is pressed
     * @throws IOException Exception thrown if the add appointment form cannot be located
     */
    @FXML
    void displayAddAppointmentForm(ActionEvent event) throws IOException {
        Parent addAppointmentFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointmentForm.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addAppointmentScene);
        window.show();
    }

    /**
     * <p>This method changes the scene and displays the modify appointment form. The selected customer's information
     *  is sent to the modify appointment form's controller. An error alert is shown if no appointment is selected.</p>
     * @param event When the modify appointment button is pressed
     * @throws IOException Exception thrown if the modify appointment form fxml cannot be located
     * @throws SQLException Exception thrown if there is an error accessing the database
     */
    @FXML
    void displayModifyAppointmentForm(ActionEvent event) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyAppointmentForm.fxml"));
            Parent appointmentsTableParent = loader.load();
            Scene modifyAppointmentScene = new Scene(appointmentsTableParent);

            ModifyAppointmentFormController ModAppointmentController = loader.getController();
            ModAppointmentController.initializeAppointmentData(appointmentsTbl.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(modifyAppointmentScene);
            window.show();
        }
        catch (NullPointerException e) {
            Alerts.showNoApptSelectedAlert();
        }
    }

    /**
     * <p>This method changes the scene and displays the options form.</p>
     * @param event When the cancel button is pressed
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
     * <p>This override method initializes the appointment view combo box with available options and default option. A
     * lambda expression is then used to listen to changes in the combo box to display the view in the appointments table
     * view according to the selection. The lambda expression was used to prevent having to save the function elsewhere.
     * The start and end columns were also formatted to be read in a more friendly format.</p>
     * @param url Location
     * @param rb Resource
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        apptViewComboBox.getItems().add("Week");
        apptViewComboBox.getItems().add("Month");
        apptViewComboBox.getItems().add("All");
        apptViewComboBox.setValue("All");


        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now.get(WeekFields.of(Locale.getDefault()).weekOfYear()));

        ObservableList<Appointment> allAppointments = AppointmentsDAO.getAllAppointments();

        apptViewComboBox.setOnAction(actionEvent -> {

            switch (apptViewComboBox.getValue()) {
                case "Week":
                    FilteredList<Appointment> apptWeek = new FilteredList<>(allAppointments,
                            appt -> appt.getApptStart().isEqual(now) || appt.getApptStart().isAfter(now) && now.get(WeekFields.of(Locale.getDefault()).weekOfYear()) == appt.getApptStart().get(WeekFields.of(Locale.getDefault()).weekOfYear()));

                    appointmentsTbl.setItems(apptWeek);
                    break;

                case "Month":
                    FilteredList<Appointment> apptMonth = new FilteredList<>(allAppointments,
                            appt -> appt.getApptStart().isEqual(now) || appt.getApptStart().isAfter(now) && now.getMonth() == appt.getApptStart().getMonth());
                    appointmentsTbl.setItems(apptMonth);
                    break;

                case "All":
                    appointmentsTbl.setItems(allAppointments);
                    break;
            }
        });

        appointmentsTbl.setItems(AppointmentsDAO.getAllAppointments());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        apptStartCol.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>() {
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                }
                else {
                    setText(formatter.format(item));
                }
            }

        });

        apptEndCol.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>() {
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                }
                else {
                    setText(formatter.format(item));
                }
            }

        });

    }

}
