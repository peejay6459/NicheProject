package com.kappatid.jerome.niche.dao;

import android.app.ProgressDialog;
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

/**
 * @author Kappatid LLC
 * @title DeleteRoom.java
 * @purpose This class will serve as a middle tier
 * for Loading the room information
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LoadRoomInformation extends AsyncTask<String, String, Void> {
    public interface AsyncResponse {
        void processFinish(String[] roomDetails);
    }

    private AsyncResponse delegate;
    private Context context;
    private String address;
    private int roomNum;
    private ProgressDialog pd;
    private String[] roomDetails;
    private String userType;
    private String username;

    public LoadRoomInformation(AsyncResponse delegate, Context context, String address, int roomNum, String userType, String username) {
        this.delegate = delegate;
        this.context = context;
        this.address = address;
        this.roomNum = roomNum;
        this.userType = userType;
        this.username = username;
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
            String passUserType = URLEncoder.encode("userType", "UTF-8");
            passUserType += "=" + URLEncoder.encode(userType, "UTF-8");
            String passUsername = URLEncoder.encode("username", "UTF-8");
            passUsername += "=" + URLEncoder.encode(username, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passAddress + "&" + passUserType + "&" + passUsername);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] roomDetails = line.split("_");
                publishProgress(roomDetails[roomNum]);
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
        roomDetails = values[0].split("-");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(roomDetails);
    }
}
