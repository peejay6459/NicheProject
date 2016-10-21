package com.example.jerome.niche;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jerome on 9/10/2016.
 */

public class ValidateAccount extends AsyncTask<String, String, Void> {
    MainActivity main;
    Context context;
    String username;
    String password;
    ProgressDialog pd;


    public ValidateAccount(Context context, MainActivity main, String username, String password){
        this.context = context;
        this.main = main;
        this.username = username;
        this.password = password;
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
            getUserName += "=" + URLEncoder.encode(username, "UTF-8");

            Log.d("Username: ", username);
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(String... values) {
        final String[] accountDetails = values[0].split("-");
        Log.d("values[0]", values[0]);
        Log.d("Username", accountDetails[0]);
        Log.d("Password", accountDetails[1]);
        if((accountDetails[0].equals(username)) && (accountDetails[1].equals(password))){
            if(accountDetails[2].equals("Tenant")) {
                Intent goTenantDashboardActivity = new Intent(main, TenantDashboardActivity.class);
                main.startActivity(goTenantDashboardActivity);
                Toast.makeText(context, "Welcome to DashBoard", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Currently unavailable", Toast.LENGTH_SHORT).show();
            }
        }else if((accountDetails[0].equals("null") && (accountDetails[1].equals("null")))){
            new AlertDialog.Builder(main)
                    .setTitle("No Records Found")
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
        }else{
            Toast.makeText(main, "Invalid username/password", Toast.LENGTH_SHORT).show();
        }
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        super.onPostExecute(aVoid);
    }
}
