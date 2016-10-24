package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.jerome.niche.activities.TenantProfileActivity;
import com.example.jerome.niche.classes.Tenant;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 23/10/2016.
 */

public class LoadTenantInformation extends AsyncTask<String, String, Tenant> {

    TextView[] tenantField;
    TenantProfileActivity tpa;
    ProgressDialog pd;
    String tenantID;
    public LoadTenantInformation(TenantProfileActivity tpa, String tenantID, TextView... tenantField){
        this.tpa = tpa;
        this.tenantID = tenantID;
        this.tenantField = tenantField;
    }
    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(tpa);
        pd.setTitle("Loading Records");
        pd.setMessage("Loading, Please wat");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Tenant doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passTenantID = URLEncoder.encode("tenantID", "UTF-8");
            passTenantID += "=" + URLEncoder.encode(tenantID, "UTF-8");
            Log.d("Passing the tenantID: ", String.valueOf(tenantID));

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passTenantID);
            os.flush();
            con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONArray tenantInfo = new JSONArray(sb.toString());
            for (int i = 0; i < tenantInfo.length(); i++) {
                JSONObject tenantData = tenantInfo.getJSONObject(i);
                publishProgress(tenantData.getString("tenant_ID"), tenantData.getString("tenant_name"), tenantData.getString("tenant_gender"), tenantData.getString("tenant_dob"),
                        tenantData.getString("tenant_phone"), tenantData.getString("tenant_mobile"), tenantData.getString("tenant_address"), tenantData.getString("tenant_country"),
                        tenantData.getString("tenant_passport_number"), tenantData.getString("tenant_id_num"), tenantData.getString("tenant_previous_country"),
                        tenantData.getString("tenant_relative_name"), tenantData.getString("tenant_relative_relationship"), tenantData.getString("tenant_relative_address"),
                        tenantData.getString("tenant_relative_contact"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        //Tenant tenant = new Tenant(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], values[12], values[13]);
        tenantField[0].setText(values[1]);
        tenantField[1].setText(values[3]);
        tenantField[2].setText(values[4]);
        tenantField[3].setText(values[5]);
        tenantField[4].setText(values[7]);
        tenantField[5].setText(values[8]);
        tenantField[6].setText(values[9]);
        tenantField[7].setText(values[10]);
        tenantField[8].setText(values[11]);
        tenantField[9].setText(values[12]);
        tenantField[10].setText(values[14]);

        //new TenantProfileActivity(tenant);
        //new TenantProfileActivity(tenant);

    }

    @Override
    protected void onPostExecute(Tenant tenant) {
        pd.dismiss();
        super.onPostExecute(tenant);
    }
}
