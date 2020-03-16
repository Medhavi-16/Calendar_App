package com.example.calenderapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private Events_dao events_dao;
    private LiveData<List<Event_db>> listLiveData;
    String date;
    AppRepository(Application application) {
       // AppDatabase db = AppDatabase.getDatabase(application);
        //events_dao = db.events_dao();

        //listLiveData = events_dao.getAllEventsOnDate(date);

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Event_db>> getAllEventsOnDate(String date) {
        return listLiveData;
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    /*void insert(Event_db event) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
           events_dao.insert(event);
        });*/
    }

