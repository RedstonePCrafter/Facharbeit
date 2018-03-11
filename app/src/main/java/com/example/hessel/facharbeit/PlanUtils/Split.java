package com.example.hessel.facharbeit.PlanUtils;

import java.util.ArrayList;

/**
 * Created by hessel on 23.01.2018.
 */

public class Split {
    private String name;
    private ArrayList<Uebung> uebunglist;

    public Split(String name, ArrayList<Uebung> uebunglist) {
        this.name = name;
        this.uebunglist = uebunglist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Uebung> getUebunglist() {
        return uebunglist;
    }

    public void setUebunglist(ArrayList<Uebung> uebunglist) {
        this.uebunglist = uebunglist;
    }
}
