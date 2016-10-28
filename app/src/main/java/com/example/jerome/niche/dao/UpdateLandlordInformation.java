package com.example.jerome.niche.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerome.niche.activities.LandlordPersonalInformationActivity;
import com.example.jerome.niche.activities.TenantProfileActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 28/10/2016.
 */

public class UpdateLandlordInformation extends AsyncTask<String, Void, Void> {
    private LandlordPersonalInformationActivity lpia;
    private String landlordID;

    public UpdateLandlordInformation(LandlordPersonalInformationActivity lpia, String landlordID){
        this.lpia = lpia;
        this.landlordID = landlordID;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();
            String sendLandlordID = URLEncoder.encode("landlordID", "UTF-8");
            sendLandlordID += "=" + URLEncoder.encode(landlordID, "UTF-8");
            String alterLandlord = URLEncoder.encode("landlordObject", "UTF-8");
            alterLandlord += "=" + URLEncoder.encode(lpia.getLandlordJsonObject(), "UTF-8");
            Log.d("landlordID", landlordID);

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(sendLandlordID + "&" + alterLandlord);
            os.flush();
            con.getInputStream();

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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
