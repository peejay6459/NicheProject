package com.example.jerome.niche.activities;

import android.content.Intent;
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
