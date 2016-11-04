package com.kappatid.jerome.niche.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.kappatid.jerome.niche.R;

/**
 * @author Kappatid LLC
 * @title TenantDashboardActivity.java
 * @purpose This class is the Dashboard of the Tenant's
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class TenantDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_dashboard);
        setTitle("Tenant Dashboard");

        // Creating a button for tenant info
        Button btnTenantInfo;
        // Creating a button for rooms offered
        Button btnTenantRoomsOffered;
        // Creating a Tab Host
        TabHost th;

        // Casting
        btnTenantInfo = (Button) findViewById(R.id.btnTenantPersonalInformation);
        btnTenantRoomsOffered = (Button) findViewById(R.id.btnTenantRoomsOffered);

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

        // Setting an onclick listener for Tenant information
        btnTenantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPersonalInfo = new Intent(TenantDashboardActivity.this, TenantPersonalInformationActivity.class);
                TenantDashboardActivity.this.startActivity(goPersonalInfo);
            }
        });

        btnTenantRoomsOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRoomsOffered = new Intent(TenantDashboardActivity.this, TenantRoomsOfferedActivity.class);
                TenantDashboardActivity.this.startActivity(goRoomsOffered);
            }
        });
    }

    // Setting up an action bar
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
