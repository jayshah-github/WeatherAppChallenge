package com.jayapps.hello.weatherappchallenge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

/**
 * Created by Jay-PC on 13-08-2018.
 */

public class NewyorkActivity extends AppCompatActivity {
    String descrip = "";
    String curtime = "";
    int temperature = 0;
    weatherarrayadapter itemadapter;
    int iconidd = 0;
    String id = "5115985";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layoutweather);
        LinearLayout lay1 = (LinearLayout) findViewById(R.id.lay1);
        lay1.setBackground(getResources().getDrawable(R.drawable.gradient));
        TextView naam = (TextView) findViewById(R.id.citynaam);
        naam.setText("New York");


        itemadapter =

                new weatherarrayadapter(this, 0, new ArrayList<DataCollector>());

        WeatherAsyncTask task = new WeatherAsyncTask();

        task.execute("https://api.openweathermap.org/data/2.5/forecast?id=" + id + "&appid=157868a80d660e5bca3321b01f05b4c3");


        ListView listView = (ListView) findViewById(R.id.list);


        listView.setAdapter(itemadapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent detailday = new Intent(NewyorkActivity.this, DetailDay.class);

                detailday.putExtra("DATE", ((TextView) view.findViewById(R.id.DATe))
                        .getText());
                detailday.putExtra("ID", id);
                detailday.putExtra("DAY", ((TextView) view.findViewById(R.id.day))
                        .getText());

                detailday.putExtra("BCKG", "1");
                startActivity(detailday);
            }
        });


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
            // Clear the adapter of previous earthquake data

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.

            TextView set = (TextView) findViewById(R.id.timeset);
            set.setText("" + curtime);

            TextView dess = (TextView) findViewById(R.id.haal);
            dess.setText("" + descrip);
            TextView settemp = (TextView) findViewById(R.id.temp23);
            settemp.setText("" + temperature + "˚");
            ImageView dayimg = (ImageView) findViewById(R.id.imgday);

            Log.i("wwwwwwwwwwwww", "onPostExecute: imgid" + iconidd);
            if (iconidd >= 200 && iconidd < 300) {
                dayimg.setImageResource(R.drawable.thunder);
            } else if (iconidd >= 300 && iconidd < 600) {
                dayimg.setImageResource(R.drawable.rainn);
            } else if (iconidd >= 600 && iconidd < 700) {
                dayimg.setImageResource(R.drawable.snowflake);
            } else if (iconidd >= 700 && iconidd < 800) {
                dayimg.setImageResource(R.drawable.storm);
            } else if (iconidd == 800) {
                dayimg.setImageResource(R.drawable.sunww);
            } else {
                dayimg.setImageResource(R.drawable.clouds);
            }


            itemadapter.addAll(info);


        }

        public ArrayList<DataCollector> jsonWeatherParser(String jsonWeatherResponse) {

            final ArrayList<DataCollector> weather = new ArrayList<>();
            String DAATe = "";
            int citytempmin = 0;

            int citytempmax = 0;
            //parsing begins::::::

            JSONObject root = null;
            try {
                root = new JSONObject(jsonWeatherResponse);
                JSONArray dayarray = root.getJSONArray("list");


                String temp = "";
                String timee = "";
                String date = "";
                int iconid = 0;

                JSONObject day1 = dayarray.getJSONObject(0);
                JSONObject mainnn = day1.getJSONObject("main");
                JSONArray desp = day1.getJSONArray("weather");
                JSONObject desp1 = desp.getJSONObject(0);

                descrip = desp1.getString("description");
                curtime = day1.getString("dt_txt");
                iconidd = desp1.getInt("id");
                temperature = mainnn.getInt("temp");
                temperature = temperature - 273;
                SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date da = sdf0.parse(curtime);
                long milis = da.getTime();


                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(milis);
                curtime = dateFormatter.format(calendar.getTime());
                SimpleDateFormat dateFormatter2 = new SimpleDateFormat("EEE");

                curtime = curtime + " ," + dateFormatter2.format(milis);


                int tempmaxxx = mainnn.getInt("temp_max");
                int tempminn = mainnn.getInt("temp_min");

                for (int i = 0; i < dayarray.length() - 1; i++) {


                    day1 = dayarray.getJSONObject(i);
                    mainnn = day1.getJSONObject("main");

                    desp = day1.getJSONArray("weather");
                    desp1 = desp.getJSONObject(0);

                    iconid = desp1.getInt("id");

                    timee = day1.getString("dt_txt");
                    DAATe = timee.substring(0, 10);


                    temp = dayarray.getJSONObject(i + 1).getString("dt_txt").substring(0, 10);
                    sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    da = sdf0.parse(timee);
                    milis = da.getTime();
                    dateFormatter2 = new SimpleDateFormat("EEE");

                    date = dateFormatter2.format(milis);

                    citytempmax = mainnn.getInt("temp_max");
                    citytempmin = mainnn.getInt("temp_min");


                    Log.i("ererrer44444444444", "jsonWeatherParser: ------  " + DAATe + "------   " + timee);

                    if (!(DAATe == temp) && (i == dayarray.length() - 2)) {
                        weather.add(new DataCollector(date, tempmaxxx - 273, tempminn - 273, DAATe, iconid));

                        Log.i("hurray", "jsonWeatherParser: ------  " + DAATe + "------   " + timee);
                    }
                    if (DAATe.equals(temp)) {


                        if (tempmaxxx < citytempmax) {
                            tempmaxxx = citytempmax;
                        }

                        if (tempminn > citytempmin) {
                            tempminn = citytempmin;
                        }


                    } else {

                        weather.add(new DataCollector(date, tempmaxxx - 273, tempminn - 273, DAATe, iconid));
                    }
                    //returning to on onpost
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return weather;
        }


    }
}