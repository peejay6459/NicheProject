package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerome.niche.activities.TenantPersonalInformationActivity;
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

    private TextView[] tenantField;
    private TenantPersonalInformationActivity tpa;
    private ProgressDialog pd;
    private String tenantID;
    private RadioButton rdioMale;
    private RadioButton rdioFemale;
    public LoadTenantInformation(TenantPersonalInformationActivity tpa, String tenantID, RadioButton rdioMale, RadioButton rdioFemale, TextView... tenantField){
        this.tpa = tpa;
        this.tenantID = tenantID;
        this.rdioMale = rdioMale;
        this.rdioFemale = rdioFemale;
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
                publishProgress(tenantData.getString("tenant_ID"), tenantData.getString("tenant_name"), tenantData.getString("tenant_gender"),
                        tenantData.getString("tenant_dob"), tenantData.getString("tenant_phone"), tenantData.getString("tenant_mobile"),
                        tenantData.getString("tenant_address1"), tenantData.getString("tenant_address2"), tenantData.getString("tenant_suburb"),
                        tenantData.getString("tenant_city"), tenantData.getString("tenant_country"), tenantData.getString("tenant_passport_number"),
                        tenantData.getString("tenant_id_num"), tenantData.getString("tenant_previous_country"),
                        tenantData.getString("tenant_relative_name"), tenantData.getString("tenant_relative_relationship"),
                        tenantData.getString("tenant_relative_address1"), tenantData.getString("tenant_relative_address2"),
                        tenantData.getString("tenant_relative_suburb"), tenantData.getString("tenant_relative_city"),
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
        setInformation(values);

    }

    @Override
    protected void onPostExecute(Tenant tenant) {
        pd.dismiss();
        super.onPostExecute(tenant);
    }

    public void setInformation(String... values){
        tenantField[0].setText(values[1]);
        if(values[2].equals("Male")){
            rdioMale.setChecked(true);
            rdioFemale.setChecked(false);
        }else if(values[2].equals("Female")){
            rdioFemale.setChecked(true);
            rdioMale.setChecked(false);
        }
        tenantField[1].setText(values[3]);
        tenantField[2].setText(values[4]);
        tenantField[3].setText(values[5]);
        tenantField[4].setText(values[6]);
        tenantField[5].setText(values[7]);
        tenantField[6].setText(values[8]);
        tenantField[7].setText(values[9]);
        tenantField[8].setText(values[10]);
        tenantField[9].setText(values[11]);
        tenantField[10].setText(values[12]);
        tenantField[11].setText(values[13]);
        tenantField[12].setText(values[14]);
        tenantField[13].setText(values[15]);
        tenantField[14].setText(values[16]);
        tenantField[15].setText(values[17]);
        tenantField[16].setText(values[18]);
        tenantField[17].setText(values[19]);
        tenantField[18].setText(values[20]);

    }

}
