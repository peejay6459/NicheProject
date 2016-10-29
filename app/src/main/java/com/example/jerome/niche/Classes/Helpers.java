package com.example.jerome.niche.classes;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jerome on 21/10/2016.
 */

// This class serves as helpers class and it is implementing on interface
public abstract class Helpers implements Settings {
    abstract void changeField(final TextView username, final TextView password, final String... params);
    abstract void changeField(final TextView username, final TextView email, final TextView password, final TextView cPassword, final String... params);
    abstract void changeTextField(final TextView firstTv, final TextView secondTv, final TextView thirdTv, final String... params);
    abstract void changeTextField(final TextView firstTv, final TextView secondTv, final TextView thirdTv, final TextView fourthTv, final TextView fifthTv, final String... params);
    abstract void changePasswordField(final TextView password, final TextView username, final String... params);
    abstract void changePasswordField(final TextView username, final TextView email, final TextView password, final TextView cPassword, final String... params);
    abstract void setEditableFalse(ArrayList<TextView> fieldName);
    abstract void setEditableTrue(ArrayList<TextView> fieldName);
}
