package com.example.jerome.niche.dao;

import android.os.AsyncTask;

import com.example.jerome.niche.activities.TenantProfileActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 24/10/2016.
 */

public class AlterTenantInformation extends AsyncTask <String, Void, Void> {
    TenantProfileActivity tpa;

    public AlterTenantInformation(TenantProfileActivity tpa){
        this.tpa = tpa;
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
            String alterTenant = URLEncoder.encode("tenantObject", "UTF-8");
            alterTenant += "=" + URLEncoder.encode(tpa.getTenantJsonObject(), "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(alterTenant);
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

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
