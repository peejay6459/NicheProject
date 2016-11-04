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
 * for Loading the Property Manager's list
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LoadPropertyManagerList extends AsyncTask<String, String, Void> {
    public interface AsyncResponse {
        void processFinish(ArrayList<String> managerName);
    }

    private AsyncResponse delegate = null;
    private Context context;
    private ArrayList<String> managerName;
    private String landlordID;
    private ProgressDialog pd;

    public LoadPropertyManagerList(Context context, AsyncResponse delegate, ArrayList<String> managerName, String landlordID) {
        this.context = context;
        this.delegate = delegate;
        this.managerName = managerName;
        this.landlordID = landlordID;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading Manager List");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passLandlordID = URLEncoder.encode("landlordID", "UTF-8");
            passLandlordID += "=" + URLEncoder.encode(landlordID, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passLandlordID);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] managerNames = line.split("_");
                for (String name : managerNames) {
                    publishProgress(name);
                    Log.d("name", name);
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
        managerName.add(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(managerName);
    }
}
