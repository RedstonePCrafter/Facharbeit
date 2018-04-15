package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.hessel.facharbeit.SearchFood.Calorie;
import com.example.hessel.facharbeit.SearchFood.FoodCount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hessel on 09.04.2018.
 */

public class CalorieUtils {
        private static final String Tag="CalorieUtils";
        private static SharedPreferences SP;

    public static ArrayList<Calorie> getCalorieList(Context context, ArrayList<String> datelist){
        SP = PreferenceManager.getDefaultSharedPreferences(context);

        ArrayList<Calorie> calorielist = new ArrayList<>();
        for (String date:datelist) {
            int calorie = 0;
            for(FoodCount foodCount: loadFoodCountList(SP.getString("pref_food_breakfast_" + date, "[]"))){
                calorie +=foodCount.getCalories();
            }
            for(FoodCount foodCount: loadFoodCountList(SP.getString("pref_food_lunch_" + date, "[]"))){
                calorie +=foodCount.getCalories();
            }
            for(FoodCount foodCount: loadFoodCountList(SP.getString("pref_food_dinner_" + date, "[]"))){
                calorie +=foodCount.getCalories();
            }
            for(FoodCount foodCount: loadFoodCountList(SP.getString("pref_food_snack_" + date, "[]"))){
                calorie +=foodCount.getCalories();
            }
            calorielist.add(new Calorie(calorie,date));
        }
        return calorielist;
    }

    public static ArrayList<FoodCount> loadFoodCountList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FoodCount>>() {}.getType();
        return gson.fromJson(json,type);
    }

    public static int calculateCalorieIntake(Context context){
        SP = PreferenceManager.getDefaultSharedPreferences(context);
        double calorieIntake= 0;
        float weight = Float.parseFloat(SP.getString("pref_weight","0"));
        float height = Float.parseFloat(SP.getString("pref_height","0"));
        float age = Float.parseFloat(SP.getString("pref_age","0"));
        String sexe = SP.getString("pref_sexe","");
        String activityLevel = SP.getString("pref_activity_level","");
        Log.d("utils",activityLevel);

        if (!sexe.equals("") || !activityLevel.equals("")){
            if(sexe.equals("Male")){
                calorieIntake = 66.4730 + 13.7516 * weight + 5.0033 * height - 6.7550 * age;

            }else{
                calorieIntake = 655.0955 + 9.5634 * weight + 1.8496 * height - 4.6756 * age;
            }
            switch (activityLevel){
                case "Little to no exercise":
                    calorieIntake = 1.2*calorieIntake;
                    break;
                case "Light exercise (1–3 days per week)":
                    calorieIntake = 1.375*calorieIntake;
                    break;
                case "Moderate exercise (3–5 days per week)":
                    calorieIntake = 1.55*calorieIntake;
                    break;
                case "Heavy exercise (6–7 days per week)":
                    calorieIntake = 1.725*calorieIntake;
                    break;
                case "Very heavy exercise (twice per day, extra heavy workouts)":
                    calorieIntake = 1.9*calorieIntake;
                    break;
            }
        }else {
            return 3000;
        }
        return (int) calorieIntake;
    }
}
