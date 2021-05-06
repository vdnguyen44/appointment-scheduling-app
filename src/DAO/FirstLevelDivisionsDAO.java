package DAO;

import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * <p>The class involving database operations on first level divisions, following the DAO pattern.</p>
 */

public class FirstLevelDivisionsDAO {

    /**
     * <p>This method queries the database for existing first level divisions.</p>
     * @return An observable list of all existing first level divisions in the database
     */

    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {

        ObservableList<FirstLevelDivision> firstLevelDivisionsList = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * from first_level_divisions";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                Timestamp dateCreated = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int countryID = resultSet.getInt("COUNTRY_ID");

                FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, dateCreated, createdBy, lastUpdated, lastUpdatedBy, countryID);
                firstLevelDivisionsList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstLevelDivisionsList;
    }
}
