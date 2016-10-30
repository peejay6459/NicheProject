package com.example.jerome.niche.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.InsertProperty;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jerome on 25/10/2016.
 */

public class LandlordCreatePropertyActivity extends AppCompatActivity implements InsertProperty.AsyncResponse{
    private TextView tvPropertyTitle;
    private TextView tvFlatStreet;
    private TextView tvSuburb;
    private Button btnAddProperty;
    private JSONObject propertyObject;
    private Spinner spinPropertyManager;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_create_property);
        setTitle("Create Property");

        FieldHelper fh = new FieldHelper();

        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");

        tvPropertyTitle = (TextView) findViewById(R.id.tvPropertyTitle);
        tvFlatStreet = (TextView) findViewById(R.id.tvFlatStreet);
        tvSuburb = (TextView) findViewById(R.id.tvSuburb);
        btnAddProperty = (Button) findViewById(R.id.btnAddProperty);
        spinPropertyManager = (Spinner) findViewById(R.id.spinPropertyManager);


        fh.changeTextField(tvPropertyTitle, tvFlatStreet, tvSuburb, "# Bedrooms # Bathrooms", "Flat # and Street", "Suburb and City");
        fh.changeTextField(tvSuburb, tvPropertyTitle, tvFlatStreet, "Suburb and City", "# Bedrooms # Bathrooms", "Flat # and Street");
        fh.changeTextField(tvFlatStreet, tvSuburb, tvPropertyTitle, "Flat # and Street", "Suburb and City", "# Bedrooms # Bathrooms");


        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields(tvPropertyTitle, tvFlatStreet, tvSuburb)){
                    Log.d("JSON create property", getPropertyJsonObject());
                    InsertProperty ip = new InsertProperty(LandlordCreatePropertyActivity.this, LandlordCreatePropertyActivity.this);
                    ip.execute(Settings.URL_ADDRESS_INSERT_PROPERTIES);
                }
            }
        });
    }

    public String getPropertyJsonObject(){
        try{
            propertyObject = new JSONObject();
            propertyObject.put("userID", userID);
            propertyObject.put("propertyTitle", tvPropertyTitle.getText().toString());
            propertyObject.put("flatStreet", tvFlatStreet.getText().toString());
            propertyObject.put("suburb", tvSuburb.getText().toString());
            propertyObject.put("propertyManager", String.valueOf(spinPropertyManager.getSelectedItem()));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return propertyObject.toString();
    }

    public boolean validateFields(TextView... tv){
        boolean isValidated = false;
        if(!(tv[0].getText().toString().isEmpty())
                &&!(tv[0].getText().toString().equals("# Bedrooms # Bathrooms"))
                &&!(tv[1].getText().toString().isEmpty())
                &&!(tv[1].getText().toString().equals("Flat # and Street"))
                &&!(tv[2].getText().toString().isEmpty())
                &&!(tv[2].getText().toString().equals("Suburb and City"))){
            if(!(tv[1].getText().toString().contains(",")) && !(tv[2].getText().toString().contains(","))) {
                isValidated = true;
            }else{
                Toast.makeText(LandlordCreatePropertyActivity.this, "Please remove the comma (,)", Toast.LENGTH_SHORT).show();
                isValidated = false;
            }
        }else{
            Toast.makeText(LandlordCreatePropertyActivity.this, "Please fill out the required forms", Toast.LENGTH_SHORT).show();
        }
        return isValidated;
    }

    @Override
    public void processFinish(String response) {
        if(response.equals("already exist")){
            Toast.makeText(LandlordCreatePropertyActivity.this, "Address already exist", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(LandlordCreatePropertyActivity.this, "Property Created Successfully", Toast.LENGTH_SHORT).show();
            Intent goBackManageProperties = new Intent(LandlordCreatePropertyActivity.this, LandlordManagePropertiesActivity.class);
            LandlordCreatePropertyActivity.this.startActivity(goBackManageProperties);
        }
    }
}