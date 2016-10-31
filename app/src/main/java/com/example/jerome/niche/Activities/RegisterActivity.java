package com.example.jerome.niche.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.niche.dao.InsertAccount;
import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.Settings;

/**
 * @author: kappatid LLC
 * @date:
 *
 * @description: This class is for Registration of accounts
 *      It takes data from user input then send the data
 *      into the middle tier class
 */
public class RegisterActivity extends AppCompatActivity implements InsertAccount.AsyncResponse {
    private TextView txtRegUsername;
    private TextView txtRegEmail;
    private TextView txtRegPassword;
    private TextView txtRegConfirmPassword;
    private CheckBox cbTermsOfServices;
    private CheckBox cbPrivacyPolicy;
    private Button btnRegister;
    private Spinner spinUserType;
    private String result;
    //private String urlAddress = "http://kappatid.co.nf/insertAccountDetails.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Create Account");

        // creating new object of FieldHelper class
        FieldHelper fh = new FieldHelper();
        txtRegUsername = (TextView) findViewById(R.id.tvRegUsername);
        txtRegEmail = (TextView) findViewById(R.id.tvRegEmail);
        txtRegPassword = (TextView) findViewById(R.id.tvRegPassword);
        txtRegConfirmPassword = (TextView) findViewById(R.id.tvRegConfirmPassword);
        cbTermsOfServices = (CheckBox) findViewById(R.id.cbTermsOfServices);
        cbPrivacyPolicy = (CheckBox) findViewById(R.id.cbPrivacyPolicy);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        spinUserType = (Spinner) findViewById(R.id.spinUserType);

        // Username field
        // Use the changeField method that is inherited from FieldHelper class
        fh.changeField(txtRegUsername, txtRegEmail, txtRegPassword, txtRegConfirmPassword, "Username", "Email", "Password", "Confirm Password");

        // Email field
        // Use the changeField method that is inherited from FieldHelper class
        fh.changeField(txtRegEmail, txtRegUsername, txtRegPassword, txtRegConfirmPassword, "Email", "Username", "Password", "Confirm Password");

        // Password field
        // Use the changeField method that is inherited from FieldHelper class
        fh.changePasswordField(txtRegUsername, txtRegEmail, txtRegPassword, txtRegConfirmPassword, "Username", "Email", "Password", "Confirm Password");

        // Confirm Password field
        // Use the changeField method that is inherited from FieldHelper class
        fh.changePasswordField(txtRegUsername, txtRegEmail, txtRegConfirmPassword, txtRegPassword, "Username", "Email", "Confirm Password", "Password");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if any of the fields are empty
                if(!(txtRegUsername.getText().toString().isEmpty())
                        &&!(txtRegUsername.getText().toString().equals("Username"))
                        &&!(txtRegEmail.getText().toString().isEmpty())
                        &&!(txtRegEmail.getText().toString().equals("Email"))
                        &&!(txtRegPassword.getText().toString().isEmpty())
                        &&!(txtRegPassword.getText().toString().equals("Password"))
                        &&!(txtRegConfirmPassword.getText().toString().isEmpty())
                        &&!(txtRegConfirmPassword.getText().toString().equals("Confirm Password"))
                        &&(cbTermsOfServices.isChecked())
                        && ((cbPrivacyPolicy.isChecked()))) {
                    // check if the password field matches
                    if(txtRegPassword.getText().toString().equals(txtRegConfirmPassword.getText().toString())){
                        // check if the username and password field are less than 8 and greater than 15
                        if((txtRegPassword.getText().toString().length() <= 7)
                                || (txtRegPassword.getText().toString().length() >= 15)
                                || (txtRegUsername.getText().toString().length() <= 7)
                                || (txtRegUsername.getText().toString().length() >= 15)){
                            Toast.makeText(RegisterActivity.this, "Username/Password must be from 8 to 14 characters", Toast.LENGTH_SHORT).show();
                        }else{
                            // check the email field if it's valid
                            if(txtRegEmail.getText().toString().contains("@")){
                                String username = txtRegUsername.getText().toString();
                                String password = txtRegPassword.getText().toString();
                                String email = txtRegEmail.getText().toString();
                                String userType = String.valueOf(spinUserType.getSelectedItem());
                                InsertAccount ic = new InsertAccount(getApplicationContext(), RegisterActivity.this, username, password, email, userType, RegisterActivity.this);
                                ic.execute(Settings.URL_ADDRESS_INSERT_ACCOUNT);
                                //processFinish(result);
                            }else {
                                Toast.makeText(RegisterActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password must match!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void processFinish(String result1) {
        Log.d("result->>>>>>>", result1);
        if(result1.equals("already exist")) {
            Toast.makeText(RegisterActivity.this, "Username already exist", Toast.LENGTH_SHORT).show();
        }else{
            Intent goMainActivity = new Intent(RegisterActivity.this, MainActivity.class);
            RegisterActivity.this.startActivity(goMainActivity);
            Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
