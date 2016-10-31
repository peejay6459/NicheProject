package com.example.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.LoadPropertyManagerList;
import com.example.jerome.niche.dao.ValidatePropertyManagerList;

import java.util.ArrayList;

public class LandlordManagePropertyManagersActivity extends AppCompatActivity implements ValidatePropertyManagerList.AsyncResponse, LoadPropertyManagerList.AsyncResponse {

    private String managerUsername;
    private String userID;
    private ListView managerListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_manage_property_managers);
        setTitle("Manage Property Managers");

        managerListView = (ListView) findViewById(R.id.managersListView);

        SharedPreferences pref = getSharedPreferences("USER_ID", MODE_PRIVATE);
        userID = pref.getString("userID", "");

        ArrayList<String> managerName = new ArrayList<>();

        LoadPropertyManagerList lpmi = new LoadPropertyManagerList(this, this, managerName, userID);
        lpmi.execute(Settings.URL_ADDRESS_LOAD_PROPERTY_MANAGER_LIST);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_manage_property_manager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addManager){
            AlertDialog.Builder db = new AlertDialog.Builder(this);
            db.setTitle("Add Manager");
            db.setIcon(R.drawable.ic_add_property_manager);
            db.setMessage("Please input username of property manager");
            final EditText tvManagerUsername = new EditText(this);
            tvManagerUsername.setInputType(InputType.TYPE_CLASS_TEXT);
            db.setView(tvManagerUsername);
            db.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    managerUsername = tvManagerUsername.getText().toString();
                    ValidatePropertyManagerList vpml = new ValidatePropertyManagerList(LandlordManagePropertyManagersActivity.this, LandlordManagePropertyManagersActivity.this, managerUsername, userID);
                    vpml.execute(Settings.URL_ADDRESS_VALIDATE_PROPERTY_MANAGER_LIST);
                }
            });
            db.setNegativeButton(android.R.string.no, null);
            db.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String response) {
        Log.d("response", response);
        if(response.equals("already exist")){
            Toast.makeText(LandlordManagePropertyManagersActivity.this, "It's already an existing record", Toast.LENGTH_LONG).show();
        }else if(response.equals("Not Found")){
            Toast.makeText(LandlordManagePropertyManagersActivity.this, "Username doesn't exist or Not a Property Manager", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(LandlordManagePropertyManagersActivity.this, "Manager added successfully", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(ArrayList<String> managerName) {
        ArrayAdapter<String> managerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                managerName);

        managerListView.setAdapter(managerAdapter);
    }
}
