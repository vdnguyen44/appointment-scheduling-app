package View_Controller;

import DAO.AppointmentsDAO;
import DAO.UsersDAO;
import Model.Appointment;
import Model.User;
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
import utils.Alerts;
import utils.UserLogInActivity;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * The controller class for the login form
 */

public class LoginFormController implements Initializable  {

    /**
     * The pane containing other GUI elements on this form
     */
    @FXML
    private TitledPane titledPane;

    /**
     * The label specifying the username
     */
    @FXML
    private Label userNameLabel;

    /**
     * The label specifying the password
     */
    @FXML
    private Label passwordLabel;

    /**
     * The text field to collect the user's username
     */
    @FXML
    private TextField userNameTxtField;

    /**
     * The label the specify the user's zone
     */
    @FXML
    private Label zoneLabel;

    /**
     * The button to submit a user's credentials
     */
    @FXML
    private Button logInButton;

    /**
     * The button to close the application
     */
    @FXML
    private Button closeAppBtn;

    /**
     * The text field to collect the user's password
     */
    @FXML
    private PasswordField passWordField;



//    String emptyFieldAlertHeaderTxt = "Credentials missing";
//    String emptyFieldAlertContentTxt = "Username/Password may not be empty";
//
//    String invalidCredentialsAlertHeaderTxt = "Invalid Credentials";
//    String invalidCredentialsAlertContentTxt = "The username/password entered is invalid. Please try again.";

    /**
     * <p>This method closes the application</p>
     * @param event When the exit button is pressed
     */
    @FXML
    void exitApp(ActionEvent event) {
        Stage stage = (Stage) closeAppBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * <p>This method handles the submission of a user's credentials. If either the username or password fields are empty,
     *  an error alert will be shown. The user's credentials are searched for matching values in the database. If there
     *  is a match, the user is set as the current user, the login attempt is written to a file as successful, and the
     *  options form is loaded. The appointments belonging to a user is also searched and an alert is displayed stating
     *  whether a user has any appointments within 15 minutes.</p>
     * @param event When the submit button is pressed
     * @throws IOException Exception thrown if the options form fxml cannot be located
     */
    @FXML
    void submitCredentials(ActionEvent event) throws IOException {

        ObservableList<User> users = UsersDAO.getAllUsers();
        boolean credentialsValid = false;

        if (userNameTxtField.getText().isEmpty() || passWordField.getText().isEmpty()){
            Alerts.showEmptyFieldAlert();
        }
        else {
            for (User user : users) {
                if (userNameTxtField.getText().equals(user.getUserName()) && passWordField.getText().equals(user.getPassword())) {
                    credentialsValid = true;
                    UserLogInActivity.updateLogInActivity(userNameTxtField.getText(), "was successful");
                    UsersDAO.setCurrentUser(user);
                    Parent optionsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/OptionsForm.fxml"));
                    Scene optionsScene = new Scene(optionsFormLoader);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(optionsScene);
                    window.show();

                    int currentUserID = UsersDAO.getCurrentUser().getUserID();
                    ObservableList<Appointment> userAppts = AppointmentsDAO.getUserAppts(currentUserID);

                    ObservableList<Appointment> apptsSoon = FXCollections.observableArrayList();


                    ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
                    LocalDateTime now = LocalDateTime.now();
                    ZonedDateTime zonedNow = ZonedDateTime.of(now, localZoneID);


                    for ( Appointment appt : userAppts) {
                        LocalDateTime apptStart = appt.getApptStart();
                        ZonedDateTime zonedApptStart = ZonedDateTime.of(apptStart, localZoneID);

                        long timeDiff = ChronoUnit.MINUTES.between(zonedApptStart, zonedNow) * -1;


                        if (timeDiff >= 0 && timeDiff <= 15) {
                            apptsSoon.add(appt);
                        }
                    }

                    if (apptsSoon.isEmpty()) {
                        Alerts.showNoApptSoonAlert();
                    }
                    else {
                        Alerts.showApptIn15Alert(apptsSoon);
                    }

                    break;
                }

            }
            if (!credentialsValid) {
                UserLogInActivity.updateLogInActivity(userNameTxtField.getText(), "failed");
                Alerts.showInvalidCredAlert();
            }
        }

    }

    /**
     * <p>This method initializes the form's GUI elements by checking for the system default's language.
     * If it is french, the GUI elements' text will change to french. The zone label is set to the user's zone</p>
     * @param url Location
     * @param rb Resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (Locale.getDefault().getLanguage().equals("fr")) {

            titledPane.setText(rb.getString("login"));
            userNameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            logInButton.setText(rb.getString("submit"));
            closeAppBtn.setText(rb.getString("exit"));

//            emptyFieldAlertHeaderTxt = rb.getString("emptyFieldAlertHeaderTxt");
//            emptyFieldAlertContentTxt = rb.getString("emptyFieldAlertContentTxt");

//            invalidCredentialsAlertHeaderTxt = rb.getString("invalidCredentialsAlertHeaderTxt");
//            invalidCredentialsAlertContentTxt = rb.getString("invalidCredentialsAlertContentTxt");
        }

        zoneLabel.setText(ZoneId.systemDefault().toString());

    }

}


