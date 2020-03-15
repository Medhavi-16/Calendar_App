package com.example.calenderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShowDetailedView extends AppCompatActivity {
    public static AppDatabase appDatabase;
    RecyclerView eventList;
    TextView date;
    String d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detailed_view);
        getSupportActionBar().hide();
        eventList=findViewById(R.id.eventList);
        date=findViewById(R.id.date);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        Intent i=getIntent();
       d=i.getStringExtra("date");
       date.setText(d);
        appDatabase= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"Events").allowMainThreadQueries().build();
        getEvents();
    }
    private void getEvents()
    {


        List<Event_db> list = appDatabase.events_dao().getAllEventsOnDate(d);
        Toast.makeText(getApplicationContext(),d,Toast.LENGTH_SHORT).show();
        EventsAdapter adapter = new EventsAdapter(ShowDetailedView.this, list);
        adapter.notifyDataSetChanged();
        eventList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getEvents();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getEvents();
    }
}
