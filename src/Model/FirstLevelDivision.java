package Model;

import java.sql.Timestamp;

/**
 * The first level division class
 */

public class FirstLevelDivision {
    /**
     * ID of the first level division
     */
    private int divisionID;
    /**
     * Name of the first level division
     */
    private String divisionName;
    /**
     * Date the first level division was created in database
     */
    private Timestamp dateCreated;
    /**
     * User who created the first level division in database
     */
    private String createdBy;
    /**
     * Date the first level division was last updated in database
     */
    private Timestamp lastUpdated;
    /**
     * User who last updated first level division in database
     */
    private String lastUpdatedBy;
    /**
     * ID of country associated with first level division in database
     */
    private int countryID;

    /**
     * Constructor for first level division
     * @param divisionID Value to be set as division's ID
     * @param divisionName Value to be set as division's name
     * @param dateCreated Value to be set as date division was created in database
     * @param createdBy Value to be set as user who created division in database
     * @param lastUpdated Value to be set as when division was last updated in database
     * @param lastUpdatedBy Value to be set as who last updated division in database
     * @param countryID Value to be set as ID of country division is associated with in database
     */
    public FirstLevelDivision(int divisionID, String divisionName, Timestamp dateCreated, String createdBy, Timestamp lastUpdated, String lastUpdatedBy,  int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * <p>This method retrieves the first level division's ID</p>
     * @return ID of first level division
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * <p>This method sets the new ID of the fist level division</p>
     * @param divisionID ID to be set as the first level division's ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * <p>This method retrieves the first level division's name</p>
     * @return Name of first level division
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * <p>This method sets the new name of the fist level division</p>
     * @param divisionName Name to be set as the first level division's name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * <p>This method retrieves the date first level division was created in database</p>
     * @return Date first level division was created in database
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * <p>This method sets the new date of when first level division was created in database</p>
     * @param dateCreated Date to be set as date first level division was created in database
     */
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * <p>This method retrieves the user who created first level division in database</p>
     * @return User who created first level division in database
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * <p>This method sets the new user who created the first level division in database</p>
     * @param createdBy New user to be set who created the first level division in database
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * <p>This method retrieves the date first level division was last updated in database</p>
     * @return Date first level division was last updated in database
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * <p>This method sets the new date for when the first level division was last updated in database</p>
     * @param lastUpdated New date to be set for when first level division was last updated in database
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * <p>This method retrieves the user who last updated first level division in database</p>
     * @return User who last updated first level division in database
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * <p>This method sets the new user who last updated the first level division in database</p>
     * @param lastUpdatedBy New user who last updated the first level division in database
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * <p>This method retrieves the ID of country associated with first level division in database</p>
     * @return ID of country associated with first level division in database
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * <p>This method sets the new ID of the country associated with the first level division in database</p>
     * @param countryID New ID of the country associated with the first level division in database
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * <p>This override method retrieve the string representation of the first level division</p>
     * @return The value of the first level division's name as a string instead of object
     */
    @Override
    public String toString(){
        return divisionName;
    }
}
