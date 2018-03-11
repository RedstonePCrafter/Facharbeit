package com.example.hessel.facharbeit.PlanUtils;

import java.util.ArrayList;

/**
 * Created by hessel on 23.01.2018.
 */

public class Plan {
    private String name;
    private String dauer;
    private ArrayList<Split> splitlist;
    private int splitanzahl;

    public Plan(String name, String dauer, ArrayList<Split> splitlist){
        this.name = name;
        this.dauer = dauer;
        this.splitlist = splitlist;
        this.splitanzahl = this.splitlist.size();


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDauer() {
        return dauer;
    }

    public void setDauer(String dauer) {
        this.dauer = dauer;
    }

    public ArrayList<Split> getSplitlist() {
        return splitlist;
    }

    public void setSplitlist(ArrayList<Split> splitlist) {
        this.splitlist = splitlist;
        setSplitanzahl(this.splitlist.size());
    }

    public int getSplitanzahl() {
        return splitanzahl;
    }

    public void setSplitanzahl(int splitanzahl) {
        this.splitanzahl = splitanzahl;
    }
}
