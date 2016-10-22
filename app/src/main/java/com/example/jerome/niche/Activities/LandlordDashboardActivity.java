package com.example.jerome.niche.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.example.jerome.niche.R;

import static android.R.attr.onClick;

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
}
