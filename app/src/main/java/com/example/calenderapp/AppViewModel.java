package com.example.calenderapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppViewModel extends AndroidViewModel {
    private AppRepository mRepository;
    Events_dao events_dao;
    private ExecutorService executorService;
    private LiveData<List<Event_db>> listLiveData;
    String date;

    public AppViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
       // events_dao=AppDatabase.events_dao();
        date=mRepository.date;
        executorService = Executors.newSingleThreadExecutor();
        listLiveData = mRepository.getAllEventsOnDate(date);
    }

    LiveData<List<Event_db>> getAllEventsOnDate(String date) { return listLiveData; }

    public void insert(Event_db event) { executorService.execute(()->insert(event)); }
}
