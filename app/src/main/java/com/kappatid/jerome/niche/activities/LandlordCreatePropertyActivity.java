package com.kappatid.jerome.niche.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.FieldHelper;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.InsertProperty;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Kappatid LLC
 * @title LandlordCreatePropertyActivity.java
 * @purpose This class is for creating a new property
 * @date November 03, 2016
 * @input Property Title, Property Location
 * @processing InsertProperty.java
 * @output Property Has been created
 */
public class LandlordCreatePropertyActivity extends AppCompatActivity implements InsertProperty.AsyncResponse {
    // Creating a textview for property title
    private TextView tvPropertyTitle;
    // Creating a textview for flat # and Street
    private TextView tvFlatStreet;
    // Creating a textview for Suburb
    private TextView tvSuburb;
    // Creating a button for adding a property
    private Button btnAddProperty;
    // Creating a JSON Object to store the data
    private JSONObject propertyObject;
    // Creating a userID variable
    private String userID;
    // Creating a Notification Builder
    private NotificationCompat.Builder notifCreateProperty;
    // Creating a unique ID for notification
    private static final int UNIQUE_ID = 4231;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_create_property);
        setTitle("Create Property");

        // Creating an object of FieldHelper
        FieldHelper fh = new FieldHelper();

        // Storing the userID from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");

        // Create a new object for Notification
        notifCreateProperty = new NotificationCompat.Builder(this);
        // Setting the autoCancel to true
        notifCreateProperty.setAutoCancel(true);

        // Casting
        tvPropertyTitle = (TextView) findViewById(R.id.tvPropertyTitle);
        tvFlatStreet = (TextView) findViewById(R.id.tvFlatStreet);
        tvSuburb = (TextView) findViewById(R.id.tvSuburb);
        btnAddProperty = (Button) findViewById(R.id.btnAddProperty);

        // Changing the textfield whenever the user changed focus
        fh.changeTextField(tvPropertyTitle, tvFlatStreet, tvSuburb, "# Bedrooms # Bathrooms", "Flat # and Street", "Suburb and City");
        fh.changeTextField(tvSuburb, tvPropertyTitle, tvFlatStreet, "Suburb and City", "# Bedrooms # Bathrooms", "Flat # and Street");
        fh.changeTextField(tvFlatStreet, tvSuburb, tvPropertyTitle, "Flat # and Street", "Suburb and City", "# Bedrooms # Bathrooms");

        // Setting an onclick listener for adding the property
        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validation of fields
                if (validateFields(tvPropertyTitle, tvFlatStreet, tvSuburb)) {
                    // Insert the data through middle tier
                    InsertProperty ip = new InsertProperty(LandlordCreatePropertyActivity.this, LandlordCreatePropertyActivity.this);
                    ip.execute(Settings.URL_ADDRESS_INSERT_PROPERTIES);

                    // Set up the notification Message and Icon
                    notifCreateProperty.setSmallIcon(R.drawable.niche);
                    notifCreateProperty.setTicker("Has been ticked");
                    notifCreateProperty.setWhen(System.currentTimeMillis());
                    notifCreateProperty.setContentTitle("Property Created");
                    notifCreateProperty.setContentText("A new property has been published");

                    // If the user is not inside the application, redirect the user to the login page
                    Intent seeNotif = new Intent(LandlordCreatePropertyActivity.this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(LandlordCreatePropertyActivity.this, 0, seeNotif, PendingIntent.FLAG_UPDATE_CURRENT);
                    notifCreateProperty.setContentIntent(pendingIntent);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(UNIQUE_ID, notifCreateProperty.build());

                }
            }
        });
    }

    // Storing data's into JSON Object
    public String getPropertyJsonObject() {
        try {
            propertyObject = new JSONObject();
            propertyObject.put("userID", userID);
            propertyObject.put("propertyTitle", tvPropertyTitle.getText().toString());
            propertyObject.put("flatStreet", tvFlatStreet.getText().toString());
            propertyObject.put("suburb", tvSuburb.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyObject.toString();
    }

    // If all the conditions are true, return true
    public boolean validateFields(TextView... tv) {
        boolean isValidated = false;
        if (!(tv[0].getText().toString().isEmpty())
                && !(tv[0].getText().toString().equals("# Bedrooms # Bathrooms"))
                && !(tv[1].getText().toString().isEmpty())
                && !(tv[1].getText().toString().equals("Flat # and Street"))
                && !(tv[2].getText().toString().isEmpty())
                && !(tv[2].getText().toString().equals("Suburb and City"))) {
            if (!(tv[1].getText().toString().contains(",")) && !(tv[2].getText().toString().contains(","))) {
                isValidated = true;
            } else {
                Toast.makeText(LandlordCreatePropertyActivity.this, "Please remove the comma (,)", Toast.LENGTH_SHORT).show();
                isValidated = false;
            }
        } else {
            Toast.makeText(LandlordCreatePropertyActivity.this, "Please fill out the required forms", Toast.LENGTH_SHORT).show();
        }
        return isValidated;
    }

    /**
     * @param response to get the result of inserting the property
     *                 <p>
     *                 Return message for user if the property has been created successfully
     *                 or it is an existing property
     */
    @Override
    public void processFinish(String response) {
        if (response.equals("already exist")) {
            Toast.makeText(LandlordCreatePropertyActivity.this, "Address already exist", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LandlordCreatePropertyActivity.this, "Property Created Successfully", Toast.LENGTH_SHORT).show();
            Intent goBackManageProperties = new Intent(LandlordCreatePropertyActivity.this, LandlordManagePropertiesActivity.class);
            LandlordCreatePropertyActivity.this.startActivity(goBackManageProperties);
        }
    }
}