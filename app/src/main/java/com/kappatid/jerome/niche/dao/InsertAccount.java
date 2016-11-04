package com.kappatid.jerome.niche.dao;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.kappatid.jerome.niche.activities.RegisterActivity;

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
 * @purpose This class serves as the middle tier of the application
 * It's purpose is to insert user input data into database
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */
public class InsertAccount extends AsyncTask<String, String, String> {
    public interface AsyncResponse {
        void processFinish(String result);
    }

    private AsyncResponse delegate;
    private RegisterActivity reg;
    private String username;
    private String password;
    private String email;
    private String userType;
    private String result1;
    private ProgressDialog pd;

    public InsertAccount(RegisterActivity reg, String username, String password, String email, String userType, AsyncResponse delegate) {
        this.reg = reg;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(reg);
        pd.setTitle("Validating");
        pd.setMessage("Please wait");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();

            // values to insert into database
            String insUsername = URLEncoder.encode("username", "UTF-8");
            insUsername += "=" + URLEncoder.encode(username, "UTF-8");
            String insPassword = URLEncoder.encode("password", "UTF-8");
            insPassword += "=" + URLEncoder.encode(password, "UTF-8");
            String insEmail = URLEncoder.encode("email", "UTF-8");
            insEmail += "=" + URLEncoder.encode(email, "UTF-8");
            String insUserType = URLEncoder.encode("userType", "UTF-8");
            insUserType += "=" + URLEncoder.encode(userType, "UTF-8");

            // Telling the connection to allow output data
            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(insUsername + "&" + insPassword + "&" + insEmail + "&" + insUserType);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result1 = line;
                publishProgress(result1);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result1;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        result1 = values[0];
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result1) {
        pd.dismiss();
        delegate.processFinish(result1);
    }
}
