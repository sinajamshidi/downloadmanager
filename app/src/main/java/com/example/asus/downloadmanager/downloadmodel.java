package com.example.asus.downloadmanager;

/**
 * Created by Asus on 8/19/2017.
 */

public class downloadmodel {
    String name;
    int percent;

    public downloadmodel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public downloadmodel(String name, int percent) {
        this.name = name;
        this.percent = percent;
    }
}
