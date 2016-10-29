package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 29/10/2016.
 */

public class LoadRoomInfo extends AsyncTask<String, String, Void> {
    private Context context;
    private String address;
    private int roomNum;
    private Spinner spinBedroom;
    private Spinner spinBathroom;
    private Spinner spinParking;
    private Spinner spinTenantCount;
    private RadioButton rdioAlarmYes;
    private RadioButton rdioAlarmNo;
    private RadioButton rdioPetsYes;
    private RadioButton rdioPetsNo;
    private RadioButton rdioPetsNeg;
    private RadioButton rdioSmokersYes;
    private RadioButton rdioSmokersNo;
    private TextView[] roomInfo;
    private ProgressDialog pd;
    public LoadRoomInfo(Context context, String address, int roomNum, Spinner spinBedroom, Spinner spinBathroom,
                        Spinner spinParking, Spinner spinTenantCount, RadioButton rdioAlarmYes, RadioButton rdioAlarmNo,
                        RadioButton rdioPetsYes, RadioButton rdioPetsNo, RadioButton rdioPetsNeg,RadioButton rdioSmokersYes,
                        RadioButton rdioSmokersNo, TextView... roomInfo){
        this.context = context;
        this.address = address;
        this.roomNum = roomNum;
        this.spinBedroom = spinBedroom;
        this.spinBathroom = spinBathroom;
        this.spinParking = spinParking;
        this.spinTenantCount = spinTenantCount;
        this.rdioAlarmYes = rdioAlarmYes;
        this.rdioAlarmNo = rdioAlarmNo;
        this.rdioPetsYes = rdioPetsYes;
        this.rdioPetsNo = rdioPetsNo;
        this.rdioPetsNeg = rdioPetsNeg;
        this.rdioSmokersYes = rdioSmokersYes;
        this.rdioSmokersNo = rdioSmokersNo;
        this.roomInfo = roomInfo;
    }
    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading Records");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passAddress = URLEncoder.encode("address", "UTF-8");
            passAddress += "=" + URLEncoder.encode(address, "UTF-8");
            Log.d("Addressssssssssssssss", address);

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passAddress);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while((line = br.readLine()) != null){
                String[] roomDetails = line.split("_");
                publishProgress(roomDetails[roomNum]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        String[] roomDetails = values[0].split("-");
        //tvRoomPrice, tvFlatStreet, tvSuburb, rdioAlarmYes, rdioAlarmNo,
        //tvSpecificDetails, tvFurnishing, tvUtilities, tvAvailableDate, rdioPetsYes, rdioPetsNo,
        //        rdioPetsNeg, rdioSmokersYes, rdioSmokersNo
        roomInfo[0].setText(roomDetails[1]);
        roomInfo[1].setText(roomDetails[2]);
        roomInfo[2].setText(roomDetails[3]);
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
        roomInfo[3].setText(roomDetails[8]);
        roomInfo[4].setText(roomDetails[9]);
        roomInfo[5].setText(roomDetails[10]);
        roomInfo[6].setText(roomDetails[11]);
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
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        super.onPostExecute(aVoid);
    }
}
