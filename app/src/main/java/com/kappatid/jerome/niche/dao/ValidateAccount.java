package com.kappatid.jerome.niche.dao;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.activities.LandlordDashboardActivity;
import com.kappatid.jerome.niche.activities.MainActivity;
import com.kappatid.jerome.niche.activities.PropertyManagerDashboardActivity;
import com.kappatid.jerome.niche.activities.RegisterActivity;
import com.kappatid.jerome.niche.activities.TenantDashboardActivity;
import com.kappatid.jerome.niche.classes.NicheUser;

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
 * for Validating the account
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class ValidateAccount extends AsyncTask<String, String, Void> {
    public interface AsyncResponse {
        void processFinish(String userID, String username, String userType);
    }

    private AsyncResponse delegate = null;
    private MainActivity main;
    private Context context;
    private NicheUser nUser;
    private ProgressDialog pd;
    private String userID;
    private String username;
    private String userType;

    public ValidateAccount(Context context, AsyncResponse delegate, MainActivity main, NicheUser nUser) {
        this.context = context;
        this.delegate = delegate;
        this.main = main;
        this.nUser = nUser;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(main);
        pd.setTitle("Validating Account");
        pd.setMessage("Loading, Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            String getUserName = URLEncoder.encode("username", "UTF-8");
            getUserName += "=" + URLEncoder.encode(nUser.getUsername(), "UTF-8");

            Log.d("Username: ", nUser.getUsername());
            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(getUserName);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] account = line.split("_");
                for (String acc : account) {
                    Log.d("Account: ", acc);
                    publishProgress(acc);

                    final String[] accountDetails = acc.split("-");
                    URL url1 = new URL(params[1]);
                    URLConnection con1 = url1.openConnection();
                    Log.d("URL", params[1]);
                    String insertID = URLEncoder.encode("userID", "UTF-8");
                    insertID += "=" + URLEncoder.encode(accountDetails[0], "UTF-8");
                    String userType = URLEncoder.encode("userType", "UTF-8");
                    userType += "=" + URLEncoder.encode(accountDetails[4], "UTF-8");
                    Log.d("userType", accountDetails[4]);

                    con1.setDoOutput(true);
                    os = new OutputStreamWriter(con1.getOutputStream());
                    os.write(insertID + "&" + userType);
                    os.flush();
                    con1.getInputStream();
                }
            }


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
    protected void onProgressUpdate(String... values) {
        final String[] accountDetails = values[0].split("-");
        Log.d("values[0]", values[0]);
        Log.d("Username", accountDetails[1]);
        Log.d("Password", accountDetails[2]);
        if ((accountDetails[1].equals(nUser.getUsername())) && (accountDetails[2].equals(nUser.getPassword()))) {
            showError(accountDetails);
        } else if ((accountDetails[1].equals("null") && (accountDetails[2].equals("null")))) {
            new AlertDialog.Builder(main)
                    .setTitle("No Records Found")
                    .setIcon(R.drawable.ic_no_records_found)
                    .setMessage("Do you want to create a new account?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goRegisterActivity = new Intent(main, RegisterActivity.class);
                            main.startActivity(goRegisterActivity);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else {
            Toast.makeText(main, "Invalid username/password", Toast.LENGTH_SHORT).show();
        }
        userID = accountDetails[0];
        username = accountDetails[1];
        userType = accountDetails[4];


        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        delegate.processFinish(userID, username, userType);
    }

    private void showError(String[] accountDetails) {
        if (accountDetails[4].equals("Tenant")) {
            Intent goTenantDashboardActivity = new Intent(main, TenantDashboardActivity.class);
            main.startActivity(goTenantDashboardActivity);
            Toast.makeText(context, "Welcome to DashBoard", Toast.LENGTH_SHORT).show();
        } else if (accountDetails[4].equals("Landlord")) {
            Intent goLandlordDashboardActivity = new Intent(main, LandlordDashboardActivity.class);
            main.startActivity(goLandlordDashboardActivity);
            Toast.makeText(context, "Welcome to DashBoard", Toast.LENGTH_SHORT).show();
        } else if (accountDetails[4].equals("Property Manager")) {
            Intent goPropertyManagerDashboardActivity = new Intent(main, PropertyManagerDashboardActivity.class);
            main.startActivity(goPropertyManagerDashboardActivity);
            Toast.makeText(context, "Welcome to DashBoard", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Currently unavailable", Toast.LENGTH_SHORT).show();
        }
    }

}