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
import com.kappatid.jerome.niche.dao.LoadLandlordInformation;
import com.kappatid.jerome.niche.dao.UpdateLandlordInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title LandlordPersonalInformationActivity.java
 * @purpose This class is for Personal Information of a Landlord
 * @date November 03, 2016
 * @input name, date of birth, phone, mobile, address, country, gender
 * @processing UpdateLandlordInformation.java
 * @output Updated the Landlord Information
 */

public class LandlordPersonalInformationActivity extends AppCompatActivity {
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
    private JSONObject landlordInfo;
    // String variable to store the userID
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_personal_information);
        setTitle("Personal Information");

        // Store the userID from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");

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

        // Load the Landlord information from the database
        LoadLandlordInformation lli = new LoadLandlordInformation(this, userID, rdioMale, rdioFemale, editName, editDob,
                editPhone, editMobile, editAdd1, editAdd2, editSuburb, editCity, editCountry);
        lli.execute(Settings.URL_ADDRESS_LOAD_LANDLORD_INFORMATION);

    }

    // Storing data's into JSON Object
    public String getLandlordJsonObject() {
        landlordInfo = new JSONObject();
        try {
            landlordInfo.put("name", editName.getText().toString());
            landlordInfo.put("dob", editDob.getText().toString());
            landlordInfo.put("phone", editPhone.getText().toString());
            landlordInfo.put("mobile", editMobile.getText().toString());
            landlordInfo.put("add1", editAdd1.getText().toString());
            landlordInfo.put("add2", editAdd2.getText().toString());
            landlordInfo.put("suburb", editSuburb.getText().toString());
            landlordInfo.put("city", editCity.getText().toString());
            landlordInfo.put("country", editCountry.getText().toString());
            if (rdioMale.isChecked()) {
                landlordInfo.put("gender", "Male");
            } else if (rdioFemale.isChecked()) {
                landlordInfo.put("gender", "Female");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return landlordInfo.toString();
    }

    // Setting up the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_personal_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Edit the personal information
        if (id == R.id.editPersonalInfo) {
            fh.setEditableTrue(fields);
            for (TextView field : fields) {
                field.setTextColor(Color.BLACK);
            }
        }
        // save the personal information
        if (id == R.id.savePersonalInfo) {
            UpdateLandlordInformation uli = new UpdateLandlordInformation(this, userID);
            uli.execute(Settings.URL_ADDRESS_UPDATE_LANDLORD_INFORMATION);
            fh.setEditableFalse(fields);
            for (TextView field : fields) {
                field.setTextColor(Color.GRAY);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
