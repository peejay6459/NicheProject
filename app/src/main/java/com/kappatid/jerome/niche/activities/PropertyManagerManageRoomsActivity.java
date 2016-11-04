package com.kappatid.jerome.niche.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.RoomCustomAdapter;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.LoadRooms;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title PropertyManagerManageRoomsActivity.java
 * @purpose This class is to Manage the rooms by a property manager
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class PropertyManagerManageRoomsActivity extends AppCompatActivity implements LoadRooms.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager_manage_rooms);
        setTitle("Manage Rooms");

        // Store the username and user type from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        String username = pref.getString("username", "");
        String userType = pref.getString("userType", "");

        // Creating an array list for room Price
        ArrayList<String> roomPrice = new ArrayList<>();
        // Creating an array list for room occupancy
        ArrayList<String> roomOccupancy = new ArrayList<>();

        // Load the room details
        LoadRooms lr = new LoadRooms(this, this, roomPrice, roomOccupancy, "", userType, username);
        lr.execute(Settings.URL_ADDRESS_LOAD_ROOMS);
    }

    // Setting up an action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_propertymanager_manageroom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Add room
        if (id == R.id.addRoom) {
            Intent goCreateRoom = new Intent(PropertyManagerManageRoomsActivity.this, LandlordCreateRoomActivity.class);
            PropertyManagerManageRoomsActivity.this.startActivity(goCreateRoom);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ArrayList<String> price, ArrayList<String> occupancy) {
        // Setting an array adapter to rooms
        ArrayAdapter roomAdapter = new RoomCustomAdapter(this, price, occupancy);
        ListView roomListView = (ListView) findViewById(R.id.managersManageListView);
        roomListView.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();

        // Setting an onclick listener for each rooms in list view
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Store the room position to Shared Preference
                SharedPreferences.Editor editor = getSharedPreferences("ROOM_NUM", MODE_PRIVATE).edit();
                editor.putInt("roomPos", position);
                editor.apply();

                Intent goEditRoom = new Intent(PropertyManagerManageRoomsActivity.this, LandlordEditRoomActivity.class);
                PropertyManagerManageRoomsActivity.this.startActivity(goEditRoom);
            }
        });
    }
}
