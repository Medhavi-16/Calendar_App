package com.example.calenderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibotta.android.support.pickerdialogs.SupportedDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CalendarView calendar;
    SearchView search;
    TextView date;
    ExtendedFloatingActionButton add;
    RecyclerView eventList;
    SupportedDatePickerDialog.OnDateSetListener dateSetListener;
    private AppViewModel appViewModel;
    EventsAdapter adapter;
    public static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendar=findViewById(R.id.calendar);
        add=findViewById(R.id.add);
        eventList=findViewById(R.id.event_list);
        eventList.setLayoutManager(new LinearLayoutManager(this));

        date=findViewById(R.id.date);
        //search.setQueryHint("31/12/2000");
        //search.setSubmitButtonEnabled(true);
        final  Calendar calendar1 = Calendar.getInstance();
        int year=calendar1.get(Calendar.YEAR);
        int month=calendar1.get(Calendar.MONTH);
        int day_of_month=calendar1.get(Calendar.DAY_OF_MONTH);
        int weekday=calendar1.get(Calendar.DAY_OF_WEEK);
        String d=day_of_month+"/"+month+"/"+year+"\n"+getDay(weekday);
        date.setText(d);
        //appViewModel= ViewModelProviders.of(this).get(AppViewModel.class);
        appDatabase= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"Events").allowMainThreadQueries().build();

        getEvents();
        //search=findViewById(R.id.search);


        add.setOnClickListener(v -> {
            Intent i=new Intent(MainActivity.this,EventPage.class);
            i.putExtra("date",date.getText().toString());
            startActivity(i);
        });




        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = sdf.format(new Date(calendar.getDate()));
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                long milliTime = calendar1.getTimeInMillis();
                calendar.setDate (milliTime, true, true);
                int weekday=calendar1.get(Calendar.DAY_OF_WEEK);
                String d=dayOfMonth+"/"+(month+1)+"/"+year+"\n"+getDay(weekday);
                date.setText(d);
                Toast.makeText(getApplicationContext(),d,Toast.LENGTH_LONG).show();
                getEvents();

            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int year=calendar1.get(Calendar.YEAR);
                int month=calendar1.get(Calendar.MONTH);
                int day_of_month=calendar1.get(Calendar.DAY_OF_MONTH);
                SupportedDatePickerDialog dialog=new SupportedDatePickerDialog(MainActivity.this,R.style.SpinnerDatePickerDialogTheme,dateSetListener,year,month,day_of_month);


               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

               dialog.show();

            }
        });
        dateSetListener=new SupportedDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;

                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month-1);
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                long milliTime = calendar1.getTimeInMillis();
                calendar.setDate (milliTime, true, true);
                int weekday=calendar1.get(Calendar.DAY_OF_WEEK);
                String d=dayOfMonth+"/"+month+"/"+year+"\n"+getDay(weekday);
                date.setText(d);
                Toast.makeText(getApplicationContext(),d,Toast.LENGTH_LONG).show();
                getEvents();
            }
        };



    }
    private String getDay(int d)
    {
        switch(d)
        {
            case 2: return "Monday";
            case 3: return "Tuesday";
            case 4: return "Wednesday";
            case 5: return "Thursday";
            case 6: return "Friday";
            case 7: return "Saturday";
            default: return "Sunday";
        }
    }
    private void getEvents()
    {
        /*class GetEvents extends AsyncTask<Void, Void, List<Event_db>> {

            @Override
            protected List<Event_db> doInBackground(Void... voids) {
                String d=date.getText().toString();
               *//* List<Event_db> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .events_dao()
                        .getAllEventsOnDate(d);*//*
               List<Event_db> event_dbList=appDatabase.events_dao().getAllEventsOnDate(d);
               // Toast.makeText(getApplicationContext(),event_dbList.size(),Toast.LENGTH_LONG).show();

                return event_dbList;
            }

            @Override
            protected void onPostExecute(List<Event_db> tasks) {
                super.onPostExecute(tasks);
                EventsAdapter adapter = new EventsAdapter(MainActivity.this, tasks);
                eventList.setAdapter(adapter);
            }
        }

        GetEvents gt = new GetEvents();
        gt.execute();
*/
       /* appViewModel.getAllEventsOnDate(d).observe(this, new Observer<List<Event_db>>() {
            @Override
            public void onChanged(@Nullable final List<Event_db> event_dbs) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(event_dbs);
                eventList.setAdapter(adapter);
            }

    });*/
      /*  class GetEvents extends AsyncTask<Void, Void, List<Event_db>> {

        @Override
        protected List&lt;MyDataList> doInBackground(Void... voids) {
        List&lt;MyDataList>myDataLists=MainActivity.myDatabase.myDao().getMyData();
        return myDataLists;

    }

        @Override
        protected void onPostExecute(List&lt;MyDataList> myDataList) {
        MyAdapter adapter=new MyAdapter(myDataList);
        rv.setAdapter(adapter);
        super.onPostExecute(myDataList);
    }
        }
        GetData gd=new GetData();
        gd.execute();
    }*/
        Runnable r = new Runnable(){
            @Override
            public void run() {
                String d=date.getText().toString();
                List<Event_db> list = appDatabase.events_dao().getAllEventsOnDate(d);
                EventsAdapter adapter = new EventsAdapter(MainActivity.this, list);
                adapter.notifyDataSetChanged();
                eventList.setAdapter(adapter);

            }
        };

        Thread newThread= new Thread(r);
        newThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getEvents();
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

