package com.kappatid.jerome.niche.dao;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kappatid.jerome.niche.activities.PropertyManagerPersonalInformationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * for Loading the Property Manager's Information
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class LoadPropertyManagerInformation extends AsyncTask<String, String, Void> {
    private TextView[] propertyManagerField;
    private PropertyManagerPersonalInformationActivity pmpi;
    private ProgressDialog pd;
    private String propertyManagerID;
    private RadioButton rdioMale;
    private RadioButton rdioFemale;

    public LoadPropertyManagerInformation(PropertyManagerPersonalInformationActivity pmpi, String propertyManagerID, RadioButton rdioMale, RadioButton rdioFemale, TextView... propertyManagerField) {
        this.pmpi = pmpi;
        this.propertyManagerID = propertyManagerID;
        this.rdioMale = rdioMale;
        this.rdioFemale = rdioFemale;
        this.propertyManagerField = propertyManagerField;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(pmpi);
        pd.setTitle("Loading Records");
        pd.setMessage("Loading, Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String passPropertyManagerID = URLEncoder.encode("propertyManagerID", "UTF-8");
            passPropertyManagerID += "=" + URLEncoder.encode(propertyManagerID, "UTF-8");

            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(passPropertyManagerID);
            os.flush();
            con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONArray propertyManagerInfo = new JSONArray(sb.toString());
            for (int i = 0; i < propertyManagerInfo.length(); i++) {
                JSONObject propertyManagerData = propertyManagerInfo.getJSONObject(i);
                publishProgress(propertyManagerData.getString("manager_ID"), propertyManagerData.getString("manager_name"), propertyManagerData.getString("manager_gender"),
                        propertyManagerData.getString("manager_dob"), propertyManagerData.getString("manager_phone"), propertyManagerData.getString("manager_mobile"),
                        propertyManagerData.getString("manager_address1"), propertyManagerData.getString("manager_address2"), propertyManagerData.getString("manager_suburb"),
                        propertyManagerData.getString("manager_city"), propertyManagerData.getString("manager_country"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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

    public void setInformation(String... values) {
        propertyManagerField[0].setText(values[1]);
        if (values[2].equals("Male")) {
            rdioMale.setChecked(true);
            rdioFemale.setChecked(false);
        } else if (values[2].equals("Female")) {
            rdioFemale.setChecked(true);
            rdioMale.setChecked(false);
        }
        propertyManagerField[1].setText(values[3]);
        propertyManagerField[2].setText(values[4]);
        propertyManagerField[3].setText(values[5]);
        propertyManagerField[4].setText(values[6]);
        propertyManagerField[5].setText(values[7]);
        propertyManagerField[6].setText(values[8]);
        propertyManagerField[7].setText(values[9]);
        propertyManagerField[8].setText(values[10]);
    }
}
