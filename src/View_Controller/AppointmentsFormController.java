package View_Controller;

import DAO.AppointmentsDAO;
import Model.Appointment;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentsFormController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentsTbl;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> apptLocationCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndCol;

    @FXML
    private TableColumn<Appointment, String> apptContactCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private ChoiceBox<?> timeSelectionChoiceBox;

    @FXML
    void deleteAppointment(ActionEvent event) {

    }

    @FXML
    void displayAddAppointmentForm(ActionEvent event) throws IOException {

        Parent addAppointmentFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointmentForm.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentFormLoader);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addAppointmentScene);
        window.show();

    }

    @FXML
    void displayModifyAppointmentForm(ActionEvent event) throws IOException, SQLException {
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

        apptStartCol.setCellFactory(column -> {
            return new TableCell<Appointment, LocalDateTime>() {
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

            };
        });

        apptEndCol.setCellFactory(column -> {
            return new TableCell<Appointment, LocalDateTime>() {
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

            };
        });

    }

}
