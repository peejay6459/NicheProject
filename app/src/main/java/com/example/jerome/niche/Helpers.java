package com.example.jerome.niche;

import android.widget.TextView;

/**
 * Created by Jerome on 21/10/2016.
 */

// This class serves as helpers class and it is implementing on interface
public abstract class Helpers implements Settings {
    abstract void changeField(final TextView username, final TextView password, final String... params);
    abstract void changeField(final TextView username, final TextView email, final TextView password, final TextView cPassword, final String... params);
    abstract void changePasswordField(final TextView password, final TextView username, final String... params);
    abstract void changePasswordField(final TextView username, final TextView email, final TextView password, final TextView cPassword, final String... params);
}
