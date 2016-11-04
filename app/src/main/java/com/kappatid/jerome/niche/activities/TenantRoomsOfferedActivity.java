package com.kappatid.jerome.niche.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.RoomCustomAdapter;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.LoadRoomsOffered;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title TenantRoomsOfferedActivity.java
 * @purpose This class is for rooms that are offered to Tenant
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class TenantRoomsOfferedActivity extends AppCompatActivity implements LoadRoomsOffered.AsyncResponse {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_rooms_offered);
        setTitle("Rooms Offered");

        // Store the username from Shared Preference
        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        String username = pref.getString("username", "");

        // Array list for room price
        ArrayList<String> roomPrice = new ArrayList<>();
        // Array list for room occupancy
        ArrayList<String> roomOccupancy = new ArrayList<>();

        // Load Offered Rooms
        LoadRoomsOffered lro = new LoadRoomsOffered(this, this, username, roomPrice, roomOccupancy);
        lro.execute(Settings.URL_ADDRESS_LOAD_ROOMS_OFFERED);

    }

    @Override
    public void processFinish(ArrayList<String> price, ArrayList<String> occupancy, String roomOfferID) {
        // Store the room offer ID to Shared Preference
        SharedPreferences.Editor editor = getSharedPreferences("ROOM_ID", MODE_PRIVATE).edit();
        editor.putString("roomOfferID", roomOfferID);
        editor.apply();

        // Setting up an array adapter
        ArrayAdapter roomAdapter = new RoomCustomAdapter(this, price, occupancy);
        ListView roomListView = (ListView) findViewById(R.id.roomsOfferedListView);
        roomListView.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();

        // Setting up an onclick listener to each rooms in list view
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Store the room position to Shared Preference
                SharedPreferences.Editor editor = getSharedPreferences("ROOM_NUM", MODE_PRIVATE).edit();
                editor.putInt("roomPos", position);
                editor.apply();

                Intent goViewRoom = new Intent(TenantRoomsOfferedActivity.this, TenantRoomsOfferedInformationActivity.class);
                TenantRoomsOfferedActivity.this.startActivity(goViewRoom);
            }
        });
    }
}
