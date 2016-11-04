package com.kappatid.jerome.niche.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * for Loading the property information
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LoadPropertyInformation extends AsyncTask<String, String, Void> {

    private Context context;
    private TextView[] propertyInfo;
    private int rowNum;
    private String userID;

    public LoadPropertyInformation(Context context, int rowNum, String userID, TextView... propertyInfo) {
        this.context = context;
        this.rowNum = rowNum;
        this.userID = userID;
        this.propertyInfo = propertyInfo;

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

            String passUserID = URLEncoder.encode("userID", "UTF-8");
            passUserID += "=" + URLEncoder.encode(userID, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passUserID);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] propertyDetails = line.split("_");
                publishProgress(propertyDetails[rowNum]);

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
        String[] propertyDetails = values[0].split("-");
        Log.d("propertyDetails", propertyDetails.toString());
        propertyInfo[0].setText(propertyDetails[0]);
        propertyInfo[1].setText(propertyDetails[1]);
        propertyInfo[2].setText(propertyDetails[2]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
