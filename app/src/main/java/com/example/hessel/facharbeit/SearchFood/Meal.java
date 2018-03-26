package com.example.hessel.facharbeit.SearchFood;

import java.util.ArrayList;

/**
 * Created by hessel on 26.03.2018.
 */

public class Meal {

    private String name;
    private ArrayList<FoodCount> ingredients;
    private int calories;
    private int protein;
    private int carbohydrates;
    private int fats;

    public Meal(String name, ArrayList<FoodCount> ingredients) {
        this.name = name;
        this.setIngredients(ingredients);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodCount> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<FoodCount> ingredients) {
        this.ingredients = ingredients;
        this.calories = 0;
        this.protein = 0;
        this.carbohydrates = 0;
        this.fats = 0;
        for(FoodCount i: ingredients){
            this.calories =this.calories + i.getCalories();
            this.protein = this.protein + i.getProtein();
            this.carbohydrates = this.carbohydrates + i.getCarbohydrates();
            this.fats = this.fats + i.getFats();
        }
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }
}
