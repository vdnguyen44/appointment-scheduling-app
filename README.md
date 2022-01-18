# Appointment Scheduling Application

Purpose: This application aims to manage a group of customers, users, and contacts and the appointments they are associated with. Due to removal of access from the 
MySQL workbench, images of each screen and descriptions of functionality will be provided.

# Login Form

![image](https://user-images.githubusercontent.com/41936050/150024667-2b2bff4b-6faf-4ddd-b905-6b7f776af491.png)

The login screen provides the function for users to log in using their credentials. Only users with matching credentials will be allowed to proceed. Otherwise, they will
receive an alert to try again.

# Options Form

![image](https://user-images.githubusercontent.com/41936050/150025083-4f9eff20-b872-4bd1-b67c-1f45bd00ebf6.png)

The options screen offers the user three options: customers form, appointments form, and reports form. Users will be forwarded to the appropriate screen once an option
is selected.


# Customers Form

![image](https://user-images.githubusercontent.com/41936050/150025505-baf79802-ef79-48d2-81bc-04c9cfdc2a09.png)

The application will query the database for the existing list of customers and populate the table. Customers can be added, modified, and deleted using the buttons below
the table.

# Add Customer Form

![image](https://user-images.githubusercontent.com/41936050/150025848-760e402e-b459-47c3-881f-088f9a1adb2b.png)

Customer IDs are automatically generated. Once the textboxes are filled and selections are made in the dropdown bar, the user must press save to add the customer. Note: textboxes must contain valid input.

# Modify Customer Form

![image](https://user-images.githubusercontent.com/41936050/150026351-79bd90b7-e8b2-4196-b3d8-da46de3b7146.png)

The form will be prepopulated with the selected customer's information. Textboxes and dropdown bars can be edited. Once saved, the customer's information will be updated.

# Appointments Form

![image](https://user-images.githubusercontent.com/41936050/150026892-05508db0-156e-42d0-ab64-7b1481d98902.png)

The application queries the database for a list of current and future appointments. Appointments can also be added, modified, or deleted. The combobox allows the user
to select a view of appointments based on their preference.


