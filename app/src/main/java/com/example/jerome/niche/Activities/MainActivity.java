package com.example.jerome.niche.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.niche.classes.NicheUser;
import com.example.jerome.niche.dao.LoadTenantInformation;
import com.example.jerome.niche.dao.ValidateAccount;
import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.Settings;

/**
 * @author: kappatid LLC
 * @date:
 *
 * @description: This class is the landing page of the application
 *       It serves as the Login Page of the application
 *
 */
public class MainActivity extends AppCompatActivity implements Settings, ValidateAccount.AsyncResponse {
    // @urlAddress to store the address of the php file
    //private String urlAddress = "http://kappatid.co.nf/getAccountDetails.php";
    // @txtUsername to store the value of username
    private TextView txtUsername;
    // @txtUsername to store the value of password
    private TextView txtPassword;
    // @btnSignIn a button to SignIn or Register
    private Button btnSignIn;
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

                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if((username.isEmpty()) || (password.isEmpty())){
                    Toast.makeText(MainActivity.this, "Username/Password can't be blank", Toast.LENGTH_SHORT).show();
                }else {
                    NicheUser nUser = new NicheUser(username, password);
                    ValidateAccount vc = new ValidateAccount(getApplicationContext(), MainActivity.this, MainActivity.this, nUser);
                    vc.execute(Settings.URL_ADDRESS_VALIDATE_ACCOUNT, Settings.URL_ADDRESS_INSERT_TENANT_INFORMATION);
                }


                // Using SQLite Database

                /*
                int id = 0;
                ++id;
                String uName = txtUsername.getText().toString();
                String uPass = txtPassword.getText().toString();
                String uID = String.valueOf(id);

                Users aUsers = new Users(uName, uID, uPass);
                NicheSQLiteDb db = new NicheSQLiteDb(MainActivity.this.getApplicationContext());

                boolean result = db.validateAccount(aUsers);
                Log.d("Result ", String.valueOf(result));
                final int ID = id;
                if(result){
                    Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                } else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("No Records Found")
                            .setMessage("Would you like to create this account?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                final String uName = txtUsername.getText().toString();
                                final String uPass = txtPassword.getText().toString();
                                final String uID = String.valueOf(ID);
                                final NicheSQLiteDb db = new NicheSQLiteDb(MainActivity.this.getApplicationContext());
                                final Users aUsers = new Users(uName, uID, uPass);
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.insertAccount(aUsers);
                                    Toast.makeText(MainActivity.this, "Record Inserted Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
                */
            }
        });
    }

    @Override
    public void processFinish(String userID) {

        SharedPreferences.Editor editor = getSharedPreferences("USER_ID", MODE_PRIVATE).edit();
        editor.putString("userID", userID);
        editor.apply();
    }
}
