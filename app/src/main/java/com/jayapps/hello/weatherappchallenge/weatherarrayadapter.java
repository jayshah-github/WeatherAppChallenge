package com.jayapps.hello.weatherappchallenge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jay-PC on 12-08-2018.
 */

public class weatherarrayadapter extends ArrayAdapter<DataCollector> {


    public weatherarrayadapter(@NonNull Context context, int resource, ArrayList<DataCollector> weather) {
        super(context, resource, weather);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.weatherdaylistlayout, parent, false);
        }
        String a = getItem(position).getday();

        if (a.equals("Mon"))
            a = "Monday";

        else if (a.equals("Tue"))
            a = "Tuesday";

        else if (a.equals("Wed"))
            a = "Wednesday";

        else if (a.equals("Thu"))
            a = "Thursday";

        else if (a.equals("Fri"))
            a = "Friday";

        else if (a.equals("Sat"))
            a = "Saturday";

        else if (a.equals("Sun"))
            a = "Sunday";
        ((TextView)convertView.findViewById(R.id.DATe)).setText(getItem(position).getDATe());

        ((TextView) convertView.findViewById(R.id.day))
                .setText(a);

        ((TextView) convertView.findViewById(R.id.tempmax))
                .setText(getItem(position).gettempmax());

        ((TextView) convertView.findViewById(R.id.tempmin))
                .setText(getItem(position).gettemppmin());

        ((TextView) convertView.findViewById(R.id.DATe)).setText(getItem(position).getDATe());


        ((ImageView) convertView.findViewById(R.id.daypic)).setImageResource(getItem(position).getimageid());


        return convertView;
    }
}