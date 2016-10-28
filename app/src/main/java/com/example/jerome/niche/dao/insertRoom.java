package com.example.jerome.niche.dao;

import android.os.AsyncTask;

import com.example.jerome.niche.activities.LandlordCreateRoomActivity;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 28/10/2016.
 */

public class InsertRoom extends AsyncTask<String, Void, Void> {
    LandlordCreateRoomActivity lcra;
    public InsertRoom(LandlordCreateRoomActivity lcra){
        this.lcra = lcra;
    }

    @Override
    protected Void doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passRoom = URLEncoder.encode("roomObject", "UTF-8");
            passRoom += "=" + URLEncoder.encode(lcra.getRoomDetailsJsonObject(), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passRoom);
            os.flush();
            con.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
