package com.kappatid.jerome.niche.dao;

import android.os.AsyncTask;

import com.kappatid.jerome.niche.activities.LandlordCreateRoomActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Kappatid LLC
 * @title DeleteRoom.java
 * @purpose This class will serve as a middle tier for Inserting a room record
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class InsertRoom extends AsyncTask<String, Void, Void> {
    LandlordCreateRoomActivity lcra;

    public InsertRoom(LandlordCreateRoomActivity lcra) {
        this.lcra = lcra;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passRoom = URLEncoder.encode("roomObject", "UTF-8");
            passRoom += "=" + URLEncoder.encode(lcra.getRoomDetailsJsonObject(), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passRoom);
            os.flush();
            con.getInputStream();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
