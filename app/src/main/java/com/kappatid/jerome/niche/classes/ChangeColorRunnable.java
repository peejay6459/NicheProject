package com.kappatid.jerome.niche.classes;

import android.graphics.Color;
import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

/**
 * @author Kappatid LLC
 * @title ChangeColorRunnable.java
 * @purpose This class is a thread to change colors of a text
 * This class is implementing to Runnable interface
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class ChangeColorRunnable implements Runnable {
    // textview to change the color
    private TextView tvFutureRelease;
    // Creating new Handler
    private Handler handler;

    // Constructor
    public ChangeColorRunnable(TextView tvFutureRelease, Handler handler) {
        this.tvFutureRelease = tvFutureRelease;
        this.handler = handler;
    }

    // Change the color base on the random number
    @Override
    public void run() {
        while (true) {
            Random r = new Random();
            final int c = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tvFutureRelease.setBackgroundColor(c);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
