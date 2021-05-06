package Model;

/**
 * The contact class
 */
public class Contact {
    /**
     * The ID of a contact
     */
    private int contactID;
    /**
     * The name of a contact
     */
    private String contactName;
    /**
     * The email of a contact
     */
    private String contactEmail;

    /**
     * Constructor for the contact class
     * @param contactID Value to set as the contact's ID
     * @param contactName Value to set as the contact's name
     * @param contactEmail Value to set as the contact's email
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * <p>This method retrieves the ID from the contact</p>
     * @return ID of the contact
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * <p>This method sets the new contact ID</p>
     * @param contactID ID to be set as the new contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * <p>This method retrieves the name from the contact</p>
     * @return Name of the contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * <p>This method sets the new contact name</p>
     * @param contactName Name to be set as the new contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * <p>This method retrieves the email from the contact</p>
     * @return Email of the contact
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * <p>This method sets the new contact email</p>
     * @param contactEmail Email to be set as the new contact email
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * <p>This override method retrieve the string representation of the contact</p>
     * @return The value of the contact's name as a string instead of object
     */
    @Override
    public String toString(){
        return contactName;
    }
}
