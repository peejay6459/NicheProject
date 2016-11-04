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
import com.kappatid.jerome.niche.classes.ChangeColorThread;

/**
 * @author Kappatid LLC
 * @title LandlordDashBoardActivity.java
 * @purpose This class is the Dashboard of the Landlord's
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LandlordDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_dashboard);
        setTitle("Landlord Dashboard");

        // Button to go to Landlord personal information
        Button btnLandlordInfo;
        // Button to go to manager property managers
        Button btnLandlordManagePropertyManagers;
        // Button to go to manage her own properties
        Button btnLandlordManageProperties;
        // Creating a Tab Host
        TabHost th;
        // Textview for textfield
        TextView tvFutureRelease;
        // Creating a handler
        Handler handler;

        // Casting
        btnLandlordInfo = (Button) findViewById(R.id.btnLandlordPersonalInformation);
        btnLandlordManagePropertyManagers = (Button) findViewById(R.id.btnLandlordManagePropertyManagers);
        btnLandlordManageProperties = (Button) findViewById(R.id.btnLandlordManageProperties);
        tvFutureRelease = (TextView) findViewById(R.id.tvFutureRelease);

        // Creating new object for handler
        handler = new Handler();

        // Setting up the Tabhost
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

        // Changing a color thread
        ChangeColorThread cThread = new ChangeColorThread(tvFutureRelease, handler);
        cThread.start();


        // Setting an onclick listener for btnLandlordInfo
        btnLandlordInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPersonalInfo = new Intent(LandlordDashboardActivity.this, LandlordPersonalInformationActivity.class);
                LandlordDashboardActivity.this.startActivity(goPersonalInfo);
            }
        });

        // Setting an onclick listener for btnLandlordManagePropertyManagers
        btnLandlordManagePropertyManagers.setOnClickListener(new View.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(View v) {
                                                                     Intent goManagePropertyManagers = new Intent(LandlordDashboardActivity.this, LandlordManagePropertyManagersActivity.class);
                                                                     LandlordDashboardActivity.this.startActivity(goManagePropertyManagers);
                                                                 }
                                                             }
        );

        // Setting an onclick listener for btnLandlordManageProperties
        btnLandlordManageProperties.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               Intent goManageProperties = new Intent(LandlordDashboardActivity.this, LandlordManagePropertiesActivity.class);
                                                               LandlordDashboardActivity.this.startActivity(goManageProperties);
                                                           }
                                                       }
        );
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
        // Sign out the user
        if (id == R.id.signOut) {
            Intent goToLoginPage = new Intent(this, MainActivity.class);
            this.startActivity(goToLoginPage);
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
