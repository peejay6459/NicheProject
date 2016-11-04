package com.kappatid.jerome.niche.dao;

import android.os.AsyncTask;

import com.kappatid.jerome.niche.activities.LandlordEditPropertyActivity;

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
 * for Updating the Property Information
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class UpdatePropertyInformation extends AsyncTask<String, Void, Void> {
    private LandlordEditPropertyActivity lepa;
    private String userID;

    public UpdatePropertyInformation(String userID, LandlordEditPropertyActivity lepa) {
        this.lepa = lepa;
        this.userID = userID;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passUserID = URLEncoder.encode("userID", "UTF-8");
            passUserID += "=" + URLEncoder.encode(userID, "UTF-8");
            String passPropertyInfoObject = URLEncoder.encode("propertyInfoObject", "UTF-8");
            passPropertyInfoObject += "=" + URLEncoder.encode(lepa.propertyInfoJsonObject(), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passUserID + "&" + passPropertyInfoObject);
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
