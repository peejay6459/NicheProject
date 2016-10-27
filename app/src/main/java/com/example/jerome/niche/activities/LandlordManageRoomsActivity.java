package com.example.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jerome.niche.R;

/**
 * Created by Jerome on 27/10/2016.
 */

public class LandlordManageRoomsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_manage_rooms);
        setTitle("Manage Rooms");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_manage_room, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.editProperty){
            Intent goEditProperty = new Intent(LandlordManageRoomsActivity.this, LandlordEditPropertyActivity.class);
            LandlordManageRoomsActivity.this.startActivity(goEditProperty);
        }

        return super.onOptionsItemSelected(item);
    }
}
