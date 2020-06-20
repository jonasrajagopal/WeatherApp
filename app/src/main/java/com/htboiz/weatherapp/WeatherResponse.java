package com.htboiz.weatherapp;

import java.util.List;
import java.util.Scanner;

public class WeatherResponse {

    public final Coord coord;
    public final WeatherDetails main;


    public WeatherResponse(Coord coord, WeatherDetails main) {
        this.coord = coord;
        this.main = main;
    }


  /*  @Override
    public String toString() {
        WeatherDescription item = null;
        if (list != null && list.size() > 0)
            item = list.get(0);

        return "WeatherResponse{" +
                "coord=" + coord +
                ", item=" + item +
                '}';
    }
*/
    public class Coord {
        float lon;
        float lat;
    }

    public class WeatherDescription {
        int id;
        WeatherDetails main;

        @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", main=" + main +
                    '}';
        }
    }

    public class WeatherDetails {
        float temp;
        float feelsLike;

        @Override
        public String toString() {
            return
                    "{" +
                    "temp=" + temp +
                    ", feelsLike=" + feelsLike +
                    '}';
        }
    }

}
