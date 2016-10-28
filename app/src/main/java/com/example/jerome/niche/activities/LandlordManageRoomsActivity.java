package com.example.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.LoadPropertyInfo;
import com.example.jerome.niche.dao.ValidatePropertyDetails;

import java.util.ArrayList;

/**
 * Created by Jerome on 27/10/2016.
 */

public class LandlordManageRoomsActivity extends AppCompatActivity implements ValidatePropertyDetails.AsyncResponse {

    String userID;
    int rowNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_manage_rooms);
        setTitle("Manage Rooms");


        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");
        rowNum = pref.getInt("rowNum", 0);

        ValidatePropertyDetails vpd = new ValidatePropertyDetails(this, rowNum, userID, this);
        vpd.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_INFO);

        ArrayList<String> roomPrice = new ArrayList<>();
        ArrayList<String> tenantCount = new ArrayList<>();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_manage_room, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addRoom){
            Intent goCreateRoom = new Intent(LandlordManageRoomsActivity.this, LandlordCreateRoomActivity.class);
            LandlordManageRoomsActivity.this.startActivity(goCreateRoom);
        }
        if(id == R.id.editProperty){
            Intent goEditProperty = new Intent(LandlordManageRoomsActivity.this, LandlordEditPropertyActivity.class);
            LandlordManageRoomsActivity.this.startActivity(goEditProperty);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String... address) {
        SharedPreferences.Editor editor = getSharedPreferences("propertyAddress", MODE_PRIVATE).edit();
        editor.putString("propertyAddress1", address[0]);
        editor.putString("propertyAddress2", address[1]);
        Log.d("address1 ", address[0]);
        Log.d("address2 ", address[1]);
        editor.apply();
    }
}
