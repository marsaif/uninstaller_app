package com.example.app_uninstaller.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.app_uninstaller.models.App;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    PackageManager pm ;

    public AppUtils(Context context)
    {
        pm = context.getPackageManager() ;
    }

    public List<App> getInstalledApps()
    {

        List<App> list = new ArrayList<>() ;

        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for(ApplicationInfo app : apps) {

            // check if the app is not a system app
            if (!((app.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) >0 ))
            {

              // get the name of the app
              String appName =(String) pm.getApplicationLabel(app) ;

              // get the icon of the app
              Drawable appIcon = (Drawable) pm.getApplicationIcon(app) ;

              // get the package of the app
              String appPackage = app.packageName ;

              // get the installation date of the
              String appInstalledDate = getAppInstalledDate(appPackage) ;

              // get the version of the app
              String appVersion = getAppVersion(appPackage) ;

              double appSize = getAppSize(appPackage) ;

              App application = new App(appName,appIcon,appInstalledDate,appVersion,appPackage,appSize);
              list.add(application) ;
            }
        }

        return list ;
    }


    public String getAppInstalledDate(String packageName)
    {
        String appInstalledDate = "" ;
        try {
            Long installTime = pm.getPackageInfo(packageName,0).firstInstallTime;
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
            appInstalledDate = sdf.format(installTime);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appInstalledDate ;
    }

    public String getAppVersion(String packageName )
    {
        String appVersion = "" ;
        try {
            appVersion = pm.getPackageInfo(packageName,0).versionName ;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion ;
    }

    public double getAppSize(String packageName)
    {
        double size = 0 ;
        try {
            System.out.println(pm.getApplicationInfo(packageName, 0).publicSourceDir);
            File file = new File(pm.getApplicationInfo(packageName, 0).publicSourceDir);
            size = file.length() ;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        size = size / 1000000.0;
        return size ;
    }

    public boolean isAppDeleted(String packageName)
    {
        boolean appDeleted = true ;
        try {
            pm.getApplicationInfo(packageName,0);
            appDeleted = false ;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appDeleted ;
    }

}
