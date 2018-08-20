package com.jayapps.hello.weatherappchallenge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Jay-PC on 17-08-2018.
 */

public class DetailDay extends AppCompatActivity {
    detailadapter itemadapter2;

    int temper = 0;
    String temp = "";

    int iconidd = 0;
    int humidity = 0;
    double wind = 0.0;
    double pressur = 0.0;
    String tarik = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hourlylayout);

        Intent in = getIntent();
        tarik = in.getStringExtra("DATE");
        String cityid = in.getStringExtra("ID");
        String day = in.getStringExtra("DAY");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lay);
        String bck = in.getStringExtra("BCKG");
        if (Objects.equals(bck, "1")) {
            linearLayout.setBackground(getResources().getDrawable(R.drawable.gradient));
        } else if (Objects.equals(bck, "2")) {
            linearLayout.setBackground(getResources().getDrawable(R.drawable.gradient2));
        } else {

            linearLayout.setBackground(getResources().getDrawable(R.drawable.gradient3));
        }


        TextView daytxt = (TextView) findViewById(R.id.daytxt);
        daytxt.setText(day);

        Log.i("wxxxxxxxxxxxxxxxxx", "onCreate: dscsdcsdcs" + tarik);
        itemadapter2 =

                new detailadapter(this, 0, new ArrayList<DataCollector>());

        WeatherAsyncTask task = new WeatherAsyncTask();

        task.execute("https://api.openweathermap.org/data/2.5/forecast?id=" + cityid + "&appid=157868a80d660e5bca3321b01f05b4c3");


        ListView listView2 = (ListView) findViewById(R.id.list2);


        listView2.setAdapter(itemadapter2);


    }

    private class WeatherAsyncTask extends AsyncTask<String, Void, ArrayList<DataCollector>> {

        @Override
        protected ArrayList<DataCollector> doInBackground(String... urls) {
            //first we need to convert the String passed here to a url
            URL url = null;

            try {
                url = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            //now we have the url

            //now we need to set up a network connection
            ArrayList<DataCollector> Weather = new ArrayList<>();
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;                    //making a inputstream reader obj to take input from site in byte format and convert
            String jsonWeatherResponse;


            StringBuilder datafromstream = new StringBuilder();//helps to build data


            try {
                urlConnection = (HttpURLConnection) url.openConnection(); //establishehing connection

                urlConnection.setRequestMethod("GET");                     //seting req method to get so that we can take info from site


                urlConnection.connect();                                 //now connecting to net


                //now we are connected to internet to the url

                //now we will get the api response


                inputStream = urlConnection.getInputStream(); //we get input stream


                //we will convert the stream data to string and store the json response in a variable


                InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(streamReader);

                String line = reader.readLine();
                while (line != null) {
                    datafromstream.append(line);
                    line = reader.readLine();
                }

                jsonWeatherResponse = datafromstream.toString();

                //now we got the json response......................

                //now we will parse the response and store it in an DataCollector obj  and return this obj to onpost method

                Weather = jsonWeatherParser(jsonWeatherResponse);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return Weather;
        }

        @Override
        protected void onPostExecute(ArrayList<DataCollector> info) {


            itemadapter2.addAll(info);
        }

        public ArrayList<DataCollector> jsonWeatherParser(String jsonWeatherResponse) {

            final ArrayList<DataCollector> weather = new ArrayList<>();

            //parsing begins::::::
            String TIME = "";
            JSONObject root = null;
            try {

                root = new JSONObject(jsonWeatherResponse);
                JSONArray dayarray = root.getJSONArray("list");
                int i = 0;
                for (int j = 0; j < dayarray.length(); j++) {

                    Log.i("dfsfdfsdfdsfs", "jsonWeatherParser: sdfsfsdfsdf " + j);
                    JSONObject day1 = dayarray.getJSONObject(j);
                    JSONObject mainnn = day1.getJSONObject("main");
                    String timee = day1.getString("dt_txt");

                    temp = timee.substring(0, 10);

                    if (Objects.equals(temp, tarik)) {
                        TIME = day1.getString("dt_txt");
                        TIME = TIME.substring(10, 16);
                        humidity = mainnn.getInt("humidity");
                        pressur = mainnn.getDouble("pressure");
                        JSONObject windobj = day1.getJSONObject("wind");
                        wind = windobj.getDouble("speed");
                        temper = mainnn.getInt("temp");

                        weather.add(new DataCollector(TIME, humidity, pressur, wind, temper, iconidd));

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weather;
        }


    }
}


