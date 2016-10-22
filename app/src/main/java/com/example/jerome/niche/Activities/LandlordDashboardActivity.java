package com.example.jerome.niche.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.example.jerome.niche.R;

/**
 * Created by philip on 10/21/2016.
 */

public class LandlordDashboardActivity extends AppCompatActivity {
    Button btnLandlordInfo;
    TabHost th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_dashboard);
        setTitle("Landlord Dashboard");
        // To enable Action bar's back button
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnLandlordInfo = (Button) findViewById(R.id.btnLandlordPersonalInformation);

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
    }

    public void btnLandlordPersonalInformation(View view)
    {
        this.startActivity(new Intent(this, LandlordPersonalInformationActivity.class));
    }
    public void btnLandlordManagePropertyManagers(View view)
    {
        this.startActivity(new Intent(this, LandlordPersonalInformationActivity.class));
    }
    public void btnLandlordManageProperties(View view)
    {
        this.startActivity(new Intent(this, LandlordPersonalInformationActivity.class));
    }


}
