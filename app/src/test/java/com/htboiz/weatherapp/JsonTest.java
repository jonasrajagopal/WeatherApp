package com.htboiz.weatherapp;

public class JsonTest {
    private Name name;
    private Age age;
    private Car car;

    public JsonTest(Name name, Age age, Car car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    public class Age {
        String age;

    }

    public class Name {
        String name;
    }

    public class Car {
        String car;
    }





}
