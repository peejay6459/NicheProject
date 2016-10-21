package com.example.jerome.niche;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Jerome on 21/10/2016.
 */

public class TenantPersonalInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_personal_info);
        setTitle("Personal Information");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_tenant_info, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
