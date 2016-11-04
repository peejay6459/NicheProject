package com.kappatid.jerome.niche.classes;

import android.os.Handler;
import android.widget.ImageSwitcher;

/**
 * @author Kappatid LLC
 * @title RoomThread.java
 * @purpose This class is a thread for images
 * This class is extending from a Thread class
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class RoomThread extends Thread {
    private ImageSwitcher imageSwitcher;
    private Handler handler;
    private int[] rooms;
    private boolean isAlive;
    private int position;

    public RoomThread(ImageSwitcher imageSwitcher, int position, Handler handler, int[] rooms, boolean isAlive) {
        this.imageSwitcher = imageSwitcher;
        this.position = position;
        this.handler = handler;
        this.rooms = rooms;
        this.isAlive = isAlive;
    }

    public void run() {
        while (true) {
            if (position == 4) {
                position = 0;
            }
            handler.post(new Runnable() {
                @Override
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}