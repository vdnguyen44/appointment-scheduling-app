package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>The class involving database operations on contacts, following the DAO pattern.</p>
 */

public class ContactsDAO {

    /**
     * <p>This method queries the database for existing contacts.</p>
     * @return An observable list of all existing contacts in the database
     */

    public static ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * FROM contacts";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");

                Contact contact = new Contact(contactID, contactName, contactEmail);
                contactsList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsList;
    }
}
