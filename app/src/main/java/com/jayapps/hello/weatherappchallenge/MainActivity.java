package com.jayapps.hello.weatherappchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void newyork(View view) {

        Intent nyintent = new Intent(MainActivity.this, NewyorkActivity.class);

        startActivity(nyintent);
    }

    public void london(View view) {
        Intent londonintent = new Intent(MainActivity.this, LondonActivity.class);
        startActivity(londonintent);


    }

    public void paris(View view) {


        Intent Parisintent = new Intent(MainActivity.this, ParisActivity.class);

        startActivity(Parisintent);


    }


}




















