package com.example.jerome.niche.classes;

import android.text.InputType;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerome.niche.R;

import java.util.ArrayList;

/**
 * Created by Jerome on 20/10/2016.
 */

/**
 * @author: kappatid LLC
 * @date:
 *
 * @description: This class extends on a abstract class Helpers and overrides all of its method
 */

public class FieldHelper extends Helpers {

    /**
     * change field with 2 edit fields @username, @password
     *
     * @param username is the edit field of username
     * @param password is the edit field of password
     * @param params
     */
    @Override
    public void changeField(final TextView username, final TextView password, final String... params) {
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(username.getText().toString().equals("Username")){
                    username.setText("");
                }
                if(password.getText().toString().equals("")){
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setText("Password");
                }
            }
        });
    }

    /**
     * change field with 4 edit fields @username, @email, @password, @cPassword
     *
     * @param username TextView to change the value of edit field
     * @param email TextView to change the value of edit field
     * @param password TextView to change the value of edit field
     * @param cPassword TextView to change the value of edit field
     * @param params String parameters to validate what's inside the edit field
     */
    @Override
    public void changeField(final TextView username, final TextView email, final TextView password, final TextView cPassword, final String... params) {
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(username.getText().toString().equals(params[0])){
                    username.setText("");
                }
                if(email.getText().toString().isEmpty()){
                    email.setText(params[1]);
                }
                if(password.getText().toString().isEmpty()){
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setText(params[2]);
                }
                if(cPassword.getText().toString().isEmpty()){
                    cPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    cPassword.setText(params[3]);
                }
            }
        });
    }

    /**
     * change field with 2 edit fields that needs to convert the inputType @username, @password
     *
     * @param password is the edit field of password
     * @param username is the edit field of username
     * @param params
     */
    @Override
    public void changePasswordField(final TextView password, final TextView username, final String... params) {
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(password.getText().toString().equals("Password")) {
                    password.setText("");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                if(username.getText().toString().equals("")){
                    username.setText("Username");
                }

            }
        });
    }

    /**
     * change field with 4 edit fields that needs to convert the inputType @username, @email, @password, @cPassword
     *
     * @param username TextView to change the value of edit field
     * @param email TextView to change the value of edit field
     * @param password TextView to change the value of edit field
     * @param cPassword TextView to change the value of edit field
     * @param params String parameters to validate what's inside the edit field
     */
    @Override
    public void changePasswordField(final TextView username, final TextView email, final TextView password, final TextView cPassword, final String... params) {
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(username.getText().toString().isEmpty()){
                    username.setText(params[0]);
                }
                if(email.getText().toString().isEmpty()){
                    email.setText(params[1]);
                }
                if(password.getText().toString().equals(params[2])) {
                    password.setText("");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                if(cPassword.getText().toString().isEmpty()){
                    cPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    cPassword.setText(params[3]);
                }
            }
        });
    }

    @Override
    public void setEditableFalse(ArrayList<TextView> fieldName) {
        for(TextView fields : fieldName){
            fields.setEnabled(false);
        }
    }
    @Override
    public void setEditableTrue(ArrayList<TextView> fieldName) {
        for(TextView fields : fieldName){
            fields.setEnabled(true);
        }
    }
}
