package com.example.jerome.niche.dao;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerome.niche.activities.LandlordPersonalInformationActivity;
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
 * Created by Jerome on 28/10/2016.
 */

public class LoadLandlordInformation extends AsyncTask<String, String, Void> {
    private TextView[] landlordField;
    private LandlordPersonalInformationActivity lpia;
    private ProgressDialog pd;
    private String landlordID;
    private RadioButton rdioMale;
    private RadioButton rdioFemale;
    public LoadLandlordInformation(LandlordPersonalInformationActivity lpia, String landlordID, RadioButton rdioMale, RadioButton rdioFemale, TextView... landlordField){
        this.lpia = lpia;
        this.landlordID = landlordID;
        this.rdioMale = rdioMale;
        this.rdioFemale = rdioFemale;
        this.landlordField = landlordField;
    }
    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(lpia);
        pd.setTitle("Loading Records");
        pd.setMessage("Loading, Please wat");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passTenantID = URLEncoder.encode("landlordID", "UTF-8");
            passTenantID += "=" + URLEncoder.encode(landlordID, "UTF-8");
            Log.d("Passing the tenantID: ", String.valueOf(landlordID));

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

            JSONArray landlordInfo = new JSONArray(sb.toString());
            for (int i = 0; i < landlordInfo.length(); i++) {
                JSONObject landlordData = landlordInfo.getJSONObject(i);
                publishProgress(landlordData.getString("landlord_ID"), landlordData.getString("landlord_name"), landlordData.getString("landlord_gender"),
                        landlordData.getString("landlord_dob"), landlordData.getString("landlord_phone"), landlordData.getString("landlord_mobile"),
                        landlordData.getString("landlord_address1"), landlordData.getString("landlord_address2"), landlordData.getString("landlord_suburb"),
                        landlordData.getString("landlord_city"), landlordData.getString("landlord_country"));
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
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        super.onPostExecute(aVoid);
    }

    public void setInformation(String... values){
        landlordField[0].setText(values[1]);
        if(values[2].equals("Male")){
            rdioMale.setChecked(true);
            rdioFemale.setChecked(false);
        }else if(values[2].equals("Female")){
            rdioFemale.setChecked(true);
            rdioMale.setChecked(false);
        }
        landlordField[1].setText(values[3]);
        landlordField[2].setText(values[4]);
        landlordField[3].setText(values[5]);
        landlordField[4].setText(values[6]);
        landlordField[5].setText(values[7]);
        landlordField[6].setText(values[8]);
        landlordField[7].setText(values[9]);
        landlordField[8].setText(values[10]);
    }
}
