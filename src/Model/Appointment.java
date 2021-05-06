package Model;

import java.time.LocalDateTime;

/**
 * The appointment class
 */

public class Appointment {
    /**
     * The ID of an appointment
     */
    private int apptID;

    /**
     * The title of an appointment
     */
    private String apptTitle;

    /**
     * The description of an appointment
     */
    private String apptDescription;

    /**
     * The location of an appointment
     */
    private String apptLocation;

    /**
     * The type of an appointment
     */
    private String apptType;

    /**
     * The start date/time of an appointment
     */
    private LocalDateTime apptStart;

    /**
     * The end date/time of an appointment
     */
    private LocalDateTime apptEnd;

    /**
     * The date an appointment was created
     */
    private LocalDateTime dateCreated;

    /**
     * The user who created an appointment
     */
    private String createdBy;

    /**
     * The date an appointment was last updated
     */
    private LocalDateTime lastUpdated;

    /**
     * The user who last updated an appointment
     */
    private String lastUpdatedBy;

    /**
     * The ID of the customer associated with an appointment
     */
    private int customerID;

    /**
     * The ID of the user associated with an appointment
     */
    private int userID;

    /**
     * The ID of the contact associated with an appointment
     */
    private int contactID;

    /**
     * The name of the contact associated with an appointment
     */
    private String contactName;

    /**
     * The constructor for the appointment class
     * @param apptID Value to set as appointment's ID
     * @param apptTitle Value to set as appointment's title
     * @param apptDescription Value to set as appointment's description
     * @param apptLocation Value to set as appointment's location
     * @param apptType Value to set as appointment's type
     * @param apptStart Value to set as appointment's start date/time
     * @param apptEnd Value to set as appointment's end date/time
     * @param dateCreated Value to set as the date the appointment was created
     * @param createdBy Value to set as the user who created the appointment
     * @param lastUpdated Value to set as the date/time the appointment was last updated
     * @param lastUpdatedBy Value to set as the user who last updated the appointment
     * @param customerID Value to set as the ID of the customer the appointment is associated with
     * @param userID Value to set as the ID of the user the appointment is associated with
     * @param contactID Value to set as the ID of the contact the appointment is associated with
     * @param contactName Value to set as the name of the contact the appointment is associated with
     */

    public Appointment(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd,
                       LocalDateTime dateCreated, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int customerID, int userID, int contactID, String contactName) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * <p>This method retrieves the ID from an appointment</p>
     * @return The ID of an appointment
     */
    public int getApptID() {
        return apptID;
    }

    /**
     * <p>This method sets the new appointment ID</p>
     * @param apptID The ID to set as the new appointment ID
     */
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    /**
     * <p>This method retrieves the title from an appointment</p>
     * @return The title of an appointment
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * <p>This method sets the new appointment title</p>
     * @param apptTitle The title to set as the new appointment title
     */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * <p>This method retrieves the description from an appointment</p>
     * @return The description of an appointment
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     * <p>This method sets the new appointment description</p>
     * @param apptDescription The description to set as the new appointment description
     */
    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    /**
     * <p>This method retrieves the location from an appointment</p>
     * @return The location of an appointment
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     * <p>This method sets the new appointment location</p>
     * @param apptLocation The location to set as the new appointment location
     */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * <p>This method retrieves the type from an appointment</p>
     * @return The type of an appointment
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * <p>This method sets the new appointment type</p>
     * @param apptType The type to be set as the new appointment type
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * <p>This method retrieves the start date/time from an appointment</p>
     * @return Start date/time of an appointment
     */
    public LocalDateTime getApptStart() {
        return apptStart;
    }

    /**
     * <p>This method sets the new appointment start date/time</p>
     * @param apptStart The start date/time to be set as the new appointment start date/time
     */
    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }

    /**
     * <p>This method retrieves the end date/time from an appointment</p>
     * @return End date/time of an appointment
     */
    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    /**
     * <p>This method sets the new appointment end date/time</p>
     * @param apptEnd The end date/time to be set as the new appointment start date/time
     */
    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
    }

    /**
     * <p>This method retrieves the date the appointment was created</p>
     * @return Date appointment was created
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * <p>This method sets a new date for date created</p>
     * @param dateCreated Date to be set as new date created
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * <p>This method retrieves the user who created the appointment</p>
     * @return User who created the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * <p>This method sets a new user for who created the appointment</p>
     * @param createdBy User who created the appointment
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * <p>This method retrieves the date/time the appointment was last updated</p>
     * @return The date/time the appointment was last updated
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * <p>This method sets a new date/time as the last time the appointment was last updated</p>
     * @param lastUpdated The date/time appointment was last updated
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * <p>This method retrieves the user who last updated the appointment</p>
     * @return User who last updated appointment
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * <p>This method sets a new user as the user who last updated the appointment</p>
     * @param lastUpdatedBy User who last updated appointment
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * <p>This method retrieves the customer ID associated with the appointment</p>
     * @return Customer ID associated with appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * <p>This method sets a new ID for the customer ID associated with the appointment</p>
     * @param customerID The ID to set as the new customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * <p>This method retrieves the user ID associated with the appointment</p>
     * @return User ID associated with appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     * <p>This method sets a new ID for the user ID associated with the appointment</p>
     * @param userID The ID to set as the new user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * <p>This method retrieves the contact ID associated with the appointment</p>
     * @return Contact ID associated with appointment
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * <p>This method sets a new ID for the contact ID associated with the appointment</p>
     * @param contactID The ID to set as the new contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * <p>This method retrieves the contact name associated with the appointment</p>
     * @return Contact name associated with appointment
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * <p>This method sets a new name for the contact associated with the appointment</p>
     * @param contactName The new name to be set for the contact associated with the appointment
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
