package com.example.jerome.niche.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.jerome.niche.R;

/**
 * Created by philip on 10/21/2016.
 */

public class LandlordDashboardActivity extends AppCompatActivity {
    Button btnLandlordInfo;
    Button btnLandlordManagePropertyManagers;
    Button btnLandlordManageProperties;
    TabHost th;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_dashboard);
        setTitle("Landlord Dashboard");


        // To enable Action bar's back button
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnLandlordInfo = (Button) findViewById(R.id.btnLandlordPersonalInformation);
        btnLandlordManagePropertyManagers = (Button) findViewById(R.id.btnLandlordManagePropertyManagers);
        btnLandlordManageProperties = (Button) findViewById(R.id.btnLandlordManageProperties);

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


        btnLandlordInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPersonalInfo = new Intent(LandlordDashboardActivity.this, LandlordPersonalInformationActivity.class);
                LandlordDashboardActivity.this.startActivity(goPersonalInfo);
            }
        });

        btnLandlordManagePropertyManagers.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent goManagePropertyManagers = new Intent(LandlordDashboardActivity.this, LandlordManagePropertyManagersActivity.class);
                 LandlordDashboardActivity.this.startActivity(goManagePropertyManagers);
             }
         }
        );

        btnLandlordManageProperties.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent goManageProperties = new Intent(LandlordDashboardActivity.this, LandlordManagePropertiesActivity.class);
                 LandlordDashboardActivity.this.startActivity(goManageProperties);
             }
         }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_signout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.signOut){
            Intent goToLoginPage = new Intent(this, MainActivity.class);
            this.startActivity(goToLoginPage);
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
