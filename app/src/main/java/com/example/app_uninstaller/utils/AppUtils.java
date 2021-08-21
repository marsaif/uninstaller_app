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

    public static List<App> getInstalledApps(Context context)
    {

        List<App> list = new ArrayList<>() ;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for(ApplicationInfo app : apps) {
            if (!((app.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) >0 ))
            {
              String appName =(String) pm.getApplicationLabel(app) ;
              Drawable icon = (Drawable) pm.getApplicationIcon(app) ;
              String appPackage = app.packageName ;
              String InstalledDate = "" ;
              String appVersion = "" ;
              try {
                Long installTime = pm.getPackageInfo(app.packageName,0).firstInstallTime ;
                appVersion = pm.getPackageInfo(app.packageName,0).versionName ;
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
                InstalledDate = sdf.format(installTime);

              } catch (Exception e)
              {
                  System.out.println("err : " + e.getMessage());
              }
              App application = new App(appName,icon,InstalledDate,appVersion,appPackage);
              list.add(application) ;
            }
        }

        return list ;
    }

}
