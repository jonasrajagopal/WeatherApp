package com.htboiz.weatherapp;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;



import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;


public class TestDriver {

    public static String json = "{\"coord\":{\"lon\":-71.06,\"lat\":42.36},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\"," +
            "\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":301,\"feels_like\":301.44,\"temp_min\":299.82,\"temp_max\":302.04,\"pressure\":1004,\"humidity\":65}}";


    @Test
    public void testGson() {
        final WeatherResponse weather = getGson().fromJson(json, new TypeToken<WeatherResponse>() {
        }.getType());
        assertEquals(weather.main.temp, 301);
    }




    Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }


}
