package com.example.jerome.niche.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.RoomCustomAdapter;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.LoadRooms;

import java.util.ArrayList;

public class PropertyManagerManageRoomsActivity extends AppCompatActivity implements LoadRooms.AsyncResponse {
    private String userID;
    private String username;
    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager_manage_rooms);
        setTitle("Manage Rooms");

        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");
        username = pref.getString("username", "");
        userType = pref.getString("userType", "");

        ArrayList<String> roomPrice = new ArrayList<>();
        ArrayList<String> roomOccupancy = new ArrayList<>();

        LoadRooms lr = new LoadRooms(this, this, roomPrice, roomOccupancy, "", userType, username);
        lr.execute(Settings.URL_ADDRESS_LOAD_ROOMS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_propertymanager_manageroom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addRoom){
            Intent goCreateRoom = new Intent(PropertyManagerManageRoomsActivity.this, LandlordCreateRoomActivity.class);
            PropertyManagerManageRoomsActivity.this.startActivity(goCreateRoom);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ArrayList<String> price, ArrayList<String> occupancy) {
        ArrayAdapter roomAdapter = new RoomCustomAdapter(this, price, occupancy);
        ListView roomListView = (ListView) findViewById(R.id.managersManageListView);
        roomListView.setAdapter(roomAdapter);

        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences("ROOM_NUM", MODE_PRIVATE).edit();
                editor.putInt("roomPos", position);
                editor.apply();

                Intent goEditRoom = new Intent(PropertyManagerManageRoomsActivity.this, LandlordEditRoomActivity.class);
                PropertyManagerManageRoomsActivity.this.startActivity(goEditRoom);
            }
        });
    }
}
