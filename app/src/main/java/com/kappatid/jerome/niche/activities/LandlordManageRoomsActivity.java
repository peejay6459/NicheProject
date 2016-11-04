package com.kappatid.jerome.niche.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.RoomCustomAdapter;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.LoadPropertyManagerList;
import com.kappatid.jerome.niche.dao.LoadRooms;
import com.kappatid.jerome.niche.dao.UpdateAssignedPropertyManager;
import com.kappatid.jerome.niche.dao.ValidatePropertyDetails;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title LandlordManageRoomsActivity.java
 * @purpose This class is to manage rooms
 * @date November 03, 2016
 * @input Property Manager
 * @processing UpdateAssignedPropertyManager.java
 * @output New Property Manager assigned
 */

public class LandlordManageRoomsActivity extends AppCompatActivity implements ValidatePropertyDetails.AsyncResponse, LoadRooms.AsyncResponse, LoadPropertyManagerList.AsyncResponse {

    // String variable to store the userID
    private String userID;
    // String variable to store the userType
    private String userType;
    // String variable to store the username
    private String username;
    // String variable to store the address
    private String address;
    // Int variable to store the row number
    private int rowNum;
    // Creating a dialog builder
    private AlertDialog.Builder chooseManager;
    // Creating a textview for Property Manager Name
    private TextView propertyManagerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_manage_rooms);
        setTitle("Manage Rooms");

        // Store the userID, row umber, userType, username from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");
        rowNum = pref.getInt("rowNum", 0);
        userType = pref.getString("userType", "");
        username = pref.getString("username", "");

        // Casting
        propertyManagerName = (TextView) findViewById(R.id.propertyManagerName);

        // Load the property details from the database
        ValidatePropertyDetails vpd = new ValidatePropertyDetails(this, rowNum, userID, this, propertyManagerName);
        vpd.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_INFO, Settings.URL_ADDRESS_LOAD_ROOM_ASSIGNED_MANAGER);

    }

    // Setting up the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_manage_room, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Adding the manager
        if (id == R.id.addManager) {
            chooseManager = new AlertDialog.Builder(this);
            chooseManager.setIcon(R.drawable.ic_add_property_manager);
            chooseManager.setTitle("Choose Property Manager");
            ArrayList<String> managerName = new ArrayList<>();

            // Load the property manager list
            LoadPropertyManagerList lpml = new LoadPropertyManagerList(this, this, managerName, userID);
            lpml.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_MANAGER_LIST);

        }
        // Adding the room
        if (id == R.id.addRoom) {
            Intent goCreateRoom = new Intent(LandlordManageRoomsActivity.this, LandlordCreateRoomActivity.class);
            LandlordManageRoomsActivity.this.startActivity(goCreateRoom);
        }
        // Edit the property
        if (id == R.id.editProperty) {
            Intent goEditProperty = new Intent(LandlordManageRoomsActivity.this, LandlordEditPropertyActivity.class);
            LandlordManageRoomsActivity.this.startActivity(goEditProperty);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param address arraylist of address of a room
     */
    @Override
    public void processFinish(String... address) {
        // Store the address 1, address 2 to Shared Preference
        SharedPreferences.Editor editor = getSharedPreferences("propertyAddress", MODE_PRIVATE).edit();
        editor.putString("propertyAddress1", address[0]);
        editor.putString("propertyAddress2", address[1]);
        editor.apply();

        ArrayList<String> roomPrice = new ArrayList<>();
        ArrayList<String> roomOccupancy = new ArrayList<>();

        // Load the rooms through middle tier
        LoadRooms lr = new LoadRooms(this, this, roomPrice, roomOccupancy, address[0], userType, username);
        lr.execute(Settings.URL_ADDRESS_LOAD_ROOMS);
    }

    /**
     * @param price     arraylist price of the rooms
     * @param occupancy arraylist occupancy of the rooms
     */
    @Override
    public void processFinish(ArrayList<String> price, ArrayList<String> occupancy) {
        // Setting the Array adapter for rooms
        ArrayAdapter roomAdapter = new RoomCustomAdapter(this, price, occupancy);
        ListView roomListView = (ListView) findViewById(R.id.roomListView);
        roomListView.setAdapter(roomAdapter);

        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences("ROOM_NUM", MODE_PRIVATE).edit();
                editor.putInt("roomPos", position);
                editor.apply();

                Intent goEditRoom = new Intent(LandlordManageRoomsActivity.this, LandlordEditRoomActivity.class);
                LandlordManageRoomsActivity.this.startActivity(goEditRoom);
            }
        });
    }

    /**
     * @param managerName arraylist of manager's name
     */
    @Override
    public void processFinish(ArrayList<String> managerName) {
        final ArrayAdapter<String> managerNames = new ArrayAdapter<>(this,
                android.R.layout.select_dialog_singlechoice, managerName);

        chooseManager.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        chooseManager.setAdapter(managerNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = managerNames.getItem(which);
                propertyManagerName.setText(name);
                SharedPreferences pref = getSharedPreferences("propertyAddress", MODE_PRIVATE);
                address = pref.getString("propertyAddress1", "");

                UpdateAssignedPropertyManager uapm = new UpdateAssignedPropertyManager(LandlordManageRoomsActivity.this, name, address);
                uapm.execute(Settings.URL_ADDRESS_UPDATE_ASSIGNED_PROPERTY_MANAGER);
            }
        });
        chooseManager.show();

    }
}
