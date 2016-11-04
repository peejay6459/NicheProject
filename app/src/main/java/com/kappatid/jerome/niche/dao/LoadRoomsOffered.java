package com.kappatid.jerome.niche.dao;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title DeleteRoom.java
 * @purpose This class will serve as a middle tier
 * for Loading the Rooms Offered
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LoadRoomsOffered extends AsyncTask<String, String, Void> {
    public interface AsyncResponse {
        void processFinish(ArrayList<String> price, ArrayList<String> occupancy, String roomOfferID);
    }

    private Context context;
    private AsyncResponse delegate;
    private String username;
    private ArrayList<String> price;
    private ArrayList<String> occupancy;
    private String roomOfferID;

    public LoadRoomsOffered(Context context, AsyncResponse delegate, String username, ArrayList<String> price, ArrayList<String> occupancy) {
        this.context = context;
        this.delegate = delegate;
        this.username = username;
        this.price = price;
        this.occupancy = occupancy;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passUsername = URLEncoder.encode("username", "UTF-8");
            passUsername += "=" + URLEncoder.encode(username, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passUsername);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] rooms = line.split("_");
                for (String room : rooms) {
                    publishProgress(room);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        final String[] rooms = values[0].split("-");
        price.add(String.valueOf(rooms[0]));
        occupancy.add(String.valueOf(rooms[1]));
        roomOfferID = rooms[2];
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        delegate.processFinish(price, occupancy, roomOfferID);
    }
}
