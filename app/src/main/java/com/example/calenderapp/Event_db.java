package com.example.calenderapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Events", indices = {@Index(value = "id")})

public class Event_db implements Serializable {
    @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name="id")
            int id;



    @ColumnInfo(name = "Type")
            @NonNull
           int type;


    @ColumnInfo(name = "Date")
            @NonNull
            String date;
    @ColumnInfo(name = "Title")
            @NonNull
            String title;
    @ColumnInfo(name = "Description")
            String description;

    public Event_db(int id, int type, String date, String title, String description) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public Event_db() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
