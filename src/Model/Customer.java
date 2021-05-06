package Model;

import java.time.LocalDateTime;

/**
 * The customer class
 */
public class Customer {
    /**
     * The ID of a customer
     */
    private int customerID;
    /**
     * The name of a customer
     */
    private String customerName;
    /**
     * The address of a customer
     */
    private String customerAddress;
    /**
     * The postal code of a customer
     */
    private String customerPostal;
    /**
     * The phone number of a customer
     */
    private String customerPhone;
    /**
     * The date/time a customer was created in the database
     */
    private LocalDateTime dateCreated;
    /**
     * The user who created the customer in the database
     */
    private String createdBy;
    /**
     * The date/time the customer was last updated in the database
     */
    private LocalDateTime lastUpdated;
    /**
     * The user who last updated the customer in the database
     */
    private String lastUpdatedBy;
    /**
     * The ID of the country associated with the customer
     */
    private int countryID;
    /**
     * The name of the country associated with the customer
     */
    private String countryName;
    /**
     * The ID of the first level division associated with the customer
     */
    private int divisionID;
    /**
     * The name of the first level division associated with the customer
     */
    private String divisionName;


    /**
     * Constructor for the customer class
     * @param customerID Value to be set as the customer's ID
     * @param customerName Value to be set as the customer's name
     * @param customerAddress Value to be set as the customer's address
     * @param customerPostal Value to be set as the customer's postal code
     * @param customerPhone Value to be set as the customer's phone number
     * @param dateCreated Value to be set as the date the customer was created in the database
     * @param createdBy Value to be set as the user who created the customer the in the database
     * @param lastUpdated Value to be set as the date the customer was last updated in the database
     * @param lastUpdatedBy Value to be set as the user who last updated the customer in the database
     * @param countryID Value to be set as the ID of the country associated with the customer
     * @param countryName Value to be set as the name of the country associated with the customer
     * @param divisionID Value to be set as the ID of the first level division associated with the customer in the database
     * @param divisionName Value to be set as the name of the first level division associated with the customer in the database
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPostal, String customerPhone, LocalDateTime dateCreated, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int countryID, String countryName, int divisionID, String divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostal = customerPostal;
        this.customerPhone = customerPhone;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
        this.countryName = countryName;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    /**
     * <p>This method retrieves the customer's ID</p>
     * @return ID of the customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * <p>This method sets the new ID of the customer</p>
     * @param customerID ID to be set as the customer's ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * <p>This method retrieves the customer's name</p>
     * @return Name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * <p>This method sets the new name of the customer</p>
     * @param customerName Name to be set as the customer's name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * <p>This method retrieves the customer's address</p>
     * @return Address of the customer
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * <p>This method sets the new address of the customer</p>
     * @param customerAddress Address to be set as the customer's address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * <p>This method retrieves the customer's postal code</p>
     * @return Postal code of the customer
     */
    public String getCustomerPostal() {
        return customerPostal;
    }

    /**
     * <p>This method sets the new postal code of the customer</p>
     * @param customerPostal Postal code to be set as the customer's postal code
     */
    public void setCustomerPostal(String customerPostal) {
        this.customerPostal = customerPostal;
    }

    /**
     * <p>This method retrieves the customer's phone number</p>
     * @return Phone number of the customer
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * <p>This method sets the new phone number of the customer</p>
     * @param customerPhone Phone number to be set as the customer's phone number
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * <p>This method retrieves the date the customer was created in the database</p>
     * @return Date customer was created in database
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * <p>This method sets the new date the customer was created in database</p>
     * @param dateCreated Date to be set as date customer was created in database
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * <p>This method retrieves the user who created the customer in the database</p>
     * @return User who created customer in database
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *<p>This method sets the new user who created the customer in database</p>
     * @param createdBy User to be set as user who created customer in database
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * <p>This method retrieves the date the customer was last updated in the database</p>
     * @return Date customer was last updated in database
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     *<p>This method sets the new date the customer was last updated in database</p>
     * @param lastUpdated Date to be set as date customer was last updated in database
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * <p>This method retrieves the user who last updated the customer in the database</p>
     * @return User who last updated customer in database
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *<p>This method sets the new user who last updated the customer in database</p>
     * @param lastUpdatedBy User to be set as user who last updated customer in database
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * <p>This method retrieves the ID of the country associated with the customer in the database</p>
     * @return ID of country associated with customer in database
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *<p>This method sets the new ID of the country associated with the customer in database</p>
     * @param countryID ID of country to be set as that associated with customer in database
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * <p>This method retrieves the name of the country associated with the customer in the database</p>
     * @return Name of country associated with customer in database
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *<p>This method sets the new name of the country associated with the customer in database</p>
     * @param countryName Name of country to be set as that associated with customer in database
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * <p>This method retrieves the ID of the first level division associated with the customer in the database</p>
     * @return ID of first level division associated with customer in database
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *<p>This method sets the new ID of the first level division associated with the customer in database</p>
     * @param divisionID ID of first level division to be set as that associated with customer in database
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * <p>This method retrieves the name of the first level division associated with the customer in the database</p>
     * @return Name of first level division associated with customer in database
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *<p>This method sets the new name of the first level division associated with the customer in database</p>
     * @param divisionName Name of first level division to be set as that associated with customer in database
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * <p>This override method retrieves the string representation of the customer as a combination of ID/name</p>
     * @return Value of customer's ID/name as string instead of object
     */
    @Override
    public String toString(){
        return "ID: " + customerID + ", " + customerName;
    }
}
