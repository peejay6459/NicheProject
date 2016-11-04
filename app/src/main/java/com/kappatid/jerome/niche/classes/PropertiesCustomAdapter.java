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
 * @title PropertiesCustomAdapter.java
 * @purpose This class serves as the custom adapter of a properties
 * @date November 03, 2016
 * @input N/A
 * @processing N/A
 * @output N/A
 */

public class PropertiesCustomAdapter extends ArrayAdapter<String> {

    private ArrayList<String> address1;
    private ArrayList<String> address2;

    public PropertiesCustomAdapter(Context context, ArrayList<String> address1, ArrayList<String> address2) {
        super(context, R.layout.activity_custom_listview_properties, address2);
        this.address1 = address1;
        this.address2 = address2;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = convertView;
        if (customView == null) {
            LayoutInflater li;
            li = layoutInflater.from(getContext());
            customView = li.inflate(R.layout.activity_custom_listview_properties, parent, false);
        }

        // Casting
        TextView flatStreet = (TextView) customView.findViewById(R.id.address1);
        TextView SuburbCity = (TextView) customView.findViewById(R.id.address2);
        ImageView testImage = (ImageView) customView.findViewById(R.id.testImage);

        // Set the text
        flatStreet.setText(address1.get(position));
        SuburbCity.setText(address2.get(position));
        testImage.setImageResource(R.drawable.property);

        return customView;
    }
}
