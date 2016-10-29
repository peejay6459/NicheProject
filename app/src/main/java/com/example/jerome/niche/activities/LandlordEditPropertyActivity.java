package com.example.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.DeleteProperty;
import com.example.jerome.niche.dao.LoadPropertyInformation;
import com.example.jerome.niche.dao.UpdatePropertyInformation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jerome on 27/10/2016.
 */

public class LandlordEditPropertyActivity extends AppCompatActivity {
    TextView tvEditPropertyTitle;
    TextView tvEditFlatStreet;
    TextView tvEditSuburb;
    Spinner spinEditPropertyManager;
    int rowNum;
    String userID;
    JSONObject propInfoJsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_edit_property);
        setTitle("Edit Property");

        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        rowNum = pref.getInt("rowNum", 0);
        userID = pref.getString("userID", "");

        tvEditPropertyTitle = (TextView) findViewById(R.id.tvEditPropertyTitle);
        tvEditFlatStreet = (TextView) findViewById(R.id.tvEditFlatStreet);
        tvEditSuburb = (TextView) findViewById(R.id.tvEditSuburb);
        spinEditPropertyManager = (Spinner) findViewById(R.id.spinEditPropertyManager);

        tvEditPropertyTitle.setEnabled(false);
        tvEditFlatStreet.setEnabled(false);
        tvEditSuburb.setEnabled(false);
        spinEditPropertyManager.setEnabled(false);

        LoadPropertyInformation lpi = new LoadPropertyInformation(this, rowNum, userID, tvEditPropertyTitle, tvEditFlatStreet, tvEditSuburb);
        lpi.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_INFO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_edit_property, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.editPropertyInfo){
            tvEditPropertyTitle.setEnabled(true);
            tvEditFlatStreet.setEnabled(true);
            tvEditSuburb.setEnabled(true);
            spinEditPropertyManager.setEnabled(true);
        }
        if(id == R.id.savePropertyInfo){
            // do something
            UpdatePropertyInformation upi = new UpdatePropertyInformation(userID, LandlordEditPropertyActivity.this);
            upi.execute(Settings.URL_ADDRESS_UPDATE_PROPERTY_INFO);
            tvEditPropertyTitle.setEnabled(false);
            tvEditFlatStreet.setEnabled(false);
            tvEditSuburb.setEnabled(false);
            spinEditPropertyManager.setEnabled(false);

        }
        if(id == R.id.deleteProperty){
            new AlertDialog.Builder(this)
                    .setTitle("Delete Property")
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

    public String propertyInfoJsonObject(){
        try {
            propInfoJsonObject = new JSONObject();
            propInfoJsonObject.put("propertyTitle", tvEditPropertyTitle.getText().toString());
            propInfoJsonObject.put("flatStreet", tvEditFlatStreet.getText().toString());
            propInfoJsonObject.put("suburb", tvEditSuburb.getText().toString());
            propInfoJsonObject.put("spinPropertyManager", spinEditPropertyManager.getSelectedItem().toString());
        }catch(JSONException e){
            e.printStackTrace();
        }
        return propInfoJsonObject.toString();
    }

}
