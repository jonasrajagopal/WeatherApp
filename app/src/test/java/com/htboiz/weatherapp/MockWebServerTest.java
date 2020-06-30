package com.htboiz.weatherapp;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;


import okhttp3.HttpUrl;
import okhttp3.mockwebserver.*;

public class MockWebServerTest {
    @Test
    public void test() throws Exception {
        final float temp = (float)292.09;


        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody( "{\"coord\":{\"lon\":-71.06,\"lat\":42.36},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\"," +
                "\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\": " + temp + "\"feels_like\":301.44,\"temp_min\":299.82,\"temp_max\":302.04,\"pressure\":1004,\"humidity\":65}}"));







        server.start();

        WeatherHelper helper = new WeatherHelper(null, null,null,null,null,null);
        HttpUrl baseURL = server.url("");
        String url = helper.getUrl("Boston", "MA", "US", baseURL);
        helper.fetchAsync(url);
        Thread.sleep(1000);
        WeatherResponse weather = helper.getLastResponse();
        System.out.println(weather.coord.lat);
        assertEquals(temp, weather.main.temp, 0.01);
        server.shutdown();

    }




}
