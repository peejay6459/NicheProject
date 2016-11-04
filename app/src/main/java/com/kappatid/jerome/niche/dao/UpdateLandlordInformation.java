package com.kappatid.jerome.niche.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.kappatid.jerome.niche.activities.LandlordPersonalInformationActivity;

import java.io.IOException;
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
 * for Updating the Landlord Information
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class UpdateLandlordInformation extends AsyncTask<String, Void, Void> {
    private LandlordPersonalInformationActivity lpia;
    private String landlordID;

    public UpdateLandlordInformation(LandlordPersonalInformationActivity lpia, String landlordID) {
        this.lpia = lpia;
        this.landlordID = landlordID;
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
}
