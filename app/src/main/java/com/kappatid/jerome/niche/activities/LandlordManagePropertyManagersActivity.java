package com.kappatid.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.LoadPropertyManagerList;
import com.kappatid.jerome.niche.dao.ValidatePropertyManagerList;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title LandlordManagePropertyManagersActivity.java
 * @purpose This class is to manage the property managers
 * @date November 03, 2016
 * @input manager's username
 * @processing ValidatePropertyManagerList.java
 * @output Manager added
 */

public class LandlordManagePropertyManagersActivity extends AppCompatActivity implements ValidatePropertyManagerList.AsyncResponse, LoadPropertyManagerList.AsyncResponse {

    // String variable to store the manager's username
    private String managerUsername;
    // String variable to store the userID
    private String userID;
    // Listview for manager's list
    private ListView managerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_manage_property_managers);
        setTitle("Manage Property Managers");

        // Casting
        managerListView = (ListView) findViewById(R.id.managersListView);

        // Store the userID from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");

        // Array list of manager's name
        ArrayList<String> managerName = new ArrayList<>();

        // Load the manager's list from the database
        LoadPropertyManagerList lpmi = new LoadPropertyManagerList(this, this, managerName, userID);
        lpmi.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_MANAGER_LIST);

    }

    // Setting up the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_manage_property_manager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Adding the manager
        if (id == R.id.addManager) {
            // Alert Dialog Builder to take a user input
            AlertDialog.Builder db = new AlertDialog.Builder(this);
            db.setTitle("Add Manager");
            db.setIcon(R.drawable.ic_add_property_manager);
            db.setMessage("Please input username of property manager");
            final EditText tvManagerUsername = new EditText(this);
            tvManagerUsername.setInputType(InputType.TYPE_CLASS_TEXT);
            db.setView(tvManagerUsername);
            db.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    managerUsername = tvManagerUsername.getText().toString();
                    // Validate the username given by user
                    ValidatePropertyManagerList vpml = new ValidatePropertyManagerList(LandlordManagePropertyManagersActivity.this, LandlordManagePropertyManagersActivity.this, managerUsername, userID);
                    vpml.execute(Settings.URL_ADDRESS_VALIDATE_PROPERTY_MANAGER_LIST);
                }
            });
            db.setNegativeButton(android.R.string.no, null);
            db.show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param response a response from the database if username already
     *                 exist, username doesn't exist or not a property manager
     */
    @Override
    public void processFinish(String response) {
        if (response.equals("already exist")) {
            Toast.makeText(LandlordManagePropertyManagersActivity.this, "It's already an existing record", Toast.LENGTH_LONG).show();
        } else if (response.equals("Not Found")) {
            Toast.makeText(LandlordManagePropertyManagersActivity.this, "Username doesn't exist or Not a Property Manager", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LandlordManagePropertyManagersActivity.this, "Manager added successfully", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param managerName arraylist of manager's name
     */
    @Override
    public void processFinish(ArrayList<String> managerName) {
        ArrayAdapter<String> managerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                managerName);

        managerListView.setAdapter(managerAdapter);
    }
}
