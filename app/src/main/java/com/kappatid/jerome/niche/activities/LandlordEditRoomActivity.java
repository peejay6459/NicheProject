package com.kappatid.jerome.niche.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.FieldHelper;
import com.kappatid.jerome.niche.classes.RoomThread;
import com.kappatid.jerome.niche.classes.Settings;
import com.kappatid.jerome.niche.dao.DeleteRoom;
import com.kappatid.jerome.niche.dao.InsertRoomOffered;
import com.kappatid.jerome.niche.dao.LoadRoomInformation;
import com.kappatid.jerome.niche.dao.UpdateRoomInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title LandlordEditRoomActivity.java
 * @purpose This class is for creating editing the room
 * @date November 03, 2016
 * @input Price, Location, Bedroom count, Bathroom count, Parking count,
 * Smoke alarm, room details, furnishings, utilities, date available,
 * occupancy, Pets allowed, Smokers allowed
 * @processing UpdateRoomInformation.java
 * @output Room has been updated
 */

public class LandlordEditRoomActivity extends AppCompatActivity implements LoadRoomInformation.AsyncResponse, InsertRoomOffered.AsyncResponse {
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
    // Creating an int variable to store the room number
    private int roomNum;
    // Creating a String variable to store the address
    private String address;
    // Creating a JSON Object
    private JSONObject roomDetails;
    // Creating an int variable to store the room ID
    private int roomID;
    // Creating an ImageSwitcher
    private ImageSwitcher imageSwitcher;
    // Creating Handler
    private Handler handler;
    // Creating an array of room pictures
    private int rooms[] = {R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.room4};
    // Creating a boolean variable for thread condition
    private boolean isAlive = true;
    // Creating an object of RoomThread
    private RoomThread roomThread;
    // Creating an int variable to store the position number of listview
    private int position = 0;
    // Creating a button for offering a room to a tenant
    private Button btnOfferRoom;
    // Creating a String variable to store the manager's username
    private String managerUsername;
    // Creating a String variable to store the tenant's username
    private String tenantUsername;
    // Creating a String variable to store the user's user type
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_edit_room);
        setTitle("Edit Room");

        // Store the room number from Shared Preference
        SharedPreferences pref = getSharedPreferences("ROOM_NUM", MODE_PRIVATE);
        roomNum = pref.getInt("roomPos", 0);
        // Store the address from Shared Preference
        SharedPreferences pref1 = getSharedPreferences("propertyAddress", MODE_PRIVATE);
        address = pref1.getString("propertyAddress1", "");
        // Store the manager username and user type from Shared Preference
        SharedPreferences pref2 = getSharedPreferences("USER_ID", MODE_PRIVATE);
        managerUsername = pref2.getString("username", "");
        userType = pref2.getString("userType", "");

        // Creating a new object of handler
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
        btnOfferRoom = (Button) findViewById(R.id.btnOfferRoom);

        // Setting the address to uneditable
        tvFlatStreet.setEnabled(false);
        tvSuburb.setEnabled(false);

        // Add the fields into array list
        fields.add(tvRoomPrice);
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

        // Setting the fields to uneditable
        fh.setEditableFalse(fields);
        // Setting the spinners to uneditable
        for (Spinner spin : spinners) {
            spin.setEnabled(false);
        }

        // Load the details of the room from the database through middle tier
        LoadRoomInformation lri = new LoadRoomInformation(this, this, address, roomNum, userType, "");
        lri.execute(Settings.URL_ADDRESS_LOAD_ROOM_INFORMATION);

        // Setting the image switcher's layout
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

        // Creating an object for animations
        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out);

        // Setting the image switcher's animation
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);

        // Starting the thread here for images
        roomThread = new RoomThread(imageSwitcher, position, handler, rooms, isAlive);
        roomThread.start();

        // Setting the onclick listener for btnOfferRoom
        btnOfferRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store the data to roomID from Shared Preference
                SharedPreferences pref3 = getSharedPreferences("ROOM_ID", MODE_PRIVATE);
                final int roomID = pref3.getInt("roomID", 0);

                // Creating an Alert Dialog Builder that will take a user input
                AlertDialog.Builder db = new AlertDialog.Builder(LandlordEditRoomActivity.this);
                db.setTitle("Offer Room");
                db.setIcon(R.drawable.ic_add_property_manager);
                db.setMessage("Please input the username of the Tenant");
                final EditText tvTenantUsername = new EditText(LandlordEditRoomActivity.this);
                tvTenantUsername.setInputType(InputType.TYPE_CLASS_TEXT);
                db.setView(tvTenantUsername);
                db.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tenantUsername = tvTenantUsername.getText().toString();
                        // Created a list of offered rooms for tenants
                        InsertRoomOffered iro = new InsertRoomOffered(LandlordEditRoomActivity.this, LandlordEditRoomActivity.this, roomID, tenantUsername, managerUsername, "Active");
                        iro.execute(Settings.URL_ADDRESS_INSERT_ROOM_OFFERED);
                    }
                });
                db.setNegativeButton(android.R.string.no, null);
                db.show();
            }
        });

    }

    // pause the thread
    @Override
    protected void onPause() {
        isAlive = false;
        position = roomThread.getPosition();
        super.onPause();
    }

    // resume the thread
    @Override
    protected void onResume() {
        isAlive = true;
        roomThread.setPosition(position);
        super.onPostResume();
    }

    // ready for garbage collection
    @Override
    protected void onDestroy() {
        roomThread = null;
        super.onDestroy();
    }

    // Storing data's into JSON Object
    public String getRoomDetailsJsonObject() {
        roomDetails = new JSONObject();
        try {
            roomDetails.put("roomPrice", tvRoomPrice.getText().toString());
            roomDetails.put("address1", tvFlatStreet.getText().toString());
            roomDetails.put("address2", tvSuburb.getText().toString());
            roomDetails.put("bedroom", spinBedroom.getSelectedItem().toString());
            roomDetails.put("bathroom", spinBathroom.getSelectedItem().toString());
            roomDetails.put("parking", spinParking.getSelectedItem().toString());
            if (rdioAlarmYes.isChecked()) {
                roomDetails.put("smokeAlarm", "Yes");
            } else if (rdioAlarmNo.isChecked()) {
                roomDetails.put("smokeAlarm", "No");
            }
            roomDetails.put("specificDetails", tvSpecificDetails.getText().toString());
            roomDetails.put("furnishings", tvFurnishing.getText().toString());
            roomDetails.put("utilities", tvUtilities.getText().toString());
            roomDetails.put("availableDate", tvAvailableDate.getText().toString());
            roomDetails.put("occupancy", spinTenantCount.getSelectedItem().toString());
            if (rdioPetsYes.isChecked()) {
                roomDetails.put("pets", "Yes");
            } else if (rdioPetsNo.isChecked()) {
                roomDetails.put("pets", "No");
            } else if (rdioPetsNeg.isChecked()) {
                roomDetails.put("pets", "Negotiable");
            }
            if (rdioSmokersYes.isChecked()) {
                roomDetails.put("smokers", "Yes");
            } else if (rdioSmokersNo.isChecked()) {
                roomDetails.put("smokers", "No");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return roomDetails.toString();
    }

    // Setting up the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_edit_property, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // if Edit button is clicked, the fields will become editable
        if (id == R.id.editPropertyInfo) {
            fh.setEditableTrue(fields);
            for (Spinner spin : spinners) {
                spin.setEnabled(true);
            }
            for (TextView field : fields) {
                field.setTextColor(Color.BLACK);
            }
        }
        // if Save button is clicked, the fields will become uneditable
        // and the property details will be updated
        if (id == R.id.savePropertyInfo) {
            fh.setEditableFalse(fields);
            for (Spinner spin : spinners) {
                spin.setEnabled(false);
            }
            for (TextView field : fields) {
                field.setTextColor(Color.GRAY);
            }
            SharedPreferences pref1 = getSharedPreferences("ROOM_ID", MODE_PRIVATE);
            roomID = pref1.getInt("roomID", 0);
            Log.d("roomID->", String.valueOf(roomID));

            UpdateRoomInformation uri = new UpdateRoomInformation(LandlordEditRoomActivity.this, roomID);
            uri.execute(Settings.URL_ADDRESS_UPDATE_ROOM_INFORMATION);
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();
        }
        // deleting the property
        if (id == R.id.deleteProperty) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Room")
                    .setIcon(R.drawable.delete_icon)
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

    /**
     * @param roomDetails array information of room details
     *                    <p>
     *                    Set the fields base on the information on the database
     */
    @Override
    public void processFinish(String[] roomDetails) {
        SharedPreferences.Editor editor = getSharedPreferences("ROOM_ID", MODE_PRIVATE).edit();
        editor.putInt("roomID", Integer.parseInt(roomDetails[0]));
        editor.apply();
        tvRoomPrice.setText(roomDetails[1]);
        tvFlatStreet.setText(roomDetails[2]);
        tvSuburb.setText(roomDetails[3]);
        if (roomDetails[4].equals("Choose")) {
        } else {
            spinBedroom.setSelection(Integer.parseInt(roomDetails[4]));
        }
        if (roomDetails[5].equals("Choose")) {
        } else {
            spinBathroom.setSelection(Integer.parseInt(roomDetails[5]));
        }
        if (roomDetails[6].equals("Choose")) {
        } else {
            spinParking.setSelection(Integer.parseInt(roomDetails[6]));
        }
        if (roomDetails[7].equals("Yes")) {
            rdioAlarmYes.setChecked(true);
        } else {
            rdioAlarmNo.setChecked(true);
        }
        tvSpecificDetails.setText(roomDetails[8]);
        tvFurnishing.setText(roomDetails[9]);
        tvUtilities.setText(roomDetails[10]);
        tvAvailableDate.setText(roomDetails[11]);
        if (roomDetails[12].equals("Single")) {
            spinTenantCount.setSelection(1);
        } else if (roomDetails[12].equals("Double")) {
            spinTenantCount.setSelection(2);
        } else {
            spinTenantCount.setSelection(3);
        }
        if (roomDetails[13].equals("Yes")) {
            rdioPetsYes.setChecked(true);
        } else if (roomDetails[13].equals("No")) {
            rdioPetsNo.setChecked(true);
        } else {
            rdioPetsNeg.setChecked(true);
        }
        if (roomDetails[14].equals("Yes")) {
            rdioSmokersYes.setChecked(true);
        } else {
            rdioSmokersNo.setChecked(true);
        }
    }

    /**
     * @param response a response from the database if the room is already
     *                 offered or already unavailable and if the username is
     *                 not existing and the username is not a tenant
     */
    @Override
    public void processFinish(String response) {
        if (response.equals("Not Found")) {
            new AlertDialog.Builder(LandlordEditRoomActivity.this)
                    .setTitle("Error")
                    .setIcon(R.drawable.ic_no_records_found)
                    .setMessage("This username doesn't exist")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        } else if (response.equals("Already Offered")) {
            new AlertDialog.Builder(LandlordEditRoomActivity.this)
                    .setTitle("Error")
                    .setIcon(R.drawable.ic_no_records_found)
                    .setMessage("Room is already offered to this user")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        } else if (response.equals("Room Unavailable")) {
            new AlertDialog.Builder(LandlordEditRoomActivity.this)
                    .setTitle("Error")
                    .setIcon(R.drawable.ic_no_records_found)
                    .setMessage("Room is already occupied")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        } else if (response.equals("Not Tenant")) {
            new AlertDialog.Builder(LandlordEditRoomActivity.this)
                    .setTitle("Error")
                    .setIcon(R.drawable.ic_no_records_found)
                    .setMessage("This user is not a Tenant")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        } else {
            Toast.makeText(LandlordEditRoomActivity.this, "Room offered successfully", Toast.LENGTH_LONG).show();
        }

    }
}
