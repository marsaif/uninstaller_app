package com.example.app_uninstaller.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.app_uninstaller.models.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    public List<App> getInstalledApps(Context context)
    {

        List<App> list = new ArrayList<>() ;

        PackageManager pm = context.getPackageManager();
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
              String appInstalledDate = getAppInstalledDate(app,pm) ;

              // get the version of the app
              String appVersion = getAppVersion(app,pm) ;


              App application = new App(appName,appIcon,appInstalledDate,appVersion,appPackage);
              list.add(application) ;
            }
        }

        return list ;
    }


    public String getAppInstalledDate(ApplicationInfo app , PackageManager pm)
    {
        String appInstalledDate = "" ;
        try {
            Long installTime = pm.getPackageInfo(app.packageName,0).firstInstallTime;
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
            appInstalledDate = sdf.format(installTime);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appInstalledDate ;
    }

    public String getAppVersion(ApplicationInfo app , PackageManager pm)
    {
        String appVersion = "" ;
        try {
            appVersion = pm.getPackageInfo(app.packageName,0).versionName ;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion ;
    }

}
