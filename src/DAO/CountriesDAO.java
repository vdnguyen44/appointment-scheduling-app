package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CountriesDAO {

    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> countriesList = FXCollections.observableArrayList();

        try{
            String sqlStatement = "SELECT * from countries";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                Timestamp dateCreated = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");

                Country country = new Country(countryID, countryName, dateCreated, createdBy, lastUpdated, lastUpdatedBy);
                countriesList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countriesList;
    }


}
