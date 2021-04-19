package DAO;

import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionsDAO {

    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {

        ObservableList<FirstLevelDivision> firstLevelDivisionsList = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * from first_level_divisions";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryID = resultSet.getInt("COUNTRY_ID");

                FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, countryID);
                firstLevelDivisionsList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstLevelDivisionsList;
    }
}
