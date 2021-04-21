package View_Controller;

import DAO.UsersDAO;
import Model.User;
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

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable  {

    @FXML
    private TitledPane titledPane;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userNameTxtField;

    @FXML
    private Label zoneLabel;

    @FXML
    private Button logInButton;

    @FXML
    private Button closeAppBtn;

    @FXML
    private PasswordField passWordField;

    // ResourceBundle resourceBundle;

    String emptyFieldAlertHeaderTxt = "Empty Field Alert";
    String emptyFieldAlertContentTxt = "Username/Password may not be empty";

    String invalidCredentialsAlertHeaderTxt = "Invalid Credentials";
    String invalidCredentialsAlertContentTxt = "The username/password entered is invalid. Please try again.";

    @FXML
    void exitApp(ActionEvent event) {
        Stage stage = (Stage) closeAppBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void submitCredentials(ActionEvent event) throws IOException {

        ObservableList<User> users = UsersDAO.getAllUsers();
        boolean credentialsValid = false;

        if (userNameTxtField.getText().isEmpty() || passWordField.getText().isEmpty()){
            Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
            emptyFieldAlert.setHeaderText(emptyFieldAlertHeaderTxt);
            emptyFieldAlert.setContentText(emptyFieldAlertContentTxt);
            emptyFieldAlert.show();
        }
        else {
            for (User user : users) {
                if (userNameTxtField.getText().equals(user.getUserName()) && passWordField.getText().equals(user.getPassword())) {
                    credentialsValid = true;
                    UsersDAO.setCurrentUser(user);
                    Parent optionsFormLoader = FXMLLoader.load(getClass().getResource("/View_Controller/OptionsForm.fxml"));
                    Scene optionsScene = new Scene(optionsFormLoader);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(optionsScene);
                    window.show();

                    break;
                }

            }
            if (!credentialsValid) {
                Alert invalidCredentialsAlert = new Alert(Alert.AlertType.ERROR);
                invalidCredentialsAlert.setHeaderText(invalidCredentialsAlertHeaderTxt);
                invalidCredentialsAlert.setContentText(invalidCredentialsAlertContentTxt);
                invalidCredentialsAlert.show();

            }
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // resourceBundle = rb;

        if (Locale.getDefault().getLanguage().equals("fr")) {

            titledPane.setText(rb.getString("login"));
            userNameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            logInButton.setText(rb.getString("submit"));
            closeAppBtn.setText(rb.getString("exit"));

            emptyFieldAlertHeaderTxt = rb.getString("emptyFieldAlertHeaderTxt");
            emptyFieldAlertContentTxt = rb.getString("emptyFieldAlertContentTxt");

            invalidCredentialsAlertHeaderTxt = rb.getString("invalidCredentialsAlertHeaderTxt");
            invalidCredentialsAlertContentTxt = rb.getString("invalidCredentialsAlertContentTxt");
        }

        zoneLabel.setText(ZoneId.systemDefault().toString());

    }

}


