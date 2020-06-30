package com.htboiz.weatherapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    private static String WEATHER_MAP_API_KEY = "bc930c66c2ab2ad7d8da6822aee863ea";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText loc = findViewById(R.id.location);
        final EditText state = findViewById(R.id.state);
        final EditText country = findViewById(R.id.country);
        Button button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String location = loc.getText().toString();
                String State = state.getText().toString();
                String Country = country.getText().toString();

                final TextView printTemp = findViewById(R.id.tempDisplay);
                final TextView feelsLike = findViewById(R.id.FeelsLike);
                //final TextView CloudCover = findViewById(R.id.Clouds);
                final TextView humidity = findViewById(R.id.Humidity);
                final TextView CloudDescription = findViewById(R.id.CloudsDescription);
                final TextView WindSpeed = findViewById(R.id.WindSpeed);

                ToggleButton toggle = (ToggleButton) findViewById(R.id.C_or_F);
                WeatherHelper helper = new WeatherHelper(printTemp, feelsLike, toggle, humidity, CloudDescription, WindSpeed);
                helper.run(location, State, Country, null);


                // NetworkOnMainException
//                try {
//                    String json = fetch(url);
//                } catch (IOException ex) {
//
//                }


            }
        });
    }



/*
    void displayWeather(final String json) {
        Log.v(TAG, "displayWeather " + json);
        if (json != null) {
            final WeatherResponse weather = getGson().fromJson(json, new TypeToken<WeatherResponse>() {
            }.getType());

            Log.i(TAG, weather.toString());

            final Handler h = new Handler(Looper.getMainLooper());
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    final TextView printTemp = findViewById(R.id.tempDisplay);
                    final TextView feelsLike = findViewById(R.id.FeelsLike);
                    final String tempk = toCelsius(weather.main.temp);
                    final String realfeel = toCelsius(weather.main.feelsLike);
                    printTemp.setText("Temperature: " + tempk + "\u00B0" + "C");
                    feelsLike.setText("Feels Like: " + realfeel + "\u00B0" + "C");
                    ToggleButton toggle = (ToggleButton) findViewById(R.id.C_or_F);
                    toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String inF = toF(tempk);
                                String realfeelF = toF(realfeel);
                                printTemp.setText("Temperature: " + inF + "\u00B0" + "F");
                                feelsLike.setText("Feels Like: " + realfeelF + "\u00B0" + "F");



                            } else {

                                printTemp.setText("Temperature: " + tempk + "\u00B0" + "C");
                                feelsLike.setText("Feels Like: " + realfeel + "\u00B0" + "C");
                            }
                        }
                    });



                }

            };

            h.post(runnable);



            //printTemp = (TextView)findViewById(R.id.tempDisplay);

            //String temp = Float.toString(weather.temp);
            //System.out.println(temp);
           // setUI();


           //printTemp.setText(temp);



        } else {
            Log.w(TAG, "Json was null");
        }

    }

    String getUrl(String location, String state, String country) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location;
        if(state.length() == 2 && Character.isLetter(state.charAt(0)) && Character.isLetter(state.charAt(1))) {
            url += "," + state;
        }
        if(country.length() == 2 && Character.isLetter(country.charAt(0)) && Character.isLetter(country.charAt(1))) {
            url += "," + country;
        }
        url += "&appid=" + WEATHER_MAP_API_KEY;

        return url;
    }

    void fetchAsync(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "fetch failed " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String json = response.body().string();
                    displayWeather(json);
                } else {
                    Log.e(TAG, "fetch failed " + response.code());
                }
            }
        });

    }


//    String fetch(String url) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }

    Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }
    public static String toFahrenheit(float f) {
        f -= 273.15;
        f *= 9;
        f /= 5;
        f += 32;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String s = numberFormat.format(f);
        return s;

    }

    public static String toCelsius (float f) {
        f -= 273.15;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String s = numberFormat.format(f);
        return s;
    }
    public static String toC(String s) {
        Float f = Float.valueOf(s);
        f -= 32;
        f /= 9;
        f *= 5;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String s2 = numberFormat.format(f);
        return s2;
    }

    public static String toF(String s) {
        Float f = Float.valueOf(s);
        f *= 9;
        f /= 5;
        f += 32;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String s2 = numberFormat.format(f);
        return s2;
    }






*/


}
