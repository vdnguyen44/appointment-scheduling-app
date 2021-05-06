package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * <p>The class involving database operations on appointments, following the DAO pattern.</p>
 */

public class AppointmentsDAO {
    /**
     * <p>This method is used to retrieve a list of all appointments. It joins two tables and loops through the result set
     * to create the appointment objects, then adds the appointments to a list.</p>
     * @return Returns an observable list of all appointments that exist in the database.
     */

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
           String sqlStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, appointments.User_ID, contacts.Contact_ID, contacts.Contact_Name " +
                   "FROM contacts " +
                   "JOIN appointments ON contacts.Contact_ID=appointments.Contact_ID " +
                   "ORDER BY Appointment_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int apptID = resultSet.getInt("Appointment_ID");
                String apptTitle = resultSet.getString("Title");
                String apptDescription = resultSet.getString("Description");
                String apptLocation = resultSet.getString("Location");
                String apptType = resultSet.getString("Type");
                LocalDateTime apptStart = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime apptEnd = resultSet.getTimestamp("End").toLocalDateTime();
                LocalDateTime dateCreated = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                LocalDateTime lastUpdated = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");

                Appointment appointment = new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, dateCreated, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID, contactName);
                appointmentsList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * <p>This method checks whether the appointment times to be entered overlap with any existing appointments. It creates
     * a list of appointments based on the customer selected. Then the new start/end appointments times are checked against
     * every appointment in that list. The boolean true is returned if any conflicting appointments exist.</p>
     * @param selectedCustomerID The customer ID to check for overlapping appointments
     * @param newStart The new start local date/time to be added
     * @param newEnd The new end local date/time to be added
     * @return Returns a boolean based on whether the new appointment has any overlapping appointments
     * @throws SQLException Thrown if there is a database access error
     */
    public static boolean hasOverlapAppts(int selectedCustomerID, LocalDateTime newStart, LocalDateTime newEnd) throws SQLException {
        ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();

        try{
        String sqlStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, appointments.User_ID, contacts.Contact_ID, contacts.Contact_Name " +
                "FROM contacts " +
                "JOIN appointments ON contacts.Contact_ID=appointments.Contact_ID " +
                "WHERE appointments.Customer_ID=?;";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setInt(1, selectedCustomerID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int apptID = resultSet.getInt("Appointment_ID");
            String apptTitle = resultSet.getString("Title");
            String apptDescription = resultSet.getString("Description");
            String apptLocation = resultSet.getString("Location");
            String apptType = resultSet.getString("Type");
            LocalDateTime apptStart = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime apptEnd = resultSet.getTimestamp("End").toLocalDateTime();
            LocalDateTime dateCreated = resultSet.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            LocalDateTime lastUpdated = resultSet.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");

            Appointment appointment = new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, dateCreated, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID, contactName);
            customerAppts.add(appointment);
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }

        for (Appointment appt : customerAppts) {

            LocalDateTime apptStart = appt.getApptStart();
            LocalDateTime apptEnd = appt.getApptEnd();

            if (newStart.isBefore(apptEnd) && apptStart.isBefore(newEnd)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>This method retrieves a list of existing appointments associated with a user.</p>
     * @param currentUserID The user ID of the current user (user logged into the application)
     * @return A list of appointments associated with the user
     */
    public static ObservableList<Appointment> getUserAppts(int currentUserID) {

        ObservableList<Appointment> userAppts = FXCollections.observableArrayList();

        try{
            String sqlStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, appointments.User_ID, contacts.Contact_ID, contacts.Contact_Name " +
                    "FROM contacts " +
                    "JOIN appointments ON contacts.Contact_ID=appointments.Contact_ID " +
                    "WHERE appointments.User_ID=?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, currentUserID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int apptID = resultSet.getInt("Appointment_ID");
                String apptTitle = resultSet.getString("Title");
                String apptDescription = resultSet.getString("Description");
                String apptLocation = resultSet.getString("Location");
                String apptType = resultSet.getString("Type");
                LocalDateTime apptStart = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime apptEnd = resultSet.getTimestamp("End").toLocalDateTime();
                LocalDateTime dateCreated = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                LocalDateTime lastUpdated = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");

                Appointment appointment = new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, dateCreated, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID, contactName);
                userAppts.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAppts;
    }

}
