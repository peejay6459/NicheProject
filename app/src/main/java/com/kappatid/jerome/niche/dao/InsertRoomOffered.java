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
 * @purpose This class will serve as a middle tier for Inserting a room offered record
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */
public class InsertRoomOffered extends AsyncTask<String, Void, Void> {
    public interface AsyncResponse {
        void processFinish(String response);
    }

    private AsyncResponse delegate;
    private Context context;
    private int roomID;
    private String tenantUsername;
    private String managerUsername;
    private String status;
    private String response;
    private ProgressDialog pd;

    public InsertRoomOffered(Context context, AsyncResponse delegate, int roomID, String tenantUsername, String managerUsername, String status) {
        this.context = context;
        this.delegate = delegate;
        this.roomID = roomID;
        this.tenantUsername = tenantUsername;
        this.managerUsername = managerUsername;
        this.status = status;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Validating Account");
        pd.setMessage("Checking the username if existing");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passRoomID = URLEncoder.encode("roomID", "UTF-8");
            passRoomID += "=" + URLEncoder.encode(String.valueOf(roomID), "UTF-8");
            String passTenantUsername = URLEncoder.encode("tenantUsername", "UTF-8");
            passTenantUsername += "=" + URLEncoder.encode(tenantUsername, "UTF-8");
            String passManagerUsername = URLEncoder.encode("managerUsername", "UTF-8");
            passManagerUsername += "=" + URLEncoder.encode(managerUsername, "UTF-8");
            String passStatus = URLEncoder.encode("status", "UTF-8");
            passStatus += "=" + URLEncoder.encode(status, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passRoomID + "&" + passTenantUsername + "&" + passManagerUsername + "&" + passStatus);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                response = line;
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
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(response);
    }
}
