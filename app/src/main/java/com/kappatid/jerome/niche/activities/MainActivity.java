package com.kappatid.jerome.niche.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kappatid.jerome.niche.classes.NicheUser;
import com.kappatid.jerome.niche.dao.ValidateAccount;
import com.kappatid.jerome.niche.classes.FieldHelper;
import com.kappatid.jerome.niche.R;
import com.kappatid.jerome.niche.classes.Settings;


/**
 * @author Kappatid LLC
 * @title MainActivity.java
 * @purpose This class is the landing page of the application
 * It serves as the Login Page of the application
 * @date November 03, 2016
 * @input Username and Password
 * @processing ValidateAccount.java
 * @output Go to Dashboard depending on the user type
 */
public class MainActivity extends AppCompatActivity implements Settings, ValidateAccount.AsyncResponse {
    // @txtUsername to store the value of username
    private TextView txtUsername;
    // @txtUsername to store the value of password
    private TextView txtPassword;
    // @btnSignIn a button to SignIn or Register
    private Button btnSignIn;
    // @txtForgotPassword whenever a user forgot his/her password
    private TextView txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create an object of a FieldHelper class
        FieldHelper fh = new FieldHelper();

        // Casting
        txtUsername = (TextView) findViewById(R.id.tvUsername);
        txtPassword = (TextView) findViewById(R.id.tvPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        txtForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        // Setting the field to blank whenever the user focus on this field
        // Username Field
        fh.changeField(txtUsername, txtPassword);

        // Setting the field to blank whenever the user focus on this field
        // Password Field
        fh.changePasswordField(txtPassword, txtUsername);

        // Setting an onclick listener for Forgot Password
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked forgot password", Toast.LENGTH_SHORT).show();
            }
        });

        // Set Onclick listener to btnSignIn
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Using MySQL

                // Getting the username on edit field
                String username = txtUsername.getText().toString();
                // Getting the password on edit field
                String password = txtPassword.getText().toString();
                // Validate the field if username or password is empty
                if ((username.isEmpty()) || (password.isEmpty())) {
                    Toast.makeText(MainActivity.this, "Username/Password can't be blank", Toast.LENGTH_SHORT).show();
                } else {
                    // Pass the data to Getter and Setter
                    NicheUser nUser = new NicheUser(username, password);
                    // Validate the accounts on middle tier
                    ValidateAccount vc = new ValidateAccount(getApplicationContext(), MainActivity.this, MainActivity.this, nUser);
                    vc.execute(Settings.URL_ADDRESS_VALIDATE_ACCOUNT, Settings.URL_ADDRESS_INSERT_TENANT_INFORMATION);
                }
            }
        });
    }

    /**
     * @param userID   get userID of the user that was process on the database
     * @param username get username of the user that was process on the database
     * @param userType get userType of the user that was process on the database
     */
    @Override
    public void processFinish(String userID, String username, String userType) {
        // Save it on a Shared Preference to use it later on
        SharedPreferences.Editor editor = getSharedPreferences("USER_ID", MODE_PRIVATE).edit();
        editor.putString("userID", userID);
        editor.putString("username", username);
        editor.putString("userType", userType);
        editor.apply();
    }
}
