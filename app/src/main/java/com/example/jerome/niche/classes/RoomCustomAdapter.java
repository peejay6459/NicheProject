package com.example.jerome.niche.classes;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerome.niche.R;

import java.util.ArrayList;

/**
 * Created by Jerome on 29/10/2016.
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
        if(customView == null){
            LayoutInflater li;
            li = layoutInflater.from(getContext());
            customView = li.inflate(R.layout.activity_custom_listview_rooms, parent, false);
        }

        TextView roomPrice = (TextView) customView.findViewById(R.id.roomPrice);
        TextView roomOccupancy = (TextView) customView.findViewById(R.id.roomOccupancy);
        ImageView roomImage = (ImageView) customView.findViewById(R.id.roomImage);

        roomPrice.setText(price.get(position));
        roomOccupancy.setText(occupancy.get(position));
        roomImage.setImageResource(R.drawable.sample_room);

        return customView;
    }
}
