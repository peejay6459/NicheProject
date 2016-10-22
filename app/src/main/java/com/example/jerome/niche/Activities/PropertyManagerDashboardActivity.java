package com.example.jerome.niche.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.example.jerome.niche.R;

/**
 * Created by philip on 10/22/2016.
 */

public class PropertyManagerDashboardActivity extends AppCompatActivity {
    Button btnPropertyManagerInfo;
    TabHost th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager_dashboard);
        setTitle("Property Manager Dashboard");

        btnPropertyManagerInfo = (Button) findViewById(R.id.btnPropertyManagerPersonalInformation);

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

    public void btnPropertyManagerPersonalInformation(View view)
    {
        this.startActivity(new Intent(this, PropertyManagerPersonalInformationActivity.class));
    }
    public void btnPropertyManagerManageRooms(View view)
    {
        this.startActivity(new Intent(this, PropertyManagerManageRoomsActivity.class));
    }
    public void btnPropertyManagerReviewTenants(View view)
    {
        this.startActivity(new Intent(this, PropertyManagerReviewTenantsActivity.class));
    }

}
