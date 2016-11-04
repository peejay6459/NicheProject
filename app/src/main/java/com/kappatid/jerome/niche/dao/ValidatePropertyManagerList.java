package com.kappatid.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * for Validating the Property Manager list
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class ValidatePropertyManagerList extends AsyncTask<String, String, Void> {
    public interface AsyncResponse {
        void processFinish(String response);
    }

    private AsyncResponse delegate = null;
    private Context context;
    private String managerUsername;
    private String userID;
    private ProgressDialog pd;
    private String response;

    public ValidatePropertyManagerList(Context context, AsyncResponse delegate, String managerUsername, String userID) {
        this.context = context;
        this.delegate = delegate;
        this.managerUsername = managerUsername;
        this.userID = userID;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Insert Record");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();
            Log.d("managerUsername", managerUsername);
            String passManagerUsername = URLEncoder.encode("passManagerUsername", "UTF-8");
            passManagerUsername += "=" + URLEncoder.encode(managerUsername, "UTF-8");
            String passUserID = URLEncoder.encode("landlordID", "UTF-8");
            passUserID += "=" + URLEncoder.encode(userID, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passManagerUsername + "&" + passUserID);
            os.flush();

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
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(response);
    }
}
