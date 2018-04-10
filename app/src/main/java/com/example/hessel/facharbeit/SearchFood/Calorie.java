package com.example.hessel.facharbeit.SearchFood;

/**
 * Created by hessel on 09.04.2018.
 */

public class Calorie {
    private int value;
    private String date;

    public Calorie(int value, String date) {
        this.value = value;
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
