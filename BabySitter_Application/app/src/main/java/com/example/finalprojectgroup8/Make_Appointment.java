package com.example.finalprojectgroup8;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Make_Appointment extends AppCompatActivity {
    CalendarView calender;
    TextView dateview,Time_View;
    SharedPreferences preferences;
    DatePickerTimeline datePickerTimeline;
    TimePicker timedata;
    Button send_request;
    DatabaseReference reference;
    final String[] fday = new String[1];
    final String[] fmonth = new String[1];
    final String[] fyear = new String[1];
    final String[] fhour = new String[1];
    final String[] fminutes = new String[1];
    final String[] fam_pm = new String[1];
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__appointment);
        preferences = this.getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dateview = (TextView) findViewById(R.id.date_view);
        Time_View = (TextView) findViewById(R.id.time_view);
        String [] todate;
        todate=currentDate.split("-");
        datePickerTimeline = findViewById(R.id.datepickertimeline);
        datePickerTimeline.setInitialDate(Integer.parseInt(todate[2]), Integer.parseInt(todate[1]), Integer.parseInt(todate[0]));
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                dateview.setText("Selected date : "+day+"-"+month+"-"+year);
                fday[0] = String.valueOf(day);
                fmonth[0] = String.valueOf(month);
                fyear[0] = String.valueOf(year);
                // Do Something
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });
        timedata = findViewById(R.id.timepickertimeline);
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String [] totime = currentTime.split(":");

        java.util.Formatter timeF = new java.util.Formatter();
        timeF.format("Time defaulted to %d:%02d", timedata.getCurrentHour(),timedata.getCurrentMinute());
        timedata.setIs24HourView(true);
        timedata.setCurrentHour(new Integer(10));
        timedata.setCurrentMinute(new Integer(10));
        timedata.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String am_pm;
                if(hourOfDay > 12)
                {
                    am_pm = "PM";
                    hourOfDay   = hourOfDay - 12;
                }
                else
                {
                    am_pm="AM";
                }
                Time_View.setText("Selected Time: "+ hourOfDay +":"+ minute+" "+am_pm);
                fhour[0] = String.valueOf(hourOfDay);
                fminutes[0] = String.valueOf(minute);
                fam_pm[0] = am_pm;
            }
        });
        final String user_name = getIntent().getStringExtra("personname");
        send_request = findViewById(R.id.fixmake);
        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String final_date,final_time;
                final_date =fday[0]+"-"+fmonth[0]+"-"+fyear[0];
                final_time = fhour[0]+":"+fminutes[0]+" "+fam_pm[0];
                String appointerid=preferences.getString("username",null);
                Log.d("data : ", final_date + final_date + user_name+appointerid);
                set_appointment(final_date,final_time,user_name,appointerid);

                finish();
            }
        });


    }

    private void set_appointment(String final_date, String final_time, String user_name,String appointerid) {

        reference = FirebaseDatabase.getInstance().getReference("appointments");
        reference = reference.child(user_name).child(appointerid);
        reference.child("appointerid").setValue(appointerid);
        reference.child("time").setValue(final_time);
        reference.child("date").setValue(final_date);
    }
}