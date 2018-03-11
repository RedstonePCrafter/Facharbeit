package com.example.hessel.facharbeit.Utils;

/**
 * Created by hessel on 15.02.2018.
 */

public class GridItem {
    private String text;
    private int icon;

    public GridItem(String text,int icon) {
        this.text=text;
        this.icon=icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
