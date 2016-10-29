package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 29/10/2016.
 */

public class LoadRoomInformation extends AsyncTask<String, String, Void> {
    public interface AsyncResponse{
        void processFinish(String[] roomDetails);
    }
    private AsyncResponse delegate;
    private Context context;
    private String address;
    private int roomNum;
    private ProgressDialog pd;
    private String[] roomDetails;
    public LoadRoomInformation(AsyncResponse delegate, Context context, String address, int roomNum){
        this.delegate = delegate;
        this.context = context;
        this.address = address;
        this.roomNum = roomNum;
    }
    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading Records");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passAddress = URLEncoder.encode("address", "UTF-8");
            passAddress += "=" + URLEncoder.encode(address, "UTF-8");
            Log.d("Addressssssssssssssss", address);

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passAddress);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while((line = br.readLine()) != null){
                String[] roomDetails = line.split("_");
                publishProgress(roomDetails[roomNum]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        roomDetails = values[0].split("-");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(roomDetails);
    }
}
