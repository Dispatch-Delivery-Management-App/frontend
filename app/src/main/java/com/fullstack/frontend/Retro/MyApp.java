package com.fullstack.frontend.Retro;

import android.app.Application;
import android.content.Context;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.ashokvarma.gander.persistence.GanderPersistence;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        // For Persistence (Uses Room to store the calls in DB)
        Gander.setGanderStorage(GanderPersistence.getInstance(MyApp.getContext()));
// For In Memory DB (Data retained in memory lost on app close)
        Gander.setGanderStorage(GanderIMDB.getInstance());

        FirebaseMessaging.getInstance().subscribeToTopic("android");

    }
}