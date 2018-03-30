package com.example.hessel.facharbeit.SearchFood;

/**
 * Created by hessel on 26.03.2018.
 */

public class FoodCount {
    private Food food;
    private float amount;
    private String meal;

    public FoodCount(Food food) {
        this.food = food;
        this.amount = 1;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public float getCount() {
        return amount;
    }

    public void setCount(float count) {
        this.amount = count;
    }

    public int getCalories() {
        return (int) (food.getCalories()*this.amount);
    }
    public int getProtein() {
        return (int) (food.getProtein()*this.amount);
    }
    public int getCarbohydrates() {
        return (int) (food.getCarbohydrates()*this.amount);
    }

    public int getFats() {
        return (int) (food.getFats()*this.amount);
    }


    public String getName() {
        return food.getName();
    }


    public String getUnit() {
        return food.getUnit();
    }

    public String getMeal() {
        return meal;
    }
    public void setMeal(String meal) {
        this.meal = meal;
    }
}
