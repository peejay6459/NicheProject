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

public class LoadPropertyManagerList extends AsyncTask<String, String, Void> {
    public interface AsyncResponse{
        void processFinish(ArrayList<String> managerName);
    }

    private AsyncResponse delegate = null;
    private Context context;
    private ArrayList<String> managerName;
    private String landlordID;
    private ProgressDialog pd;
    public LoadPropertyManagerList(Context context, AsyncResponse delegate, ArrayList<String> managerName, String landlordID){
        this.context = context;
        this.delegate = delegate;
        this.managerName = managerName;
        this.landlordID = landlordID;
    }


    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading Properties");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passLandlordID = URLEncoder.encode("landlordID", "UTF-8");
            passLandlordID += "=" + URLEncoder.encode(landlordID, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passLandlordID);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while((line = br.readLine()) != null){
                String[] managerNames = line.split("_");
                for(String name : managerNames){
                    publishProgress(name);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        final String[] managerNames = values[0].split("-");
        managerName.add(managerNames[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(managerName);
    }
}
