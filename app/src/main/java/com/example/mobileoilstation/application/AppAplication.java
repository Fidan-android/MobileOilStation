package com.example.mobileoilstation.application;

import android.app.Application;

import com.example.mobileoilstation.R;
import com.yandex.mapkit.MapKitFactory;

public class AppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(getResources().getString(R.string.apiKey));
    }
}
