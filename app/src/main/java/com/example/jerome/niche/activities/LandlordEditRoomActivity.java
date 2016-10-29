package com.example.jerome.niche.activities;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.LoadRoomInfo;

import java.util.ArrayList;

/**
 * Created by Jerome on 29/10/2016.
 */

public class LandlordEditRoomActivity extends AppCompatActivity {
    private TextView tvRoomPrice;
    private TextView tvFlatStreet;
    private TextView tvSuburb;
    private Spinner spinBedroom;
    private Spinner spinBathroom;
    private Spinner spinParking;
    private RadioButton rdioAlarmYes;
    private RadioButton rdioAlarmNo;
    private TextView tvSpecificDetails;
    private TextView tvFurnishing;
    private TextView tvUtilities;
    private TextView tvAvailableDate;
    private Spinner spinTenantCount;
    private RadioButton rdioPetsYes;
    private RadioButton rdioPetsNo;
    private RadioButton rdioPetsNeg;
    private RadioButton rdioSmokersYes;
    private RadioButton rdioSmokersNo;
    private FieldHelper fh = new FieldHelper();
    private ArrayList<TextView> fields = new ArrayList<>();
    private ArrayList<Spinner> spinners = new ArrayList<>();
    private int roomNum;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_edit_room);
        setTitle("Edit Room");

        SharedPreferences pref = getSharedPreferences("ROOM_NUM", MODE_PRIVATE);
        roomNum = pref.getInt("roomPos", 0);
        SharedPreferences pref1 = getSharedPreferences("propertyAddress", MODE_PRIVATE);
        address = pref.getString("propertyAddress1", "");

        tvRoomPrice = (TextView) findViewById(R.id.tvRoomPrice);
        tvFlatStreet = (TextView) findViewById(R.id.tvFlatStreet);
        tvSuburb = (TextView) findViewById(R.id.tvSuburb);
        spinBedroom = (Spinner) findViewById(R.id.spinBedroom);
        spinBathroom = (Spinner) findViewById(R.id.spinBathroom);
        spinParking = (Spinner) findViewById(R.id.spinParking);
        rdioAlarmYes = (RadioButton) findViewById(R.id.rdioAlarmYes);
        rdioAlarmNo = (RadioButton) findViewById(R.id.rdioAlarmNo);
        tvSpecificDetails = (TextView) findViewById(R.id.tvSpecificDetails);
        tvFurnishing = (TextView) findViewById(R.id.tvFurnishing);
        tvUtilities = (TextView) findViewById(R.id.tvUtilities);
        tvAvailableDate = (TextView) findViewById(R.id.tvAvailableDate);
        spinTenantCount = (Spinner) findViewById(R.id.spinTenantCount);
        rdioPetsYes = (RadioButton) findViewById(R.id.rdioPetsYes);
        rdioPetsNo = (RadioButton) findViewById(R.id.rdioPetsNo);
        rdioPetsNeg = (RadioButton) findViewById(R.id.rdioPetsNeg);
        rdioSmokersYes = (RadioButton) findViewById(R.id.rdioSmokeYes);
        rdioSmokersNo = (RadioButton) findViewById(R.id.rdioSmokeNo);

        tvFlatStreet.setEnabled(false);
        tvSuburb.setEnabled(false);

        fields.add(tvRoomPrice);
        fields.add(tvFlatStreet);
        fields.add(tvSuburb);
        fields.add(rdioAlarmYes);
        fields.add(rdioAlarmNo);
        fields.add(tvSpecificDetails);
        fields.add(tvFurnishing);
        fields.add(tvUtilities);
        fields.add(tvAvailableDate);
        fields.add(rdioPetsYes);
        fields.add(rdioPetsNo);
        fields.add(rdioPetsNeg);
        fields.add(rdioSmokersYes);
        fields.add(rdioSmokersNo);
        spinners.add(spinBedroom);
        spinners.add(spinBathroom);
        spinners.add(spinParking);
        spinners.add(spinTenantCount);

        fh.setEditableFalse(fields);
        for(Spinner spin : spinners){
            spin.setEnabled(false);
        }

        LoadRoomInfo lri = new LoadRoomInfo(this, address, roomNum, spinBedroom, spinBathroom, spinParking, spinTenantCount,
                rdioAlarmYes, rdioAlarmNo, rdioPetsYes, rdioPetsNo, rdioPetsNeg, rdioSmokersYes, rdioSmokersNo,
                tvRoomPrice, tvFlatStreet, tvSuburb,
                tvSpecificDetails, tvFurnishing, tvUtilities, tvAvailableDate);
        lri.execute(Settings.URL_ADDRESS_LOAD_ROOM_INFO);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_edit_property, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.editPropertyInfo){
            fh.setEditableTrue(fields);
            for(Spinner spin : spinners){
                spin.setEnabled(true);
            }
        }
        if(id == R.id.savePropertyInfo){
            fh.setEditableFalse(fields);
            for(Spinner spin : spinners){
                spin.setEnabled(false);
            }
        }
        if(id == R.id.deleteProperty){

        }
        return super.onOptionsItemSelected(item);
    }
}
