package com.example.hessel.facharbeit.PlanUtils;

import java.util.ArrayList;


/**
 * Created by hessel on 23.01.2018.
 */

public class Uebung {
    private static final String TAG = "Uebung";
    private String name;
    private String Muskelgruppe;
    private ArrayList<Set> sets;

    public Uebung(String name, String muskelgruppe,ArrayList<Set> sets) {
        this.name = name;
        this.sets = sets;
        this.Muskelgruppe = muskelgruppe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuskelgruppe() {
        return Muskelgruppe;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }
}


