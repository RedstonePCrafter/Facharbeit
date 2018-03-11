package com.example.hessel.facharbeit.PlanUtils;

/**
 * Created by hessel on 23.01.2018.
 */

public class Set {
    private String Gewicht;
    private String Wiederholungen;

    public Set(String gewicht, String wiederholungen) {
        Gewicht = gewicht;
        Wiederholungen = wiederholungen;
    }

    public String getGewicht() {
        return Gewicht;
    }

    public void setGewicht(String gewicht) {
        Gewicht = gewicht;
    }

    public String getWiederholungen() {
        return Wiederholungen;
    }

    public void setWiederholungen(String wiederholungen) {
        Wiederholungen = wiederholungen;
    }
}
