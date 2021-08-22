package com.example.app_uninstaller.models;

import android.graphics.drawable.Drawable;

public class App {
    private String name ;
    private Drawable icon ;
    private String installedDate ;
    private String appVersion ;
    private String AppPackage ;
    private double appSize ;

    public App() {

    }

    public App(String name, Drawable icon, String installedDate , String appVersion , String appPackage , double appSize) {
        this.name = name;
        this.icon = icon;
        this.installedDate = installedDate;
        this.appVersion = appVersion ;
        this.AppPackage = appPackage ;
        this.appSize = appSize ;
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

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppPackage() {
        return AppPackage;
    }

    public void setAppPackage(String appPackage) {
        AppPackage = appPackage;
    }

    public double getAppSize() {
        return appSize;
    }

    public void setAppSize(double appSize) {
        this.appSize = appSize;
    }
}
