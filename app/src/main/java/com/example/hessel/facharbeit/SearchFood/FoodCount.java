package com.example.hessel.facharbeit.SearchFood;

/**
 * Created by hessel on 26.03.2018.
 */

public class FoodCount {
    private Food food;
    private float count;
    private int calories;
    private int protein;
    private int carbohydrates;
    private int fats;

    public FoodCount(Food food, float count) {
        this.food = food;
        this.count = count;
        this.calories = (int) (food.getCalories()*this.count);
        this.protein = (int) (food.getProtein()*this.count);
        this.carbohydrates = (int) (food.getCarbohydrates()*this.count);
        this.fats = (int) (food.getFats()*this.count);
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
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
