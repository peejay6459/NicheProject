package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Jerome on 30/10/2016.
 */

public class ValidatePropertyManagerList extends AsyncTask<String, String, Void> {
    public interface AsyncResponse{
        void processFinish(String response);
    }

    private AsyncResponse delegate = null;
    private Context context;
    private String managerUsername;
    private String userID;
    private ProgressDialog pd;
    private String response;
    public ValidatePropertyManagerList(Context context, AsyncResponse delegate, String managerUsername, String userID){
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
        try{
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
            while((line = br.readLine()) != null){
                String[] myResponse = line.split("_");
                    response = myResponse[0];
            }

        }catch (Exception e){
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
