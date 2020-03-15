package com.example.calenderapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Events_dao {
    @Query("Select * from Events")
    List<Event_db> getAll();
    @Query("Delete from Events")
    void deleteAll();
    @Insert
    void insert(Event_db event_db);
    @Update
    void update(Event_db event_db);
    @Delete
    void delete(Event_db event_db);
    @Query("Select * from Events where Date = :date")
   List<Event_db> getAllEventsOnDate(String date);
    @Query("Delete from Events where id = :id")
    void delete(int id);
    @Query("Update Events set title = :title,description = :description where id = :id")
    void update(String title, String description,int id);
    @Query("Select * from Events where id = :id")
    Event_db[] getEvent(int id);


}
