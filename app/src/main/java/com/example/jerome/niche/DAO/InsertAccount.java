package com.example.jerome.niche.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jerome.niche.activities.RegisterActivity;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jeffrey on 17/10/2016.
 */

/**
 * @author Kappatid LLC
 * @date
 *
 * @description: This class serves as the middle tier of the application
 *      It's purpose is to insert user input data into database
 */
public class InsertAccount extends AsyncTask<String, String, Void> {
    // not yet used
    private Context context;
    // not yet used
    private RegisterActivity reg;
    private String username;
    private String password;
    private String email;
    private String userType;

    public InsertAccount(Context context, RegisterActivity reg, String username, String password, String email, String userType){
        this.context = context;
        this.reg = reg;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
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
            con.getInputStream();
            Log.d("username ", username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected    void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
