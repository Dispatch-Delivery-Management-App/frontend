package com.fullstack.frontend.ui.notification;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

public class DispatchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Stetho.initializeWithDefaults(this);
        FirebaseMessaging.getInstance().subscribeToTopic("android");
    }
}