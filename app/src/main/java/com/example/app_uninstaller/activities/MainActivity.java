package com.example.app_uninstaller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app_uninstaller.R;
import com.example.app_uninstaller.models.App;
import com.example.app_uninstaller.utils.AppUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<App> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = AppUtils.getInstalledApps(this);
        System.out.println(list);
    }
}