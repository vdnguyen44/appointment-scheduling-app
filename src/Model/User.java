package Model;

import java.time.LocalDateTime;

/**
 * The user class
 */

public class User {
    /**
     * ID of the user
     */
    private int userID;
    /**
     * Username of the user
     */
    private String userName;
    /**
     * Password of the user
     */
    private String password;
    /**
     * Date user was created in database
     */
    private LocalDateTime dateCreated;
    /**
     * The entity that created the user in database
     */
    private String createdBy;
    /**
     * Date user was last updated in database
     */
    private LocalDateTime lastUpdated;
    /**
     * The entity that last updated the user in database
     */
    private String lastUpdatedBy;

    /**
     * Constructor for user
     * @param userID Value to be set as user's ID
     * @param userName Value to be set as user's username
     * @param password Value to be set as user's password
     * @param dateCreated Value to be set as date user was created in database
     * @param createdBy Value to be set as entity that created the user in database
     * @param lastUpdated Value to be set as when user was last updated in database
     * @param lastUpdatedBy Value to be set as last entity to update user in database
     */
    public User(int userID, String userName, String password, LocalDateTime dateCreated, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * <p>This method retrieves the user's ID</p>
     * @return ID of user
     */
    public int getUserID() {
        return userID;
    }

    /**
     * <p>This method sets the new ID of the user</p>
     * @param userID ID to be set as the user's ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * <p>This method retrieves the user's username</p>
     * @return Username of user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <p>This method sets the new username of the user</p>
     * @param userName Username to be set as the user's username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * <p>This method retrieves the user's password</p>
     * @return Password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     *  <p>This method sets the new password of the user</p>
     * @param password Password to be set as the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>This method retrieves the date user was created in database</p>
     * @return Date user was created in database
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * <p>This method sets the new date for when the user was created in database</p>
     * @param dateCreated Date to be set for when the user was created in database
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * <p>This method retrieves the entity that created the user in database</p>
     * @return Entity that created the user in database
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * <p>This method sets the new entity who created the user in database</p>
     * @param createdBy Entity to be set who created the user in database
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * <p>This method retrieves the date user was last updated in database</p>
     * @return Date user was last updated in database
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * <p>This method sets the new date when the user was last updated in database</p>
     * @param lastUpdated Date to be set when the user was last updated in database
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * <p>This method retrieves the entity that last updated the user in database</p>
     * @return Entity that last updated the user in database
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * <p>This method sets the new entity who last updated the user in database</p>
     * @param lastUpdatedBy Entity to be set that last updated the user in database
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * <p>This override method retrieves the string representation of the user as a combination of ID/username</p>
     * @return Value of user's ID/username as string instead of object
     */
    @Override
    public String toString(){
        return "ID: " + userID + ", " + userName;
    }
}
