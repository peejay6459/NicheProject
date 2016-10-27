package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jerome.niche.activities.LandlordManagePropertiesActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Jerome on 26/10/2016.
 */

public class LoadProperties extends AsyncTask<String, String, ArrayList<String>> {
    public interface AsyncResponse{
        void processFinish(ArrayList<String> address2, ArrayList<String> address1);
    }

    private AsyncResponse delegate = null;
    private Context context;
    private ArrayList<String> address2;
    private ArrayList<String> address1;
    private String userID;
    private ProgressDialog pd;
    public LoadProperties(Context context, AsyncResponse delegate, ArrayList<String> address2, ArrayList<String> address1, String userID){
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
        try{
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
            while((line = br.readLine()) != null){
                String[] properties = line.split("_");
                for(String prop : properties){
                    publishProgress(prop);
                    Log.d("PROP ", prop);
                }
            }

        }catch (Exception e){
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
