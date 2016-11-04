package com.kappatid.jerome.niche.dao;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.kappatid.jerome.niche.activities.LandlordCreatePropertyActivity;

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
 * @purpose This class will serve as a middle tier for Inserting a property record
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class InsertProperty extends AsyncTask<String, Void, Void> {
    public interface AsyncResponse {
        void processFinish(String response);
    }

    private AsyncResponse delegate = null;
    private LandlordCreatePropertyActivity lcpa;
    private String response;
    private ProgressDialog pd;

    public InsertProperty(LandlordCreatePropertyActivity lcpa, AsyncResponse delegate) {
        this.lcpa = lcpa;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(lcpa);
        pd.setTitle("Validating details");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passProperty = URLEncoder.encode("propertyObject", "UTF-8");
            passProperty += "=" + URLEncoder.encode(lcpa.getPropertyJsonObject(), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passProperty);
            os.flush();
            con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] myResponse = line.split("_");
                response = myResponse[0];
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
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(response);
    }

}
