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


}
