package com.example.jerome.niche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author: kappatid LLC
 * @date:
 *
 * @description: Not yet used
 */

public class ManageRoomsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);

        ActionBar bar = this.getSupportActionBar();
        bar.setTitle("Dashboard");
        bar.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_managerooms, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.manageRooms) {
            Intent addTenant = new Intent(this, TenantRegisterActivity.class);
            this.startActivity(addTenant);
        }
        return super.onOptionsItemSelected(item);
    }
}
