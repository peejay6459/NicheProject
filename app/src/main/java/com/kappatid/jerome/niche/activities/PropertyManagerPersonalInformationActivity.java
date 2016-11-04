package com.kappatid.jerome.niche.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.FieldHelper;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.LoadPropertyManagerInformation;
import com.kappatid.jerome.niche.dao.UpdatePropertyManagerInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title PropertyManagerPersonalInformationActivity.java
 * @purpose This class is for Manager's Personal Information
 * @date November 03, 2016
 * @input name, date of birth, phone, mobile, address, country, gender
 * @processing UpdatePropertyManagerInformation
 * @output Updated personal information
 */

public class PropertyManagerPersonalInformationActivity extends AppCompatActivity {
    // Creating a textview for editName
    private TextView editName;
    // Creating a textview for editDob
    private TextView editDob;
    // Creating a textview for editPhone
    private TextView editPhone;
    // Creating a textview for editMobile
    private TextView editMobile;
    // Creating a textview for editAdd1
    private TextView editAdd1;
    // Creating a textview for editAdd2
    private TextView editAdd2;
    // Creating a textview for editSuburb
    private TextView editSuburb;
    // Creating a textview for editCity
    private TextView editCity;
    // Creating a textview for editCountry
    private TextView editCountry;
    // Creating a Radio Button for rdioMale
    private RadioButton rdioMale;
    // Creating a Radio Button for rdioFemale
    private RadioButton rdioFemale;
    // Creating an arraylist of fields
    private ArrayList<TextView> fields = new ArrayList<>();
    // Creating an object of Field Helper
    private FieldHelper fh = new FieldHelper();
    // Creating a JSON Object
    private JSONObject propertyManagerInfo;
    // String variable to store the userID
    private String userID;
    // String variable to store the username
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager_personal_information);
        setTitle("Personal Information");

        // Store the userID and username from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");
        username = pref.getString("username", "");

        // Casting
        editName = (TextView) findViewById(R.id.editName);
        editDob = (TextView) findViewById(R.id.editDob);
        editPhone = (TextView) findViewById(R.id.editPhone);
        editMobile = (TextView) findViewById(R.id.editMobile);
        editAdd1 = (TextView) findViewById(R.id.editAdd1);
        editAdd2 = (TextView) findViewById(R.id.editAdd2);
        editSuburb = (TextView) findViewById(R.id.editSuburb);
        editCity = (TextView) findViewById(R.id.editCity);
        editCountry = (TextView) findViewById(R.id.editCountry);
        rdioMale = (RadioButton) findViewById(R.id.rdioMale);
        rdioFemale = (RadioButton) findViewById(R.id.rdioFemale);

        // Adding the textviews to array list
        fields.add(editName);
        fields.add(editDob);
        fields.add(editPhone);
        fields.add(editMobile);
        fields.add(editAdd1);
        fields.add(editAdd2);
        fields.add(editSuburb);
        fields.add(editCity);
        fields.add(editCountry);
        fields.add(rdioMale);
        fields.add(rdioFemale);

        // Set the fields to uneditable
        fh.setEditableFalse(fields);

        // Load the Propert Manager's information from the database
        LoadPropertyManagerInformation lpmi = new LoadPropertyManagerInformation(this, userID, rdioMale, rdioFemale, editName, editDob,
                editPhone, editMobile, editAdd1, editAdd2, editSuburb, editCity, editCountry);
        lpmi.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_MANAGER_INFORMATION);
    }

    // Storing data's into JSON Object
    public String getLandlordJsonObject() {
        propertyManagerInfo = new JSONObject();
        try {
            propertyManagerInfo.put("name", editName.getText().toString());
            propertyManagerInfo.put("dob", editDob.getText().toString());
            propertyManagerInfo.put("phone", editPhone.getText().toString());
            propertyManagerInfo.put("mobile", editMobile.getText().toString());
            propertyManagerInfo.put("add1", editAdd1.getText().toString());
            propertyManagerInfo.put("add2", editAdd2.getText().toString());
            propertyManagerInfo.put("suburb", editSuburb.getText().toString());
            propertyManagerInfo.put("city", editCity.getText().toString());
            propertyManagerInfo.put("country", editCountry.getText().toString());
            if (rdioMale.isChecked()) {
                propertyManagerInfo.put("gender", "Male");
            } else if (rdioFemale.isChecked()) {
                propertyManagerInfo.put("gender", "Female");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyManagerInfo.toString();
    }

    // Setting up an action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_personal_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // edit personal information
        if (id == R.id.editPersonalInfo) {
            fh.setEditableTrue(fields);
            for (TextView field : fields) {
                field.setTextColor(Color.BLACK);
            }
        }
        // save personal information
        if (id == R.id.savePersonalInfo) {
            UpdatePropertyManagerInformation upmi = new UpdatePropertyManagerInformation(this, userID, username);
            upmi.execute(Settings.URL_ADDRESS_UPDATE_PROPERTY_MANAGER_INFORMATION);
            fh.setEditableFalse(fields);
            for (TextView field : fields) {
                field.setTextColor(Color.GRAY);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
