package com.htboiz.weatherapp;

import java.util.List;
import java.util.Scanner;

public class WeatherResponse {

    public final Coord coord;
    public final WeatherDetails main;
    public final Details[] weather;
    public final WindSpeed wind;
    public final Country sys;


    public WeatherResponse(Coord coord, WeatherDetails main, Details[] weather, WindSpeed wind, Country sys) {
        this.coord = coord;
        this.main = main;
        this.weather = weather;
        this.wind = wind;
        this.sys = sys;
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
        int humidity;

        @Override
        public String toString() {
            return
                    "{" +
                    "temp=" + temp +
                    ", feelsLike=" + feelsLike + ", humidity=" + humidity +
                    '}';
        }
    }

    public class Details {
        String description;

    }

    public class WindSpeed {
        float speed;
    }

    public class Country {
        String country;
    }
/*


*/

}
