package com.example.jerome.niche.dao;

import android.os.AsyncTask;

import com.example.jerome.niche.activities.LandlordCreateRoomActivity;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 27/10/2016.
 */

public class InsertProperty extends AsyncTask<String, Void, Void> {
    private LandlordCreateRoomActivity lcra;
    public InsertProperty(LandlordCreateRoomActivity lcra){
        this.lcra = lcra;
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

            String passProperty = URLEncoder.encode("propertyObject", "UTF-8");
            passProperty += "=" + URLEncoder.encode(lcra.getPropertyJsonObject(), "UTF-8");

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

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
