package utils;

import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A class that keeps track of alerts used throughout the application
 */

public class Alerts {

    /**
     * <p>This method creates and shows an alert that shows when any text field is empty during login.</p>
     */
    public static void showEmptyFieldAlert() {
        ResourceBundle rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());
        String emptyFieldAlertHeaderTxt = rb.getString("emptyFieldAlertHeaderTxt");
        String emptyFieldAlertContentTxt = rb.getString("emptyFieldAlertContentTxt");
        Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
        emptyFieldAlert.setTitle(emptyFieldAlertHeaderTxt);
        emptyFieldAlert.setHeaderText(null);
        emptyFieldAlert.setContentText(emptyFieldAlertContentTxt);
        emptyFieldAlert.show();
    }

    /**
     * <p>This method creates and shows an alert when a user's credentials are invalid.</p>
     */
    public static void showInvalidCredAlert() {
        ResourceBundle rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());
        String invalidCredentialsAlertHeaderTxt = rb.getString("invalidCredentialsAlertHeaderTxt");
        String invalidCredentialsAlertContentTxt = rb.getString("invalidCredentialsAlertContentTxt");
        Alert invalidCredentialsAlert = new Alert(Alert.AlertType.ERROR);
        invalidCredentialsAlert.setTitle(invalidCredentialsAlertHeaderTxt);
        invalidCredentialsAlert.setHeaderText(null);
        invalidCredentialsAlert.setContentText(invalidCredentialsAlertContentTxt);
        invalidCredentialsAlert.show();
    }

    /**
     * <p>This method creates and shows an alert when no customer is selected in the customer's table.</p>
     */
    public static void showNoCustomerSelectedAlert() {
        Alert noneSelectedAlert = new Alert(Alert.AlertType.ERROR);
        noneSelectedAlert.setTitle("Customer Selection Error");
        noneSelectedAlert.setHeaderText(null);
        noneSelectedAlert.setContentText("No customer is selected.");
        noneSelectedAlert.show();
    }

    /**
     * <p>This method creates and shows an alert when a customer is to be deleted.</p>
     * @return Button type and reply
     */
    public static Optional<ButtonType> showCustomerDeleteConfirmAlert() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Deletion Confirmation");
        deleteAlert.setHeaderText(null);
        deleteAlert.setContentText("Are you sure you want to delete this customer?");
        return deleteAlert.showAndWait();
    }

    /**
     * <p>This method creates and shows an alert that confirms the selected customer has been deleted.</p>
     */
    public static void showCustomerDeleteConfirmation() {
        Alert deleteConfirmation = new Alert(Alert.AlertType.INFORMATION);
        deleteConfirmation.setTitle("Deletion Confirmed");
        deleteConfirmation.setHeaderText(null);
        deleteConfirmation.setContentText("The selected customer has been deleted.");
        deleteConfirmation.show();
    }

    /**
     * <p>This method shows and creates an alert when no appointment is selected from the appointments table.</p>
     */
    public static void showNoApptSelectedAlert() {
        Alert noneSelectedAlert = new Alert(Alert.AlertType.ERROR);
        noneSelectedAlert.setTitle("Appointment Selection Error");
        noneSelectedAlert.setHeaderText(null);
        noneSelectedAlert.setContentText("No appointment is selected.");
        noneSelectedAlert.show();
    }

    /**
     * <p>This method creates and shows an alert when an appointment is to be deleted.</p>
     * @return Button type and reply
     */
    public static Optional<ButtonType> showApptDeleteConfirmAlert() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Deletion Confirmation");
        deleteAlert.setHeaderText(null);
        deleteAlert.setContentText("Are you sure you want to delete this appointment?");
        return deleteAlert.showAndWait();
    }

    /**
     * <p>This method shows and creates an alert confirming an appointment has been deleted and displays its ID/Type.</p>
     * @param selectedApptID The deleted appointment's ID
     * @param selectedApptType The deleted appointment's type
     */
    public static void showApptDeleteConfirmation(int selectedApptID, String selectedApptType) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.INFORMATION);
        deleteConfirmation.setTitle("Deletion Confirmed");
        deleteConfirmation.setHeaderText(null);
        deleteConfirmation.setContentText(selectedApptType + " appointment with ID: " + selectedApptID + " has been deleted.");
        deleteConfirmation.show();
    }

    /**
     * <p>This method creates and shows an alert when the appointment's start/end times are not within business hours.</p>
     */
    public static void showApptTimesError() {
        Alert apptTimesErrorAlert = new Alert(Alert.AlertType.ERROR);
        apptTimesErrorAlert.setTitle("Appointment Times Invalid");
        apptTimesErrorAlert.setHeaderText(null);
        apptTimesErrorAlert.setContentText("Appointment times must be within 8:00 a.m. EST and 10:00 p.m. EST.");
        apptTimesErrorAlert.show();
    }

    /**
     * <p>This method creates and shows an alert when a customer's appointment conflict with existing appointments.</p>
     */
    public static void showOverlapError() {
        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
        overlapAlert.setTitle("Appointment times overlap");
        overlapAlert.setHeaderText(null);
        overlapAlert.setContentText("This customer has existing times that overlap. Please select a different start/end time");
        overlapAlert.show();
    }

    /**
     * <p>This method creates and shows an alert when the user does not have any appointments within 15 minutes of login.</p>
     */
    public static void showNoApptSoonAlert() {
        Alert noApptSoonAlert = new Alert(Alert.AlertType.INFORMATION);
        noApptSoonAlert.setTitle("No Appointments Soon");
        noApptSoonAlert.setHeaderText(null);
        noApptSoonAlert.setContentText("There are no upcoming appointments.");
        noApptSoonAlert.show();
    }

    /**
     * <p>This method creates and shows an alert when the user has an appointment within 15 minutes of logging in.</p>
     * @param apptsSoon List of coming appointments associated with the user
     */
    public static void showApptIn15Alert(ObservableList<Appointment> apptsSoon) {
        StringBuilder contentText = new StringBuilder("You have an appointment within 15 minutes. \n");

        for (Appointment appt : apptsSoon ) {
            int apptID = appt.getApptID();
            LocalDateTime apptStart = appt.getApptStart();
            contentText.append("Appointment ID: ").append(apptID).append(" is at ").append(apptStart);
        }

        Alert apptIn15Alert = new Alert(Alert.AlertType.INFORMATION);
        apptIn15Alert.setTitle("Appointment Alert");
        apptIn15Alert.setHeaderText(null);
        apptIn15Alert.setContentText(contentText.toString());
        apptIn15Alert.show();
    }


}
