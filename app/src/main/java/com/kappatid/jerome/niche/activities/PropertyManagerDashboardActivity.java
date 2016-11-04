package com.kappatid.jerome.niche.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.ChangeColorRunnable;

/**
 * @author Kappatid LLC
 * @title PropertyManagerDashboardActivity.java
 * @purpose This class is the Dashboard of the Manager's
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class PropertyManagerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager_dashboard);
        setTitle("Property Manager Dashboard");

        // Creating button for Property Manager Information
        Button btnPropertyManagerInfo;
        // Creating button for property manager managing rooms
        Button btnPropertyManagerManageRooms;
        // Creating a Tab Host
        TabHost th;
        // Creating a handler
        Handler handler;
        // Creating a textview for a textfield
        TextView tvFutureRelease;

        // Casting
        btnPropertyManagerInfo = (Button) findViewById(R.id.btnPropertyManagerPersonalInformation);
        btnPropertyManagerManageRooms = (Button) findViewById(R.id.btnPropertyManagerManageRooms);
        ;
        tvFutureRelease = (TextView) findViewById(R.id.tvFutureRelease);

        // Creating a new object for handler
        handler = new Handler();

        // Setting up the Tab Host
        th = (TabHost) findViewById(R.id.tvTabHost);
        th.setup();
        TabHost.TabSpec specs = th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Dashboard");
        th.addTab(specs);
        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("News Feed");
        th.addTab(specs);

        // Execute the thread of changing colors
        new Thread(new ChangeColorRunnable(tvFutureRelease, handler)).start();

        // Setting an onclick listener for property Manager Information
        btnPropertyManagerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPersonalInfo = new Intent(PropertyManagerDashboardActivity.this, PropertyManagerPersonalInformationActivity.class);
                PropertyManagerDashboardActivity.this.startActivity(goPersonalInfo);
            }
        });

        // Setting an onclick listener for property Manager Managing Rooms
        btnPropertyManagerManageRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goManageRooms = new Intent(PropertyManagerDashboardActivity.this, PropertyManagerManageRoomsActivity.class);
                PropertyManagerDashboardActivity.this.startActivity(goManageRooms);
            }
        });
    }

    // Setting up the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_signout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Sign out
        if (id == R.id.signOut) {
            Intent goToLoginPage = new Intent(this, MainActivity.class);
            this.startActivity(goToLoginPage);
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
