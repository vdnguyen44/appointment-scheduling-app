package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class using jdbc driver to connect to database
 */

public class DBConnection {

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = " WJ07r6a";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    // Driver Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    private static final String username = "U07r6a";
    private static final String password = "53689110923";


    /**
     * <p>This method uses the provided credentials and driver to connect to the mySQL database.</p>
     * @return connection to database
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * <p>This method gets the connection when called instead of having to restart the connection.</p>
     * @return connection to database
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * <p>This method gives a visual confirmation that the connection has closed by printing to the console.</p>
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
