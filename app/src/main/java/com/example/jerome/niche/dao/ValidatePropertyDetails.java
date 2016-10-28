package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 28/10/2016.
 */

public class ValidatePropertyDetails extends AsyncTask<String, String, Void> {
    public interface AsyncResponse{
        void processFinish(String... address);
    }

    private AsyncResponse delegate;
    private Context context;
    private int rowNum;
    private String userID;
    private ProgressDialog pd;
    private String propertyAddress1;
    private String propertyAddress2;
    public ValidatePropertyDetails(Context context, int rowNum, String userID, AsyncResponse delegate){
        this.context = context;
        this.rowNum = rowNum;
        this.userID = userID;
        this.delegate = delegate;
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
            while((line = br.readLine()) != null){
                String[] propertyDetails = line.split("_");
                publishProgress(propertyDetails[rowNum]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        String[] propertyDetails = values[0].split("-");
        Log.d("propertyDetails", propertyDetails.toString());
        propertyAddress1 = propertyDetails[1];
        propertyAddress2 = propertyDetails[2];
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(propertyAddress1, propertyAddress2);
    }
}
