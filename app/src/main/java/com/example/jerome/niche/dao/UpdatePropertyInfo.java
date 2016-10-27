package com.example.jerome.niche.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;

import com.example.jerome.niche.activities.LandlordEditPropertyActivity;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 27/10/2016.
 */

public class UpdatePropertyInfo extends AsyncTask<String, Void, Void> {
    private LandlordEditPropertyActivity lepa;
    private String userID;
    public UpdatePropertyInfo(String userID, LandlordEditPropertyActivity lepa){
        this.lepa = lepa;
        this.userID = userID;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passUserID = URLEncoder.encode("userID", "UTF-8");
            passUserID += "=" + URLEncoder.encode(userID, "UTF-8");
            String passPropertyInfoObject = URLEncoder.encode("propertyInfoObject", "UTF-8");
            passPropertyInfoObject += "=" + URLEncoder.encode(lepa.propertyInfoJsonObject(), "UTF-8");

            Log.d("userID", userID);
            Log.d("propInfo", lepa.propertyInfoJsonObject());
            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passUserID + "&" + passPropertyInfoObject);
            os.flush();
            con.getInputStream();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
