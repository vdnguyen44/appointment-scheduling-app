package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * <p>The class involving database operations on users, following the DAO pattern.</p>
 */

public class UsersDAO {

    /**
     * The user logged into the application.
     */
    private static User currentUser;

    /**
     * <p>This method queries the database for existing users.</p>
     * @return An observable list of all existing users in the database
     */

    public static ObservableList<User> getAllUsers() {

        ObservableList<User> usersList = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * from users";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userID = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                LocalDateTime dateCreated = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                LocalDateTime lastUpdated = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");

                User user = new User(userID, userName, password, dateCreated, createdBy, lastUpdated, lastUpdatedBy);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    /**
     * <p>This method sets the current user of the application.</p>
     * @param user The user to be set as current user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * <p>This method retrieves the current user.</p>
     * @return The current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }
}
