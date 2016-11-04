package com.kappatid.jerome.niche.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.PropertiesCustomAdapter;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.LoadProperties;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title LandlordManagePropertiesActivity.java
 * @purpose This class is for managing the properties
 * @date November 03, 2016
 * @input N/A
 * @processing LoadProperties.java
 * @output Load the listview of properties
 */
public class LandlordManagePropertiesActivity extends AppCompatActivity implements LoadProperties.AsyncResponse {

    // String variable to store the userID
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_manage_properties);
        setTitle("Manage Properties");

        // Store the userID from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");

        // arraylist of address1
        ArrayList<String> address1 = new ArrayList<>();
        // arraylist of address2
        ArrayList<String> address2 = new ArrayList<>();

        // Load the properties from the database
        LoadProperties lp = new LoadProperties(this, this, address2, address1, userID);
        lp.execute(Settings.URL_ADDRESS_LOAD_PROPERTIES);

        // execute the processFinish
        processFinish(address2, address1);

    }

    // Setting up the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_manage_property, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Adding the property
        if (id == R.id.addProperty) {
            Intent goCreateProperty = new Intent(LandlordManagePropertiesActivity.this, LandlordCreatePropertyActivity.class);
            this.startActivity(goCreateProperty);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param address1 array information of address 1
     * @param address2 array information of address 2
     */
    @Override
    public void processFinish(final ArrayList<String> address1, ArrayList<String> address2) {
        // Creating an array adapter
        ArrayAdapter propertyAdapter = new PropertiesCustomAdapter(this, address1, address2);
        ListView propertyListView = (ListView) findViewById(R.id.propertyListView);
        propertyListView.setAdapter(propertyAdapter);
        propertyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Store the row number to Shared Preference
                SharedPreferences.Editor editor = getSharedPreferences("USER_ID", MODE_PRIVATE).edit();
                editor.putInt("rowNum", position);
                editor.apply();

                // go to LandlordManageRoomsActivity.class
                Intent goManageRooms = new Intent(LandlordManagePropertiesActivity.this, LandlordManageRoomsActivity.class);
                LandlordManagePropertiesActivity.this.startActivity(goManageRooms);
            }
        });
    }
}
