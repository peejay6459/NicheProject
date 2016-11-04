package com.kappatid.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.DeleteProperty;
import com.kappatid.jerome.niche.dao.LoadPropertyInformation;
import com.kappatid.jerome.niche.dao.UpdatePropertyInformation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kappatid LLC
 * @title LandlordEditPropertyActivity.java
 * @purpose This class is for creating editing the property
 * @date November 03, 2016
 * @input Property Title, Property Location
 * @processing UpdatePropertyInformation.java
 * @output Property has been updated
 */
public class LandlordEditPropertyActivity extends AppCompatActivity {
    // Creating textview for tvEditPropertyTitle
    private TextView tvEditPropertyTitle;
    // Creating textview for tvEditFlatStreet
    private TextView tvEditFlatStreet;
    // Creating textview for tvEditSuburb
    private TextView tvEditSuburb;
    // Creating a spinner for spinEditPropertyManager
    private Spinner spinEditPropertyManager;
    // Creating an int variable to store the row number
    private int rowNum;
    // Creating a string variable to store the userID
    private String userID;
    // Creating a JSON Object
    private JSONObject propInfoJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_edit_property);
        setTitle("Edit Property");

        // Store the row number and userID from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        rowNum = pref.getInt("rowNum", 0);
        userID = pref.getString("userID", "");

        // Casting
        tvEditPropertyTitle = (TextView) findViewById(R.id.tvEditPropertyTitle);
        tvEditFlatStreet = (TextView) findViewById(R.id.tvEditFlatStreet);
        tvEditSuburb = (TextView) findViewById(R.id.tvEditSuburb);
        spinEditPropertyManager = (Spinner) findViewById(R.id.spinEditPropertyManager);

        // Setting the textviews to uneditable
        tvEditPropertyTitle.setEnabled(false);
        tvEditFlatStreet.setEnabled(false);
        tvEditSuburb.setEnabled(false);
        spinEditPropertyManager.setEnabled(false);

        // Load the details of the property from the database through middle tier
        LoadPropertyInformation lpi = new LoadPropertyInformation(this, rowNum, userID, tvEditPropertyTitle, tvEditFlatStreet, tvEditSuburb);
        lpi.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_INFO);
    }

    // Setting up an action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_edit_property, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // if Edit button is clicked, the fields will become editable
        if (id == R.id.editPropertyInfo) {
            tvEditPropertyTitle.setEnabled(true);
            tvEditFlatStreet.setEnabled(true);
            tvEditSuburb.setEnabled(true);
            spinEditPropertyManager.setEnabled(true);
            setTextColorBlack(tvEditPropertyTitle, tvEditFlatStreet, tvEditSuburb);
        }
        // if Save button is clicked, the fields will become uneditable
        // and the property details will be updated
        if (id == R.id.savePropertyInfo) {
            UpdatePropertyInformation upi = new UpdatePropertyInformation(userID, LandlordEditPropertyActivity.this);
            upi.execute(Settings.URL_ADDRESS_UPDATE_PROPERTY_INFO);
            tvEditPropertyTitle.setEnabled(false);
            tvEditFlatStreet.setEnabled(false);
            tvEditSuburb.setEnabled(false);
            spinEditPropertyManager.setEnabled(false);
            setTextColorGray(tvEditPropertyTitle, tvEditFlatStreet, tvEditSuburb);

        }
        // deleting the property
        if (id == R.id.deleteProperty) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Property")
                    .setIcon(R.drawable.delete_icon)
                    .setMessage("Are you sure you want to delete this property?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DeleteProperty dp = new DeleteProperty(userID, LandlordEditPropertyActivity.this);
                            dp.execute(Settings.URL_ADDRESS_DELETE_PROPERTY);
                            Intent goManageProperties = new Intent(LandlordEditPropertyActivity.this, LandlordManagePropertiesActivity.class);
                            LandlordEditPropertyActivity.this.startActivity(goManageProperties);
                            Toast.makeText(LandlordEditPropertyActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Storing data's into JSON Object
    public String propertyInfoJsonObject() {
        try {
            propInfoJsonObject = new JSONObject();
            propInfoJsonObject.put("propertyTitle", tvEditPropertyTitle.getText().toString());
            propInfoJsonObject.put("flatStreet", tvEditFlatStreet.getText().toString());
            propInfoJsonObject.put("suburb", tvEditSuburb.getText().toString());
            propInfoJsonObject.put("spinPropertyManager", spinEditPropertyManager.getSelectedItem().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propInfoJsonObject.toString();
    }

    // set the text color to black
    public void setTextColorBlack(TextView... fields) {
        for (TextView field : fields) {
            field.setTextColor(Color.BLACK);
        }
    }

    // set the text color to gray
    public void setTextColorGray(TextView... fields) {
        for (TextView field : fields) {
            field.setTextColor(Color.GRAY);
        }
    }

}
