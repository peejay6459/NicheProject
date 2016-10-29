package com.example.jerome.niche.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerome.niche.activities.PropertyManagerPersonalInformationActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 30/10/2016.
 */

public class UpdatePropertyManagerInformation extends AsyncTask<String, Void, Void> {
    PropertyManagerPersonalInformationActivity pmpi;
    String propertyManagerID;

    public UpdatePropertyManagerInformation(PropertyManagerPersonalInformationActivity pmpi, String propertyManagerID){
        this.pmpi = pmpi;
        this.propertyManagerID = propertyManagerID;
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();
            String passPropertyManagerID = URLEncoder.encode("propertyManagerID", "UTF-8");
            passPropertyManagerID += "=" + URLEncoder.encode(propertyManagerID, "UTF-8");
            String alterPropertyManager = URLEncoder.encode("propertyManagerObject", "UTF-8");
            alterPropertyManager += "=" + URLEncoder.encode(pmpi.getLandlordJsonObject(), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passPropertyManagerID + "&" + alterPropertyManager);
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
