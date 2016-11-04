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
 * for Loading the Properties information
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LoadProperties extends AsyncTask<String, String, ArrayList<String>> {
    public interface AsyncResponse {
        void processFinish(ArrayList<String> address2, ArrayList<String> address1);
    }

    private AsyncResponse delegate = null;
    private Context context;
    private ArrayList<String> address2;
    private ArrayList<String> address1;
    private String userID;
    private ProgressDialog pd;

    public LoadProperties(Context context, AsyncResponse delegate, ArrayList<String> address2, ArrayList<String> address1, String userID) {
        this.context = context;
        this.delegate = delegate;
        this.address2 = address2;
        this.address1 = address1;
        this.userID = userID;
    }


    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading Properties");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passUserID = URLEncoder.encode("userID", "UTF-8");
            passUserID += "=" + URLEncoder.encode(userID, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passUserID);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] properties = line.split("_");
                for (String prop : properties) {
                    publishProgress(prop);
                    Log.d("PROP ", prop);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address2;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        final String[] properties = values[0].split("-");
        Log.d("Address: ", properties[1]);
        address1.add(String.valueOf(properties[1]));
        address2.add(String.valueOf(properties[2]));


        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<String> address2) {
        pd.dismiss();
        delegate.processFinish(address1, address2);
    }
}
