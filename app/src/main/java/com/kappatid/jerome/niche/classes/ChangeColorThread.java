package com.kappatid.jerome.niche.classes;

import android.graphics.Color;
import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

/**
 * @author Kappatid LLC
 * @title ChangeColorRunnable.java
 * @purpose This class is a thread to change colors of a text
 * This class is extending to a Thread class
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class ChangeColorThread extends Thread {
    private TextView tvFutureRelease;
    private Handler handler;

    public ChangeColorThread(TextView tvFutureRelease, Handler handler) {
        this.tvFutureRelease = tvFutureRelease;
        this.handler = handler;
    }

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
