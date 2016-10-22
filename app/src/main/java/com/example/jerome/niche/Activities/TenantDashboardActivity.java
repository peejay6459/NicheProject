package com.example.jerome.niche.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.example.jerome.niche.R;

/**
 * @author: kappatid LLC
 * @date:
 *
 * @description
 */

public class TenantDashboardActivity extends AppCompatActivity {
    Button btnTenantInfo;
    Button btnTenantRoomsOffered;
    Button btnTenantPaymentHistory;

    TabHost th;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_dashboard);
        setTitle("Tenant Dashboard");

        btnTenantInfo = (Button) findViewById(R.id.btnTenantPersonalInformation);
        btnTenantRoomsOffered = (Button) findViewById(R.id.btnTenantRoomsOffered);
        btnTenantPaymentHistory = (Button) findViewById(R.id.btnTenantPaymentHistory);

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

        btnTenantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPersonalInfo = new Intent(TenantDashboardActivity.this, TenantProfileActivity.class);
                TenantDashboardActivity.this.startActivity(goPersonalInfo);
            }
        });

        btnTenantRoomsOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRoomsOffered= new Intent(TenantDashboardActivity.this, TenantRoomsOfferedActivity.class);
                TenantDashboardActivity.this.startActivity(goRoomsOffered);
            }
        });

        btnTenantPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPaymentHistory = new Intent(TenantDashboardActivity.this, TenantPaymentHistoryActivity.class);
                TenantDashboardActivity.this.startActivity(goPaymentHistory);
            }
        });
    }


}
