package com.kappatid.jerome.niche.classes;

import android.os.Handler;
import android.widget.ImageSwitcher;

/**
 * @author Kappatid LLC
 * @title RoomOfferedThread.java
 * @purpose This class is a thread for images
 * This class is implementing a Runnable interface
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class RoomOfferedThread implements Runnable {
    private ImageSwitcher imageSwitcher;
    private Handler handler;
    private int[] rooms;
    private boolean isAlive;
    private int position;

    // Constructor
    public RoomOfferedThread(ImageSwitcher imageSwitcher, int position, Handler handler, int[] rooms, boolean isAlive) {
        this.imageSwitcher = imageSwitcher;
        this.position = position;
        this.handler = handler;
        this.rooms = rooms;
        this.isAlive = isAlive;
    }

    @Override
    public void run() {
        while (true) {
            if (position == 4) {
                position = 0;
            }
            handler.post(new Runnable() {
                public void run() {
                    imageSwitcher.setImageResource(rooms[position]);
                }
            });
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            position++;
        }
    }

}
