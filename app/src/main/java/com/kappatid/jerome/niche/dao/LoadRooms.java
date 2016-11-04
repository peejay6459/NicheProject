package com.kappatid.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * for Loading the Rooms
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LoadRooms extends AsyncTask<String, String, Void> {
    public interface AsyncResponse {
        void processFinish(ArrayList<String> price, ArrayList<String> occupancy);
    }

    private AsyncResponse delegate;
    private Context context;
    private ArrayList<String> price;
    private ArrayList<String> occupancy;
    private String propAddress;
    private ProgressDialog pd;
    private String userType;
    private String username;

    public LoadRooms(Context context, AsyncResponse delegate, ArrayList<String> price, ArrayList<String> occupancy, String propAddress, String userType, String username) {
        this.context = context;
        this.delegate = delegate;
        this.price = price;
        this.occupancy = occupancy;
        this.propAddress = propAddress;
        this.userType = userType;
        this.username = username;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading Rooms");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passPropAddress = URLEncoder.encode("propAddress", "UTF-8");
            passPropAddress += "=" + URLEncoder.encode(propAddress, "UTF-8");
            String passUserType = URLEncoder.encode("userType", "UTF-8");
            passUserType += "=" + URLEncoder.encode(userType, "UTF-8");
            String passUsername = URLEncoder.encode("username", "UTF-8");
            passUsername += "=" + URLEncoder.encode(username, "UTF-8");
            Log.d("username", username);

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passPropAddress + "&" + passUserType + "&" + passUsername);
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
        price.add(String.valueOf(rooms[1]));
        occupancy.add(String.valueOf(rooms[2]));
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(price, occupancy);
    }
}
