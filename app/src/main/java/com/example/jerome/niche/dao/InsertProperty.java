package com.example.jerome.niche.dao;

import android.os.AsyncTask;

import com.example.jerome.niche.activities.LandlordCreatePropertyActivity;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 27/10/2016.
 */

public class InsertProperty extends AsyncTask<String, Void, Void> {
    private LandlordCreatePropertyActivity lcpa;
    public InsertProperty(LandlordCreatePropertyActivity lcpa){
        this.lcpa = lcpa;
    }

    @Override
    protected Void doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passProperty = URLEncoder.encode("propertyObject", "UTF-8");
            passProperty += "=" + URLEncoder.encode(lcpa.getPropertyJsonObject(), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passProperty);
            os.flush();
            con.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
