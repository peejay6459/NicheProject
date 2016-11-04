package com.kappatid.jerome.niche.dao;

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
 * @purpose This class will serve as a middle tier for Deleting a room
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class DeleteRoom extends AsyncTask<String, Void, Void> {
    private int roomID;

    public DeleteRoom(int roomID) {
        this.roomID = roomID;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passRoomID = URLEncoder.encode("roomID", "UTF-8");
            passRoomID += "=" + URLEncoder.encode(String.valueOf(roomID), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passRoomID);
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
