package com.example.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.classes.Settings;
import com.example.jerome.niche.dao.DeleteProperty;
import com.example.jerome.niche.dao.DeleteRoom;
import com.example.jerome.niche.dao.LoadRoomInformation;
import com.example.jerome.niche.dao.UpdateRoomInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jerome on 29/10/2016.
 */

public class LandlordEditRoomActivity extends AppCompatActivity implements LoadRoomInformation.AsyncResponse {
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
    private JSONObject roomDetails;
    private int roomID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_edit_room);
        setTitle("Edit Room");

        SharedPreferences pref = getSharedPreferences("ROOM_NUM", MODE_PRIVATE);
        roomNum = pref.getInt("roomPos", 0);
        SharedPreferences pref1 = getSharedPreferences("propertyAddress", MODE_PRIVATE);
        address = pref1.getString("propertyAddress1", "");

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

        LoadRoomInformation lri = new LoadRoomInformation(this, this, address, roomNum);
        lri.execute(Settings.URL_ADDRESS_LOAD_ROOM_INFORMATION);

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
            for(TextView field : fields){
                field.setTextColor(Color.BLACK);
            }
        }
        if(id == R.id.savePropertyInfo){
            fh.setEditableFalse(fields);
            for(Spinner spin : spinners){
                spin.setEnabled(false);
            }
            for(TextView field : fields){
                field.setTextColor(Color.GRAY);
            }
            SharedPreferences pref1 = getSharedPreferences("ROOM_ID", MODE_PRIVATE);
            roomID = pref1.getInt("roomID", 0);
            Log.d("roomID->", String.valueOf(roomID));

            UpdateRoomInformation uri = new UpdateRoomInformation(LandlordEditRoomActivity.this, roomID);
            uri.execute(Settings.URL_ADDRESS_UPDATE_ROOM_INFORMATION);
        }
        if(id == R.id.deleteProperty){
            new AlertDialog.Builder(this)
                    .setTitle("Delete Room")
                    .setMessage("Are you sure you want to delete this room?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences pref1 = getSharedPreferences("ROOM_ID", MODE_PRIVATE);
                            roomID = pref1.getInt("roomID", 0);
                            DeleteRoom dr = new DeleteRoom(roomID);
                            dr.execute(Settings.URL_ADDRESS_DELETE_ROOM);
                            Intent goManageRooms = new Intent(LandlordEditRoomActivity.this, LandlordManageRoomsActivity.class);
                            LandlordEditRoomActivity.this.startActivity(goManageRooms);
                            Toast.makeText(LandlordEditRoomActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String[] roomDetails) {
        SharedPreferences.Editor editor = getSharedPreferences("ROOM_ID", MODE_PRIVATE).edit();
        editor.putInt("roomID", Integer.parseInt(roomDetails[0]));
        editor.apply();
        Log.d("roomID", roomDetails[0]);
        tvRoomPrice.setText(roomDetails[1]);
        tvFlatStreet.setText(roomDetails[2]);
        tvSuburb.setText(roomDetails[3]);
        if(roomDetails[4].equals("Choose")) {
        }else{
            spinBedroom.setSelection(Integer.parseInt(roomDetails[4]));
        }
        if(roomDetails[5].equals("Choose")) {
        }else{
            spinBathroom.setSelection(Integer.parseInt(roomDetails[5]));
        }
        if(roomDetails[6].equals("Choose")) {
        }else{
            spinParking.setSelection(Integer.parseInt(roomDetails[6]));
        }
        if(roomDetails[7].equals("Yes")){
            rdioAlarmYes.setChecked(true);
        }else{
            rdioAlarmNo.setChecked(true);
        }
        tvSpecificDetails.setText(roomDetails[8]);
        tvFurnishing.setText(roomDetails[9]);
        tvUtilities.setText(roomDetails[10]);
        tvAvailableDate.setText(roomDetails[11]);
        if(roomDetails[12].equals("Single")){
            spinTenantCount.setSelection(1);
        }else if(roomDetails[12].equals("Double")){
            spinTenantCount.setSelection(2);
        }else{
            spinTenantCount.setSelection(3);
        }
        if(roomDetails[13].equals("Yes")){
            rdioPetsYes.setChecked(true);
        }else if(roomDetails[13].equals("No")){
            rdioPetsNo.setChecked(true);
        }else{
            rdioPetsNeg.setChecked(true);
        }
        if(roomDetails[14].equals("Yes")){
            rdioSmokersYes.setChecked(true);
        }else{
            rdioSmokersNo.setChecked(true);
        }
    }
}
