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
    Movie_Fragment movie_fragment;
    TravelFragment travel_fragment;
    AppDatabase db;
    int itype;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        done = findViewById(R.id.done);
        event = findViewById(R.id.event);
        birthday = findViewById(R.id.birthday);
        travel = findViewById(R.id.travel);
        movie = findViewById(R.id.movie);
        i = getIntent();
        date = findViewById(R.id.set_date);
        date1 = i.getStringExtra("date");
        itype = i.getIntExtra("itype", 1);
        if (itype == 2)
            update();
        date.setText(date1);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        event_fragment = new Event_fragement();
        birthday_fragment = new Birthday_fragment();
        movie_fragment = new Movie_Fragment();
        travel_fragment = new TravelFragment();
        ft.replace(R.id.placeholder, event_fragment, "Event Fragment");
        ft.addToBackStack("Event Fragment");
        ft.commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.placeholder);
        // event_fragement.date.setText(date1);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment f = fragmentManager.findFragmentById(R.id.placeholder);
                String tag = f.getTag();
                if (tag.equals("Event Fragment")) {
                    String event = event_fragment.event_name.getEditText().getText().toString();
                    if(event.isEmpty())
                        Toast.makeText(getApplicationContext(),"Please enter the event", Toast.LENGTH_LONG).show();
                    else
                    {
                        Toast.makeText(getApplicationContext(), event, Toast.LENGTH_LONG).show();
                        title = event;
                        desc = "";
                        type = 1;
                        saveTask();
                        finish();
                    }

                }
                if (tag.equals("Birthday Fragment")) {
                    String name = birthday_fragment.name.getEditText().getText().toString();
                    if(name.isEmpty())
                        Toast.makeText(getApplicationContext(),"PLease enter a name", Toast.LENGTH_LONG).show();
                    else
                    {
                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                        title = name;
                        desc = "";
                        type = 2;
                        saveTask();
                        finish();
                    }

                }
                if (tag.equals("Movie Fragment")) {
                    String name = movie_fragment.movie.getEditText().getText().toString();
                    String time = movie_fragment.time.getEditText().getText().toString();
                    if(name.isEmpty() && time.isEmpty())
                        Toast.makeText(getApplicationContext(),"Please enter a name or time", Toast.LENGTH_LONG).show();
                    else
                    {
                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                        desc = "Time: " + movie_fragment.time.getEditText().getText().toString();
                        title = name;
                        type = 3;
                        saveTask();
                        finish();
                    }

                }
                if (tag.equals("Travel Fragment")) {
                    String name = travel_fragment.dest.getEditText().getText().toString();
                    String time = travel_fragment.time.getEditText().getText().toString();
                    if(name.isEmpty() && time.isEmpty())
                        Toast.makeText(getApplicationContext(),"Please enter a destination or time", Toast.LENGTH_LONG).show();
                    else
                    {
                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                        desc = "Time: " + travel_fragment.time.getEditText().getText().toString();
                        title = name;

                        type = 4;
                        saveTask();
                        finish();
                    }

                }



                /*Intent i=new Intent(EventPage.this,MainActivity.class);
                startActivity(i);*/

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
        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, movie_fragment, "Movie Fragment");
                ft.addToBackStack("Movie Fragment");

                ft.commit();
                reset(3);
            }
        });
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, travel_fragment, "Travel Fragment");
                ft.addToBackStack("Travel Fragment");

                ft.commit();
                reset(4);
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

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Events").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.events_dao().insert(new Event_db(0, type, date1, title, desc));
            }
        });
        Toast.makeText(getApplicationContext(), "Data Save", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void update() {

        done.setText("UPDATE");

    }
}



