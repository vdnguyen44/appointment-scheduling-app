package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

                Country country = new Country(countryID, countryName);
                countriesList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countriesList;
    }

}
