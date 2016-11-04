package com.kappatid.jerome.niche.dao;

import android.content.Context;
import android.os.AsyncTask;

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
 * @purpose This class will serve as a middle tier
 * for Updating the Rooms Offered
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class UpdateRoomsOffered extends AsyncTask<String, Void, Void> {
    private Context context;
    private String status;
    private int roomOfferedID;

    public UpdateRoomsOffered(Context context, String status, int roomOfferedID) {
        this.context = context;
        this.status = status;
        this.roomOfferedID = roomOfferedID;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();
            String passStatus = URLEncoder.encode("status", "UTF-8");
            passStatus += "=" + URLEncoder.encode(status, "UTF-8");
            String passRoomOfferedID = URLEncoder.encode("roomOfferedID", "UTF-8");
            passRoomOfferedID += "=" + URLEncoder.encode(String.valueOf(roomOfferedID), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passStatus + "&" + passRoomOfferedID);
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
