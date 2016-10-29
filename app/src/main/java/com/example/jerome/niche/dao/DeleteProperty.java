package com.example.jerome.niche.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerome.niche.activities.LandlordEditPropertyActivity;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by Jerome on 27/10/2016.
 */

public class DeleteProperty extends AsyncTask<String, Void, Void> {
    private LandlordEditPropertyActivity lepa;
    private String userID;
    public DeleteProperty(String userID, LandlordEditPropertyActivity lepa){
        this.lepa = lepa;
        this.userID = userID;
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

}
