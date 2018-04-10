package com.example.hessel.facharbeit.Utils;

/**
 * Created by hessel on 23.01.2018.
 */







public class Body {
    private float weight;
    private float height;
    private float fat;
    private float muscle;
    private String date;

    public Body(float weight, float height, String date,float fat, float muscle) {
        this.weight = weight;
        this.height = height;
        this.fat = fat;
        this.muscle = muscle;
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getDate() {
        return date;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getMuscle() {
        return muscle;
    }

    public void setMuscle(float muscle) {
        this.muscle = muscle;
    }

}
