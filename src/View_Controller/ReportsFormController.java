package View_Controller;

import DAO.ContactsDAO;
import Model.Contact;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import utils.DBConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The controller class for the reports form
 */

public class ReportsFormController implements Initializable {

    /**
     * The text area that displays each report
     */
    @FXML
    private TextArea reportsTxtArea;

    /**
     * The combo box that allows the user to select report to view
     */
    @FXML
    private ComboBox<String> reportSelection;

    /**
     * <p>This method initializes the report combo box with available report options. A lambda expression is used to
     * listen to changes in selection. The report changing logic is kept in one place is readable in determining which
     * report has been selected. Once a selection is made, an sql query is executed to pull the specified data from
     * the database. The data is converted into a string to set as the text area's content.</p>
     * @param url Location
     * @param rb Resource
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        reportSelection.getItems().add("Report 1");
        reportSelection.getItems().add("Report 2");
        reportSelection.getItems().add("Report 3");

        reportSelection.setOnAction(actionEvent -> {

            switch (reportSelection.getValue()) {
                case "Report 1": {

                    StringBuilder reportText = new StringBuilder(String.format("%-20s %-20s %-5s \n", "Month", "Type", "Count"));
                    try {
                        String report1Query = "SELECT MONTHNAME(appointments.Start), appointments.Type, Count(*) FROM appointments GROUP BY MONTHNAME(appointments.Start), appointments.Type ORDER BY MONTHNAME(appointments.Start) DESC;";
                        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(report1Query);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            String month = resultSet.getString("MONTHNAME(appointments.Start)");
                            String type = resultSet.getString("Type");
                            String count = String.valueOf(resultSet.getInt("Count(*)"));
                            String lineTxt = String.format("%-20s %-20s %-5s", month, type, count);
                            reportText.append(lineTxt).append("\n");
                        }
                        reportsTxtArea.setText(String.valueOf(reportText.toString()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "Report 2": {
//
                    ObservableList<Contact> allContacts = ContactsDAO.getAllContacts();
                    StringBuilder reportText = new StringBuilder(String.format("%-30s %-30s %-30s %-30s %-30s %-30s %-30s \n", "Appointment ID", "Title", "Type", "Description", "Start Date/Time", "End Date/Time", "Customer ID"));
                    for (Contact contact : allContacts) {
                        StringBuilder contactAppt = new StringBuilder(contact.getContactName() + "\n");
                        int contactID = contact.getContactID();

                        try {
                            String report2Query = "SELECT * FROM appointments WHERE Contact_ID=?";
                            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(report2Query);
                            preparedStatement.setInt(1, contactID);
                            ResultSet resultSet = preparedStatement.executeQuery();

                            while (resultSet.next()) {
                                String apptID = String.valueOf(resultSet.getInt("Appointment_ID"));
                                String title = resultSet.getString("Title");
                                String type = resultSet.getString("Type");
                                String description = resultSet.getString("Description");
                                String startDateTime = String.valueOf(resultSet.getTimestamp("Start"));
                                String endDateTime = String.valueOf(resultSet.getTimestamp("End"));
                                String customerID = String.valueOf(resultSet.getInt("Customer_ID"));
                                contactAppt.append(String.format("%-30s %-30s %-30s %-30s %-30s %-30s %-30s \n", apptID, title, type, description, startDateTime, endDateTime, customerID));

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        reportText.append(contactAppt);

                    }
                    reportsTxtArea.setText(String.valueOf(reportText));
                    break;
                }
                case "Report 3": {
                    StringBuilder reportText = new StringBuilder(String.format("%-20s %10s \n", "Country", "Avg Appointment Duration"));

                    try {
                        String report3Query = "SELECT countries.Country, AVG(TIMESTAMPDIFF(MINUTE, appointments.Start, appointments.End)) AS \"AVG APPT DURATION\" FROM countries \n" +
                                "JOIN first_level_divisions ON countries.Country_ID=first_level_divisions.Country_ID\n" +
                                "JOIN customers ON first_level_divisions.Division_ID=customers.Division_ID\n" +
                                "JOIN appointments ON customers.Customer_ID=appointments.Customer_ID\n" +
                                "GROUP BY countries.Country_ID;";
                        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(report3Query);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            String country = resultSet.getString("Country");
                            String avgApptDuration = String.format("%.2f", resultSet.getDouble("AVG APPT DURATION"));
                            reportText.append(String.format("%-20s %10s min \n", country, avgApptDuration));
                        }
                        reportsTxtArea.setText(String.valueOf(reportText));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        });

    }

}
