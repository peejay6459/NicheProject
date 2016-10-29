package com.example.jerome.niche.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.InsertRoom;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jerome on 28/10/2016.
 */

public class LandlordCreateRoomActivity extends AppCompatActivity {
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
    private String address1;
    private String address2;
    private Button btnAddRoom;
    private JSONObject roomDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_create_room);
        setTitle("Create Room");

        SharedPreferences pref = getSharedPreferences("propertyAddress", MODE_PRIVATE);
        address1 = pref.getString("propertyAddress1", "");
        address2 = pref.getString("propertyAddress2", "");

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
        btnAddRoom = (Button) findViewById(R.id.btnAddRoom);

        tvFlatStreet.setText(address1);
        tvFlatStreet.setEnabled(false);
        tvSuburb.setText(address2);
        tvSuburb.setEnabled(false);

        fh.changeTextField(tvRoomPrice, tvSpecificDetails, tvFurnishing, tvUtilities, tvAvailableDate,
                "Price per (week/fortnight/month)", "About the room", "(e.g. Fridge, Stove etc)", "(eg. Electricities, Internet, Water)", "DD MM YYYY");
        fh.changeTextField(tvAvailableDate, tvRoomPrice, tvSpecificDetails, tvFurnishing, tvUtilities,
                "DD MM YYYY", "Price per (week/fortnight/month)", "About the room", "(e.g. Fridge, Stove etc)", "(eg. Electricities, Internet, Water)");
        fh.changeTextField(tvUtilities, tvAvailableDate, tvRoomPrice, tvSpecificDetails, tvFurnishing,
                "(eg. Electricities, Internet, Water)", "DD MM YYYY", "Price per (week/fortnight/month)", "About the room", "(e.g. Fridge, Stove etc)");
        fh.changeTextField(tvFurnishing, tvUtilities, tvAvailableDate, tvRoomPrice, tvSpecificDetails,
                "(e.g. Fridge, Stove etc)", "(eg. Electricities, Internet, Water)", "DD MM YYYY", "Price per (week/fortnight/month)", "About the room");
        fh.changeTextField(tvSpecificDetails, tvFurnishing, tvUtilities, tvAvailableDate, tvRoomPrice,
                "About the room", "(e.g. Fridge, Stove etc)", "(eg. Electricities, Internet, Water)", "DD MM YYYY", "Price per (week/fortnight/month)");

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()){
                    InsertRoom ir = new InsertRoom(LandlordCreateRoomActivity.this);
                    ir.execute(Settings.URL_ADDRESS_INSERT_ROOM);
                    Intent backManageRooms = new Intent(LandlordCreateRoomActivity.this, LandlordManageRoomsActivity.class);
                    LandlordCreateRoomActivity.this.startActivity(backManageRooms);
                    Toast.makeText(LandlordCreateRoomActivity.this, "Room created successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validateFields(){
        boolean isFilledUp = false;
        if(!(tvRoomPrice.getText().toString().equals("Price per (week/fortnight/month)"))
                && !(tvRoomPrice.getText().toString().isEmpty())){
            if(!(spinTenantCount.getSelectedItem().toString().equals("Occupancy"))){
                if(rdioAlarmYes.isChecked() || rdioAlarmNo.isChecked()){
                    if((rdioPetsYes.isChecked()) || (rdioPetsNo.isChecked()) || (rdioPetsNeg.isChecked())){
                        if(rdioSmokersYes.isChecked() || rdioSmokersNo.isChecked()){
                            isFilledUp = true;
                        }else{
                            Toast.makeText(LandlordCreateRoomActivity.this, "Please choose if Smokers are allowed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LandlordCreateRoomActivity.this, "Please choose if Pets are allowed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LandlordCreateRoomActivity.this, "Please select Smoke Alarm status", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LandlordCreateRoomActivity.this, "Please select Occupancy", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LandlordCreateRoomActivity.this, "Please give a price for this room", Toast.LENGTH_SHORT).show();
        }
        return isFilledUp;
    }
    public String getRoomDetailsJsonObject(){
        roomDetails = new JSONObject();
        try{
            roomDetails.put("roomPrice", tvRoomPrice.getText().toString());
            roomDetails.put("address1", tvFlatStreet.getText().toString());
            roomDetails.put("address2", tvSuburb.getText().toString());
            roomDetails.put("bedroom", spinBedroom.getSelectedItem().toString());
            roomDetails.put("bathroom", spinBathroom.getSelectedItem().toString());
            roomDetails.put("parking", spinParking.getSelectedItem().toString());
            if(rdioAlarmYes.isChecked()){
                roomDetails.put("smokeAlarm", "Yes");
            }else if(rdioAlarmNo.isChecked()){
                roomDetails.put("smokeAlarm", "No");
            }
            roomDetails.put("specificDetails", tvSpecificDetails.getText().toString());
            roomDetails.put("furnishings", tvFurnishing.getText().toString());
            roomDetails.put("utilities", tvUtilities.getText().toString());
            roomDetails.put("availableDate", tvAvailableDate.getText().toString());
            roomDetails.put("occupancy", spinTenantCount.getSelectedItem().toString());
            if(rdioPetsYes.isChecked()){
                roomDetails.put("pets", "Yes");
            }else if(rdioPetsNo.isChecked()){
                roomDetails.put("pets", "No");
            }else if(rdioPetsNeg.isChecked()){
                roomDetails.put("pets", "Negotiable");
            }
            if(rdioSmokersYes.isChecked()){
                roomDetails.put("smokers", "Yes");
            }else if(rdioSmokersNo.isChecked()){
                roomDetails.put("smokers", "No");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return roomDetails.toString();
    }
}
