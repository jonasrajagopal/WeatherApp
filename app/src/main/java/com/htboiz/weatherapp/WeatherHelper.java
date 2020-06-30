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

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Collection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.*;

public class WeatherHelper {

    private static String TAG = "MainActivity";

    private static String WEATHER_MAP_API_KEY = "bc930c66c2ab2ad7d8da6822aee863ea";

    public static WeatherResponse lastResponse;

    public static String Country;

    private TextView TempDisplay;
    private TextView FeelsLike;
    private ToggleButton toggle;
   // private TextView CloudCover;
    private TextView Humidity;
    private TextView CloudDescription;
    private TextView WindSpeed;

    public WeatherHelper(TextView TempDisplay, TextView RealFeel, ToggleButton toggle, TextView humidity, TextView CloudDescription, TextView WindSpeed) {
        this.TempDisplay = TempDisplay;
        this.FeelsLike = RealFeel;
        this.toggle = toggle;
        this.Humidity = humidity;
      //  this.CloudCover = CloudCover;
        this.CloudDescription = CloudDescription;
        this.WindSpeed = WindSpeed;

    }










    String getUrl(String location, String state, String country, HttpUrl BaseURL) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        if(BaseURL != null) {
            sb.append(BaseURL.host());
            sb.append(BaseURL.port());
        }
        else {
            sb.append("api.openweathermap.org");
        }



        sb.append("/data/2.5/weather?q=");

        sb.append(location);

        if(state.length() == 2 && Character.isLetter(state.charAt(0)) && Character.isLetter(state.charAt(1))) {
            sb.append(",");
            sb.append(state);
        }
        if(country.length() == 2 && Character.isLetter(country.charAt(0)) && Character.isLetter(country.charAt(1))) {
            sb.append(",");
            sb.append(country);
        }
        sb.append("&appid=");
        sb.append(WEATHER_MAP_API_KEY);

        Country = country;
        System.out.println(sb.toString());

        return sb.toString();
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
                    //Log.v(TAG, "displayWeather " + json);
                    if (json != null) {
                        final WeatherResponse weather = getGson().fromJson(json, new TypeToken<WeatherResponse>() {
                        }.getType());
                        Log.i(TAG, weather.toString());
                        lastResponse = weather;
                        displayWeather(weather);

                    }
                    else {
                        Log.w(TAG, "Json was null");
                    }


                } else {
                    Log.e(TAG, "fetch failed " + response.code());
                }
            }
        });

    }

    void displayWeather(final WeatherResponse weather) {

        if(TempDisplay == null || FeelsLike == null) {
            return;
        }








        //final WeatherResponse2[] weather2 = getGson().fromJson(json, WeatherResponse2[].class);
        // Type collectionType = new TypeToken<Collection<WeatherResponse2>>(){}.getType();
        // final Collection<WeatherResponse2> weather2 = getGson().fromJson(json, collectionType);


        final Handler h = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String inF = toFahrenheit(weather.main.temp);
                final String realfeelF = toFahrenheit(weather.main.feelsLike);
                final String tempk = toCelsius(weather.main.temp);
                final String realfeel = toCelsius(weather.main.feelsLike);
                final String description = weather.weather[0].description;
                final double speedMPH = weather.wind.speed * 2.23;
                final double speedKPH = weather.wind.speed * 3.6;
                //final String main = weather.weather[0].main;
                if(weather.sys.country.toLowerCase().equals("us")) {
                    TempDisplay.setText("Temperature: " + inF + "\u00B0" + "F");
                    FeelsLike.setText("Feels Like: " + realfeelF + "\u00B0" + "F");
                    WindSpeed.setText("Wind Speed: " + (int)speedMPH + "mph");
                    toggle.setChecked(true);

                }
                else {
                    TempDisplay.setText("Temperature: " + tempk + "\u00B0" + "C");
                    FeelsLike.setText("Feels Like: " + realfeel + "\u00B0" + "C");
                    WindSpeed.setText("Wind Speed: " + (int)speedKPH + "km/h");
                    toggle.setChecked(false);
                }

                Humidity.setText("Humidity: " + weather.main.humidity + "%");
                //CloudCover.setText("Main: " + main);
                CloudDescription.setText("Description: " + capitalizeString(description));

                toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            TempDisplay.setText("Temperature: " + inF + "\u00B0" + "F");
                            FeelsLike.setText("Feels Like: " + realfeelF + "\u00B0" + "F");
                            WindSpeed.setText("Wind Speed: " + (int)speedMPH + "mph");
                        } else {
                            TempDisplay.setText("Temperature: " + tempk + "\u00B0" + "C");
                            FeelsLike.setText("Feels Like: " + realfeel + "\u00B0" + "C");
                            WindSpeed.setText("Wind Speed: " + (int)speedKPH + "km/h");
                        }
                    }
                });
            }
        };
        h.post(runnable);
    }


    // conversion helper methods

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



    public void run(String location, String state, String country, HttpUrl base) {
        String url = getUrl(location, state, country, null);
        fetchAsync(url);


    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public WeatherResponse getLastResponse() {
        return lastResponse;

    }

    Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }



/*
    static String setHostName() {
        MockWebServerTest server = new MockW
        baseURL.host() + baseURL.port() +
    }
*/


}
