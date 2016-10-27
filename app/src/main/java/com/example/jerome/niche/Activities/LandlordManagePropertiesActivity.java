package com.example.jerome.niche.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.CustomerAdapter;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.LoadProperties;

import java.util.ArrayList;

public class LandlordManagePropertiesActivity extends AppCompatActivity implements LoadProperties.AsyncResponse{

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_manage_properties);
        setTitle("Manage Properties");

        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");

        ArrayList<String> address1 = new ArrayList<>();
        ArrayList<String> address2 = new ArrayList<>();
        //ArrayList<String> propertyManger = new ArrayList<>();

        LoadProperties lp = new LoadProperties(this, this, address2, address1, userID);
        lp.execute(Settings.URL_ADDRESS_LOAD_PROPERTIES);

        processFinish(address2, address1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_manage_property, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addProperty){
            Intent goCreateRoom = new Intent(LandlordManagePropertiesActivity.this, LandlordCreateRoomActivity.class);
            this.startActivity(goCreateRoom);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ArrayList<String> address1, ArrayList<String> address2) {
        Log.d("address1 ", address1.toString());
        Log.d("address2 ", address2.toString());
        ArrayAdapter propertyAdapter = new CustomerAdapter(this, address1, address2);
        ListView propertyListView = (ListView) findViewById(R.id.propertyListView);
        propertyListView.setAdapter(propertyAdapter);

        propertyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String property = String.valueOf(parent.getItemAtPosition(position));
                //Toast.makeText(LandlordManagePropertiesActivity.this, property, Toast.LENGTH_SHORT).show();
                Intent goManageRooms = new Intent(LandlordManagePropertiesActivity.this, LandlordManageRoomsActivity.class);
                LandlordManagePropertiesActivity.this.startActivity(goManageRooms);

                SharedPreferences.Editor editor = getSharedPreferences("USER_ID", MODE_PRIVATE).edit();
                editor.putInt("rowNum", position);
                editor.apply();
            }
        });
    }
}
