package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Francislainy on 10/01/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Earthquake earthquake = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        TextView magnitudeTextView = (TextView) convertView.findViewById(R.id.magnitude_text_view);
        TextView locationTextView =  (TextView) convertView.findViewById(R.id.location_text_view);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date_text_view);

        magnitudeTextView.setText(earthquake.getMagnitude());
        locationTextView.setText(earthquake.getLocation());
        dateTextView.setText(earthquake.getDate());

        return convertView;
    }

}
