package com.kappatid.jerome.niche.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kappatid.jerome.niche.classes.FieldHelper;
import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.UpdateTenantInformation;
import com.kappatid.jerome.niche.dao.LoadTenantInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * @author Kappatid LLC
 * @title TenantPersonalInformationActivity.java
 * @purpose This class is for Tenant's Personal Information
 * @date November 03, 2016
 * @input name, date of birth, phone, mobile, address, country, gender,
 * passport, ID, previous country, relative name, relative
 * relationship, relative address, relative contact number
 * @processing LoadTenantInformation.java
 * @output Updated Tenant's Information
 */

public class TenantPersonalInformationActivity extends AppCompatActivity {
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
    // Creating a textview for editPassport
    private TextView editPassport;
    // Creating a textview for editIdNum
    private TextView editIdNum;
    // Creating a textview for editPrevCountry
    private TextView editPrevCountry;
    // Creating a textview for editRelName
    private TextView editRelName;
    // Creating a textview for editRelRelationship
    private TextView editRelRelationship;
    // Creating a textview for editRelAdd1
    private TextView editRelAdd1;
    // Creating a textview for editRelAdd2
    private TextView editRelAdd2;
    // Creating a textview for editRelSuburb
    private TextView editRelSuburb;
    // Creating a textview for editRelCity
    private TextView editRelCity;
    // Creating a textview for editRelPhoneMobile
    private TextView editRelPhoneMobile;
    // Creating an object of Field Helper
    private FieldHelper fh = new FieldHelper();
    // Creating an arraylist of fields
    private ArrayList<TextView> fields = new ArrayList<>();
    // Creating JSON Object
    private JSONObject tenantInfo;
    // String variable to store the userID
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);
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
        editPassport = (TextView) findViewById(R.id.editPassport);
        editIdNum = (TextView) findViewById(R.id.editIdNum);
        editPrevCountry = (TextView) findViewById(R.id.editPrevCountry);
        editRelName = (TextView) findViewById(R.id.editRelName);
        editRelRelationship = (TextView) findViewById(R.id.editRelRelationship);
        editRelAdd1 = (TextView) findViewById(R.id.editRelAdd1);
        editRelAdd2 = (TextView) findViewById(R.id.editRelAdd2);
        editRelSuburb = (TextView) findViewById(R.id.editRelSuburb);
        editRelCity = (TextView) findViewById(R.id.editRelCity);
        editRelPhoneMobile = (TextView) findViewById(R.id.editRelPhoneMobile);
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
        fields.add(editPassport);
        fields.add(editIdNum);
        fields.add(editPrevCountry);
        fields.add(editRelName);
        fields.add(editRelRelationship);
        fields.add(editRelAdd1);
        fields.add(editRelAdd2);
        fields.add(editRelSuburb);
        fields.add(editRelCity);
        fields.add(editRelPhoneMobile);
        fields.add(rdioMale);
        fields.add(rdioFemale);

        // Set the fields to uneditable
        for (TextView field : fields) {
            field.setEnabled(false);
        }

        // Set the radio buttons to uneditable
        rdioMale.setClickable(false);
        rdioFemale.setClickable(false);

        // Load the Tenant Information from the database
        LoadTenantInformation lti = new LoadTenantInformation(this, userID, rdioMale, rdioFemale,
                editName, editDob, editPhone, editMobile, editAdd1, editAdd2, editSuburb, editCity, editCountry, editPassport,
                editIdNum, editPrevCountry, editRelName, editRelRelationship, editRelAdd1, editRelAdd2, editRelSuburb, editRelCity,
                editRelPhoneMobile);
        lti.execute(Settings.URL_ADDRESS_LOAD_TENANT_INFORMATION);
    }

    // Storing data's into JSON Object
    public String getTenantJsonObject() {
        try {
            tenantInfo = new JSONObject();
            tenantInfo.put("name", editName.getText().toString());
            if (rdioMale.isChecked()) {
                tenantInfo.put("gender", "Male");
            } else if (rdioFemale.isChecked()) {
                tenantInfo.put("gender", "Female");
            }
            tenantInfo.put("dob", editDob.getText().toString());
            tenantInfo.put("phone", editPassport.getText().toString());
            tenantInfo.put("mobile", editMobile.getText().toString());
            tenantInfo.put("add1", editAdd1.getText().toString());
            tenantInfo.put("add2", editAdd2.getText().toString());
            tenantInfo.put("suburb", editSuburb.getText().toString());
            tenantInfo.put("city", editCity.getText().toString());
            tenantInfo.put("country", editCountry.getText().toString());
            tenantInfo.put("passport", editPassport.getText().toString());
            tenantInfo.put("idNum", editIdNum.getText().toString());
            tenantInfo.put("previousCountry", editPrevCountry.getText().toString());
            tenantInfo.put("relName", editRelName.getText().toString());
            tenantInfo.put("relRelationship", editRelRelationship.getText().toString());
            tenantInfo.put("relAdd1", editRelAdd1.getText().toString());
            tenantInfo.put("relAdd2", editRelAdd2.getText().toString());
            tenantInfo.put("relSuburb", editRelSuburb.getText().toString());
            tenantInfo.put("relCity", editRelCity.getText().toString());
            tenantInfo.put("relPhone", editRelPhoneMobile.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tenantInfo.toString();
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
        // Edit Personal Information
        if (id == R.id.editPersonalInfo) {
            fh.setEditableTrue(fields);
            for (TextView field : fields) {
                field.setTextColor(Color.BLACK);
            }
            rdioMale.setClickable(true);
            rdioFemale.setClickable(true);
            // Save Personal Information
        } else if (id == R.id.savePersonalInfo) {
            fh.setEditableFalse(fields);
            for (TextView field : fields) {
                field.setTextColor(Color.GRAY);
            }
            rdioMale.setClickable(false);
            rdioFemale.setClickable(false);
            UpdateTenantInformation uti = new UpdateTenantInformation(this, userID);
            uti.execute(Settings.URL_ADDRESS_UPDATE_TENANT_INFORMATION);

        }
        return super.onOptionsItemSelected(item);
    }
}
