package com.kappatid.jerome.niche.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.FieldHelper;
import com.kappatid.jerome.niche.classes.RoomOfferedThread;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.LoadRoomInformation;
import com.kappatid.jerome.niche.dao.UpdateRoomsOffered;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title TenantRoomsOfferedInformationActivity.java
 * @purpose This class is the information of rooms offered to a Tenant
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class TenantRoomsOfferedInformationActivity extends AppCompatActivity implements LoadRoomInformation.AsyncResponse {
    // Creating textview fortv RoomPrice
    private TextView tvRoomPrice;
    // Creating textview fortv FlatStreet
    private TextView tvFlatStreet;
    // Creating textview fortv Suburb
    private TextView tvSuburb;
    // Creating Spinner for spinBedroom
    private Spinner spinBedroom;
    // Creating Spinner for spinBathroom
    private Spinner spinBathroom;
    // Creating Spinner for spinParking
    private Spinner spinParking;
    // Creating RadioButton for rdioAlarmYes
    private RadioButton rdioAlarmYes;
    // Creating RadioButton for rdioAlarmNo
    private RadioButton rdioAlarmNo;
    // Creating textview for tvSpecificDetails
    private TextView tvSpecificDetails;
    // Creating textview for tvFurnishing
    private TextView tvFurnishing;
    // Creating textview for tvUtilities
    private TextView tvUtilities;
    // Creating textview for tvAvailableDate
    private TextView tvAvailableDate;
    // Creating Spinner for spinTenantCount
    private Spinner spinTenantCount;
    // Creating RadioButton for rdioPetsYes
    private RadioButton rdioPetsYes;
    // Creating RadioButton for rdioPetsNo
    private RadioButton rdioPetsNo;
    // Creating RadioButton for rdioPetsNeg
    private RadioButton rdioPetsNeg;
    // Creating RadioButton for rdioSmokersYes
    private RadioButton rdioSmokersYes;
    // Creating RadioButton for rdioSmokersNo
    private RadioButton rdioSmokersNo;
    // Creating object of FieldHelper
    private FieldHelper fh = new FieldHelper();
    // Creating an array list of textview
    private ArrayList<TextView> fields = new ArrayList<>();
    // Creating an array list of spinners
    private ArrayList<Spinner> spinners = new ArrayList<>();
    // Creating an Image Switcher
    private ImageSwitcher imageSwitcher;
    // Creating a Handler
    private Handler handler;
    // Creating an array list of rooms images
    private int rooms[] = {R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.room4};
    // Boolean for Thread's loop condition
    private boolean isAlive = true;
    // int variable for room's position
    private int position = 0;
    // String variable to store the username of the tenant
    private String tenantUsername;
    // String variabled to store the user type of a user
    private String userType;
    // int variable to store the room number
    private int roomNum;
    // Button for accepting the room offer
    private Button btnAcceptRoomOffer;
    // Button for declining the room offer
    private Button btnDeclineRoomOffer;
    private int roomOfferedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_rooms_offered_information);
        setTitle("Rooms Details");

        // Store the room number from Shared Preference
        SharedPreferences pref1 = getSharedPreferences("ROOM_NUM", MODE_PRIVATE);
        roomNum = pref1.getInt("roomPos", 0);
        // Store the tenant username from Shared Preference
        SharedPreferences pref2 = getSharedPreferences("USER_ID", MODE_PRIVATE);
        tenantUsername = pref2.getString("username", "");
        // Store the user's user type from Shared Preference
        userType = pref2.getString("userType", "");

        // Creating a new handler object
        handler = new Handler();

        // Casting
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
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
        btnAcceptRoomOffer = (Button) findViewById(R.id.btnAcceptRoomOffer);
        btnDeclineRoomOffer = (Button) findViewById(R.id.btnDeclineRoomOffer);

        // Adding the textviews to array list
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

        // Set the fields to uneditable
        fh.setEditableFalse(fields);
        for (Spinner spin : spinners) {
            spin.setEnabled(false);
        }

        // Setting up the Image Switcher
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        // Creating the animation
        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out);

        // Setup the animation to image switcher
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);
        new Thread(new RoomOfferedThread(imageSwitcher, position, handler, rooms, isAlive)).start();

        // Load the room information
        LoadRoomInformation lri = new LoadRoomInformation(this, this, "", roomNum, userType, tenantUsername);
        lri.execute(Settings.URL_ADDRESS_LOAD_ROOM_INFORMATION);

        // Accept the room offered
        btnAcceptRoomOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRoomStatus("Accepted", "Do you want to accept this room?", "Room accepted!");
            }
        });

        // Decline the room offeered
        btnDeclineRoomOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRoomStatus("Declined", "Do you want to decline this room?", "Room declined!");
            }
        });

    }

    /**
     * @param status       status of the room
     * @param message      message to display on dialog
     * @param toastMessage message to display on toast
     */
    public void changeRoomStatus(final String status, String message, final String toastMessage) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Room Offered")
                .setIcon(R.drawable.ic_no_records_found)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref1 = getSharedPreferences("ROOM_ID", MODE_PRIVATE);
                        roomOfferedID = pref1.getInt("roomOfferedID", 0);

                        UpdateRoomsOffered uro = new UpdateRoomsOffered(TenantRoomsOfferedInformationActivity.this, status, roomOfferedID);
                        uro.execute(Settings.URL_ADDRESS_UPDATE_ROOM_OFFERED_STATUS);

                        Intent goRoomsOffered = new Intent(TenantRoomsOfferedInformationActivity.this, TenantRoomsOfferedActivity.class);
                        TenantRoomsOfferedInformationActivity.this.startActivity(goRoomsOffered);
                        Toast.makeText(TenantRoomsOfferedInformationActivity.this, toastMessage, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * @param roomDetails array list details of rooms
     *                    <p>
     *                    Set the field's details base on the information
     *                    on the database
     */
    @Override
    public void processFinish(String[] roomDetails) {
        SharedPreferences.Editor editor = getSharedPreferences("ROOM_ID", MODE_PRIVATE).edit();
        editor.putInt("roomOfferedID", Integer.parseInt(roomDetails[14]));
        editor.apply();
        tvRoomPrice.setText(roomDetails[0]);
        tvFlatStreet.setText(roomDetails[1]);
        tvSuburb.setText(roomDetails[2]);
        if (roomDetails[3].equals("Choose")) {
        } else {
            spinBedroom.setSelection(Integer.parseInt(roomDetails[4]));
        }
        if (roomDetails[4].equals("Choose")) {
        } else {
            spinBathroom.setSelection(Integer.parseInt(roomDetails[5]));
        }
        if (roomDetails[5].equals("Choose")) {
        } else {
            spinParking.setSelection(Integer.parseInt(roomDetails[6]));
        }
        if (roomDetails[6].equals("Yes")) {
            rdioAlarmYes.setChecked(true);
        } else {
            rdioAlarmNo.setChecked(true);
        }
        tvSpecificDetails.setText(roomDetails[7]);
        tvFurnishing.setText(roomDetails[8]);
        tvUtilities.setText(roomDetails[9]);
        tvAvailableDate.setText(roomDetails[10]);
        if (roomDetails[11].equals("Single")) {
            spinTenantCount.setSelection(1);
        } else if (roomDetails[11].equals("Double")) {
            spinTenantCount.setSelection(2);
        } else {
            spinTenantCount.setSelection(3);
        }
        if (roomDetails[12].equals("Yes")) {
            rdioPetsYes.setChecked(true);
        } else if (roomDetails[12].equals("No")) {
            rdioPetsNo.setChecked(true);
        } else {
            rdioPetsNeg.setChecked(true);
        }
        if (roomDetails[13].equals("Yes")) {
            rdioSmokersYes.setChecked(true);
        } else {
            rdioSmokersNo.setChecked(true);
        }
    }
}
