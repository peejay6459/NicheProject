package com.example.jerome.niche.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerome.niche.activities.LandlordEditPropertyActivity;
import com.example.jerome.niche.activities.LandlordEditRoomActivity;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 29/10/2016.
 */

public class DeleteRoom extends AsyncTask<String, Void, Void> {
    private int roomID;
    public DeleteRoom(int roomID){
        this.roomID = roomID;
    }

    @Override
    protected Void doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passRoomID = URLEncoder.encode("roomID", "UTF-8");
            passRoomID += "=" + URLEncoder.encode(String.valueOf(roomID), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passRoomID);
            os.flush();
            con.getInputStream();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
