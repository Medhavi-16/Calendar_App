package com.example.calenderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Button showList;
    TextView date;
    ExtendedFloatingActionButton add;

    SupportedDatePickerDialog.OnDateSetListener dateSetListener;
    private AppViewModel appViewModel;
    EventsAdapter adapter;
    public static AppDatabase appDatabase;
    RecyclerView eventList;
    private int mCurrentItemPosition;
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
        eventList.addItemDecoration(new DividerItemDecoration(eventList.getContext(),DividerItemDecoration.VERTICAL));
        showList=findViewById(R.id.showList);
        date=findViewById(R.id.date);

        final  Calendar calendar1 = Calendar.getInstance();
        int year=calendar1.get(Calendar.YEAR);
        int month=calendar1.get(Calendar.MONTH);
        int day_of_month=calendar1.get(Calendar.DAY_OF_MONTH);
        int weekday=calendar1.get(Calendar.DAY_OF_WEEK);
        String d=day_of_month+"/"+(month+1)+"/"+year+"\n"+getDay(weekday);
        date.setText(d);
        //appViewModel= ViewModelProviders.of(this).get(AppViewModel.class);
        appDatabase= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"Events").allowMainThreadQueries().build();

        getEvents();
       showList.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(MainActivity.this,ShowDetailedView.class);
               i.putExtra("date",date.getText().toString());
               startActivity(i);
           }
       });



        add.setOnClickListener(v -> {
            Intent i=new Intent(MainActivity.this,EventPage.class);
            i.putExtra("date",date.getText().toString());
            i.putExtra("itype",1);
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

        String d=date.getText().toString();
        List<Event_db> list = appDatabase.events_dao().getAllEventsOnDate(d);
        Toast.makeText(getApplicationContext(),d,Toast.LENGTH_SHORT).show();
        adapter = new EventsAdapter(MainActivity.this, list);
        adapter.notifyDataSetChanged();
        eventList.setAdapter(adapter);
        registerForContextMenu(eventList);
        adapter.setOnLongItemClickListener(new EventsAdapter.onLongItemClickListener() {
            @Override
            public void ItemLongClicked(View v, int position) {
                mCurrentItemPosition = position;
                v.showContextMenu();
            }
        });


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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        menu.setHeaderTitle("Select The Action");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.delete){
            Toast.makeText(getApplicationContext(),"Delete",Toast.LENGTH_LONG).show();
            int id=adapter.getItem(mCurrentItemPosition).getId();
            appDatabase.events_dao().delete(id);
           // adapter.notifyDataSetChanged();
            getEvents();
        }
        else if(item.getItemId()==R.id.update){
            Toast.makeText(getApplicationContext(),"Update",Toast.LENGTH_LONG).show();
            int id=adapter.getItem(mCurrentItemPosition).getId();
            appDatabase.events_dao().delete(id);
           update();
        }else{
            return false;
        }
        return true;
    }
    private void  update()
    {

        Intent i=new Intent(MainActivity.this,EventPage.class);
        i.putExtra("date",date.getText().toString());
        i.putExtra("itype",2);

        startActivity(i);
    }
}

