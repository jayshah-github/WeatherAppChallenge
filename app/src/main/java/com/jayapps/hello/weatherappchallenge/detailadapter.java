package com.jayapps.hello.weatherappchallenge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jay-PC on 17-08-2018.
 */

public class detailadapter extends ArrayAdapter<DataCollector> {


    public detailadapter(@NonNull Context context, int resource, ArrayList<DataCollector> weather) {
        super(context, resource, weather);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.detaildaylistitem, parent, false);
        }


        ((TextView) convertView.findViewById(R.id.humidity))
                .setText(getItem(position).getChumidity());
        ((TextView) convertView.findViewById(R.id.hour))
                .setText(getItem(position).gettime());
        ((TextView) convertView.findViewById(R.id.pressure))
                .setText(getItem(position).getCpressur());
        ((TextView) convertView.findViewById(R.id.wind))
                .setText(getItem(position).getCwind());

        ((TextView) convertView.findViewById(R.id.temperature))
                .setText(getItem(position).getCtemper());

        return convertView;
    }
}
