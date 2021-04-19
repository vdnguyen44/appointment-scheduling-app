package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomersDAO {

    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * from customers";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String customerAddress = resultSet.getString("Address");
                String customerPostal = resultSet.getString("Postal_Code");
                String customerPhone = resultSet.getString("Phone");
                Timestamp dateCreated = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionID = resultSet.getInt("Division_ID");


                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, dateCreated, createdBy, lastUpdated, lastUpdatedBy, divisionID);
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }
}
