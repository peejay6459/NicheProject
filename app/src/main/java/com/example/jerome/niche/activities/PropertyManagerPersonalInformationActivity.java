package com.example.jerome.niche.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.LoadPropertyManagerInformation;
import com.example.jerome.niche.dao.UpdateLandlordInformation;
import com.example.jerome.niche.dao.UpdatePropertyManagerInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PropertyManagerPersonalInformationActivity extends AppCompatActivity {
    private TextView editName;
    private TextView editDob;
    private TextView editPhone;
    private TextView editMobile;
    private TextView editAdd1;
    private TextView editAdd2;
    private TextView editSuburb;
    private TextView editCity;
    private TextView editCountry;
    private RadioButton rdioMale;
    private RadioButton rdioFemale;
    private ArrayList<TextView> fields = new ArrayList<>();
    private FieldHelper fh = new FieldHelper();
    private JSONObject propertyManagerInfo;
    private String userID;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager_personal_information);
        setTitle("Personal Information");

        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");
        username = pref.getString("username", "");

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

        fh.setEditableFalse(fields);

        LoadPropertyManagerInformation lpmi = new LoadPropertyManagerInformation(this, userID, rdioMale, rdioFemale, editName, editDob,
                editPhone, editMobile, editAdd1, editAdd2, editSuburb, editCity, editCountry);
        lpmi.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_MANAGER_INFORMATION);
    }

    public String getLandlordJsonObject(){
        propertyManagerInfo = new JSONObject();
        try{
            propertyManagerInfo.put("name", editName.getText().toString());
            propertyManagerInfo.put("dob", editDob.getText().toString());
            propertyManagerInfo.put("phone", editPhone.getText().toString());
            propertyManagerInfo.put("mobile", editMobile.getText().toString());
            propertyManagerInfo.put("add1", editAdd1.getText().toString());
            propertyManagerInfo.put("add2", editAdd2.getText().toString());
            propertyManagerInfo.put("suburb", editSuburb.getText().toString());
            propertyManagerInfo.put("city", editCity.getText().toString());
            propertyManagerInfo.put("country", editCountry.getText().toString());
            if(rdioMale.isChecked()){
                propertyManagerInfo.put("gender", "Male");
            }else if(rdioFemale.isChecked()){
                propertyManagerInfo.put("gender", "Female");
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return propertyManagerInfo.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_personal_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.editPersonalInfo){
            fh.setEditableTrue(fields);
            for(TextView field : fields){
                field.setTextColor(Color.BLACK);
            }
        }
        if(id == R.id.savePersonalInfo){
            UpdatePropertyManagerInformation upmi = new UpdatePropertyManagerInformation(this, userID, username);
            upmi.execute(Settings.URL_ADDRESS_UPDATE_PROPERTY_MANAGER_INFORMATION);
            fh.setEditableFalse(fields);
            for(TextView field : fields){
                field.setTextColor(Color.GRAY);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
