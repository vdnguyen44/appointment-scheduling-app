package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomersDAO {

    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Create_Date, customers.Created_By,\n" +
                    "customers.Last_Update, customers.Last_Updated_By, countries.Country_ID, countries.Country, first_level_divisions.Division_ID, first_level_divisions.Division FROM countries \n" +
                    "JOIN first_level_divisions ON countries.Country_ID=first_level_divisions.Country_ID \n" +
                    "JOIN customers ON first_level_divisions.Division_ID=customers.Division_ID \n" +
                    "ORDER BY Customer_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String customerAddress = resultSet.getString("Address");
                String customerPostal = resultSet.getString("Postal_Code");
                String customerPhone = resultSet.getString("Phone");
                LocalDateTime dateCreated = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                LocalDateTime lastUpdated = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");


                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, dateCreated, createdBy, lastUpdated, lastUpdatedBy, countryID, countryName, divisionID, divisionName);
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }
}
