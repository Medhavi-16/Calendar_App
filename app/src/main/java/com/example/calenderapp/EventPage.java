package com.example.calenderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventPage extends AppCompatActivity {
    ImageView event, birthday, travel, movie;
    Button done;
    Event_fragement event_fragment;
    Birthday_fragment birthday_fragment;
    TextView date;
    String title;
    String desc;
    int type;
    String date1;
    private AppViewModel appViewModel;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        done = findViewById(R.id.done);
        event = findViewById(R.id.event);
        birthday = findViewById(R.id.birthday);
        travel = findViewById(R.id.travel);
        movie = findViewById(R.id.movie);
        Intent i = getIntent();
        date = findViewById(R.id.set_date);
        date1 = i.getStringExtra("date");
        date.setText(date1);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        event_fragment = new Event_fragement();
        birthday_fragment = new Birthday_fragment();
        ft.replace(R.id.placeholder, event_fragment, "Event Fragment");
        ft.addToBackStack("Event Fragment");
        ft.commit();
        // event_fragement.date.setText(date1);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment f = fragmentManager.findFragmentById(R.id.placeholder);
                String tag = f.getTag();
                if (tag.equals("Event Fragment")) {
                    String event = event_fragment.event_name.getEditText().getText().toString();
                    Toast.makeText(getApplicationContext(), event, Toast.LENGTH_LONG).show();
                    title = event;
                    desc = " ";
                    type = 1;
                }
                if (tag.equals("Birthday Fragment")) {
                    String name = birthday_fragment.name.getEditText().getText().toString();
                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                    title = name;
                    desc = " ";
                    type = 2;
                }
                saveTask();

                Intent i=new Intent(EventPage.this,MainActivity.class);
                startActivity(i);
            }



        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, event_fragment, "Event Fragment");
                ft.addToBackStack("Event Fragment");

                ft.commit();
                reset(1);


            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, birthday_fragment, "Birthday Fragment");
                ft.addToBackStack("Birthday Fragment");

                ft.commit();
                reset(2);


            }
        });


    }

    private void reset(int i) {
        switch (i) {
            case 1:
                event.setImageResource(R.drawable.ic_event);
                birthday.setImageResource(R.drawable.ic_cakebl);
                movie.setImageResource(R.drawable.ic_moviebl);
                travel.setImageResource(R.drawable.ic_flightbl);

                break;
            case 2:
                event.setImageResource(R.drawable.ic_eventbl);
                birthday.setImageResource(R.drawable.ic_cake);
                movie.setImageResource(R.drawable.ic_moviebl);
                travel.setImageResource(R.drawable.ic_flightbl);
                break;
            case 3:
                event.setImageResource(R.drawable.ic_eventbl);
                birthday.setImageResource(R.drawable.ic_cakebl);
                movie.setImageResource(R.drawable.ic_movie);
                travel.setImageResource(R.drawable.ic_flightbl);
                break;
            default:
                event.setImageResource(R.drawable.ic_eventbl);
                birthday.setImageResource(R.drawable.ic_cakebl);
                movie.setImageResource(R.drawable.ic_moviebl);
                travel.setImageResource(R.drawable.ic_flight);
        }

    }

    private void saveTask() {
            /*class SaveTask extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                   *//* Event_db eventDb=new Event_db();
                    eventDb.setDate(date1);
                    eventDb.setType(Integer.toString(type));
                    eventDb.setTitle(title);
                    eventDb.setDescription(desc);*//*
                   String date1=date.getText().toString();
                  // Toast.makeText(getApplicationContext(),date1,Toast.LENGTH_LONG).show();
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().events_dao().insert(new Event_db(0,type,date1,title,desc));
                    return null;

                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }

            SaveTask st = new SaveTask();
            st.execute();
            }*/
        //appViewModel.insert(new Event_db(0, type, date1, title, desc));
     //MainActivity.appDatabase.events_dao().insert(new Event_db(0, type, date1, title, desc));
        db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"Events").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.events_dao().insert(new Event_db(0, type, date1, title, desc));
            }
        });
        Toast.makeText(getApplicationContext(),"Data Save",Toast.LENGTH_LONG).show();
    }
}



