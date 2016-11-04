package com.kappatid.jerome.niche.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kappatid.jerome.niche.R;

import java.util.ArrayList;

/**
 * @author Kappatid LLC
 * @title RoomCustomAdapter.java
 * @purpose This class serves as the custom adapter of a room
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class RoomCustomAdapter extends ArrayAdapter<String> {
    private ArrayList<String> price;
    private ArrayList<String> occupancy;

    public RoomCustomAdapter(Context context, ArrayList<String> price, ArrayList<String> occupancy) {
        super(context, R.layout.activity_custom_listview_rooms, price);
        this.price = price;
        this.occupancy = occupancy;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = convertView;
        if (customView == null) {
            LayoutInflater li;
            li = layoutInflater.from(getContext());
            customView = li.inflate(R.layout.activity_custom_listview_rooms, parent, false);
        }

        // Casting
        TextView roomPrice = (TextView) customView.findViewById(R.id.roomPrice);
        TextView roomOccupancy = (TextView) customView.findViewById(R.id.roomOccupancy);
        ImageView roomImage = (ImageView) customView.findViewById(R.id.roomImage);

        // Set the values to a list
        roomPrice.setText(price.get(position));
        roomOccupancy.setText(occupancy.get(position));
        roomImage.setImageResource(R.drawable.sample_room);

        return customView;
    }
}
