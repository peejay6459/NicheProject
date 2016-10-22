Instructions

setTitle("Niche");
-> Login Page

Working Prototype:
1. Creating an account
	a. Click "Log In/Register" Button
	b. Dialog box will pop up asking if you want to create an account
	c. Click "Ok"

setTitle("Create Account");
-> Registration page

2. Saving an account
	a. Choose the desire Username
	b. Provide a valid Email
	c. Choose the password
	d. Confirm password
	e. Choose what type of user you are (Landlord/Tenant/Property Manager)
	f. Please choose "Tenant" as user type for now.
	f. Accept Terms of Services
	g. Accept Privacy Policy
	h. Click "Accept and Continue" Button

3. Validation
	a. Username and password must be from 8 to 14 characters
	b. Email must have "@" sign
	c. Password must match
	d. Must accept Terms of Services
	e. Must accept Privacy Policy

4. Login Depending on user type
	a. If you have a "tenant" account you are now on Tenant DashBoard
	b. If you have a "Landlord" account you are now on Landlord DashBoard
	c. If you have a "Property Manager" account you are now on Property Manager DashBoard
	d. You can logout using the action bar and click "Sign Out"

setTitle("Tenant Dashboard");
-> Tenant Dashboard Page
	a. There are 2 Tabs (Dashboard and News Feed) The only working tab is the Dashboard and the other tab is saved for future release
	b. Click on the "Personal Information" Button

setTitle("Personal Information");
-> Personal Information Page
	a. Every fields are disabled as default
	b. Click the "Edit" Button on action bar
	c. All the fields are now editable
	d. Edit some fields then click "save" button on action bar
Note: For now the data's are not being saved into the database but we're working on it. The idea is to pull the information on the database depending on userID. That way every other users will have different data's on their account.



