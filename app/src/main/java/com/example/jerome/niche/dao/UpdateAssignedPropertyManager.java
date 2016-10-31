package com.example.jerome.niche.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 31/10/2016.
 */

public class UpdateAssignedPropertyManager extends AsyncTask<String, Void, Void> {

    private Context context;
    private String managerName;
    private String address;
    public UpdateAssignedPropertyManager(Context context, String managerName, String address) {
        this.context = context;
        this.managerName = managerName;
        this.address = address;

    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();
            String passManagerName = URLEncoder.encode("managerName", "UTF-8");
            passManagerName += "=" + URLEncoder.encode(managerName, "UTF-8");
            String passAddress = URLEncoder.encode("address", "UTF-8");
            passAddress += "=" + URLEncoder.encode(address, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passManagerName + "&" + passAddress);
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
