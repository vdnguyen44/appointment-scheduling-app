package Model;

import java.sql.Timestamp;

/**
 * The country class
 */
public class Country {
    /**
     * The ID of a country
     */
    private int countryID;
    /**
     * The name of a country
     */
    private String countryName;
    /**
     * The date the country was created in the database
     */
    private Timestamp dateCreated;
    /**
     * The user who created the country in the database
     */
    private String createdBy;
    /**
     * The date/time the country was last updated in the database
     */
    private Timestamp lastUpdated;
    /**
     * The user who last updated the country in the database
     */
    private String lastUpdatedBy;

    /**
     * Constructor for the country class
     * @param countryID Value to set as the country's ID
     * @param countryName Value to set as the country's name
     * @param dateCreated Value to set as the date the country was created in the database
     * @param createdBy Value to set as the user who created the country in the database
     * @param lastUpdated Value to set as the date the country was last updated in the database
     * @param lastUpdatedBy Value to set as the user who last updated the country in the database
     */
    public Country(int countryID, String countryName, Timestamp dateCreated, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * <p>This method retrieves the country's ID</p>
     * @return ID of the country
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * <p>This method sets the new ID of the country</p>
     * @param countryID ID to be set as the country's ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * <p>This method retrieves the country's name</p>
     * @return name of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * <p>This method sets the new name of the country</p>
     * @param countryName Name to be set as the country's name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * <p>This method retrieves the date the country was created in the database</p>
     * @return Date the country was created in the database
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * <p>This method sets the new date the country was created in the database</p>
     * @param dateCreated Date to be set as the date the country was created in the database
     */
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * <p>This method retrieves the user who created the country in the database</p>
     * @return User who created the country in the database
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * <p>This method sets the new user who created the country in the database</p>
     * @param createdBy User to be set as the user who created the country in the database
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * <p>This method retrieves the date/time the country was last updated in the database</p>
     * @return Date/time country was last updated in the database
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * <p>This method sets the new date/time as the time the country was last updated in the database</p>
     * @param lastUpdated The new date/time to be set as the last time the country was updated in the database
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * <p>This method retrieves the user who last updated the country in the database</p>
     * @return User who last updated the country in the database
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * <p>This method sets the new user who last updated the country in the database</p>
     * @param lastUpdatedBy The new user who last updated the country in the database
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * <p>This override method retrieve the string representation of the country</p>
     * @return The value of the country's name as a string instead of object
     */
    @Override
    public String toString(){
        return countryName;
    }
}
