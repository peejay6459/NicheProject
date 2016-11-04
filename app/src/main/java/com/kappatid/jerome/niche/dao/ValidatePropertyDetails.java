package com.kappatid.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
 * for Validating the Property Details
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class ValidatePropertyDetails extends AsyncTask<String, String, Void> {
    public interface AsyncResponse {
        void processFinish(String... address);
    }

    private AsyncResponse delegate;
    private Context context;
    private int rowNum;
    private String userID;
    private ProgressDialog pd;
    private String propertyAddress1;
    private String propertyAddress2;
    private TextView propertyManagerName;
    private String managerName;

    public ValidatePropertyDetails(Context context, int rowNum, String userID, AsyncResponse delegate, TextView propertyManagerName) {
        this.context = context;
        this.rowNum = rowNum;
        this.userID = userID;
        this.delegate = delegate;
        this.propertyManagerName = propertyManagerName;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading Property Info");
        pd.setMessage("Please wait");
        pd.show();
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

                String[] details = propertyDetails[rowNum].split("-");
                propertyAddress1 = details[1];
                propertyAddress2 = details[2];
            }
            try {
                URL url1 = new URL(params[1]);
                URLConnection con1 = url1.openConnection();
                String passAddress = URLEncoder.encode("address", "UTF-8");
                passAddress += "=" + URLEncoder.encode(propertyAddress1, "UTF-8");
                con1.setDoOutput(true);
                os = new OutputStreamWriter(con1.getOutputStream());
                os.write(passAddress);
                os.flush();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
                String line1;
                line1 = br1.readLine();
                String[] names = line1.split("_");
                publishProgress(names);
            } catch (Exception e) {
                e.printStackTrace();
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
        managerName = values[0];

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        propertyManagerName.setText(managerName);
        pd.dismiss();
        delegate.processFinish(propertyAddress1, propertyAddress2);
    }
}
