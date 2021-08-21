package com.example.app_uninstaller.models;

import android.graphics.drawable.Drawable;

public class App {
    private String name ;
    private Drawable icon ;

    public App() {

    }

    public App(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                '}';
    }
}
