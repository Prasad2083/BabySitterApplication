package com.example.finalprojectgroup8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Availability extends AppCompatActivity {
    CheckBox sun,mon,tue,wed,thurs,fri,sat;
    TextView headi;
    Boolean bsun,bmon,btue,bwed,bthurs,bfri,bsat,sunday,monday,tuesday,wednesday,thursday,friday,saturday;;
    Button setav;
    String usernamesession;
    FirebaseDatabase rootnode;
    String status;
    DatabaseReference reference,newreference,subref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        SharedPreferences preferences = getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);
        sun = findViewById(R.id.sunday);
        mon = findViewById(R.id.monday);
        tue = findViewById(R.id.tuesday);
        wed = findViewById(R.id.wednesday);
        thurs =findViewById(R.id.thursday);
        fri = findViewById(R.id.friday);
        sat = findViewById(R.id.saturday);
        setav = findViewById(R.id.setAv);
        final Boolean userbool = preferences.getBoolean("asNanny",true);
        usernamesession = preferences.getString("username", null);
        if(userbool == true)
        {
            reference = FirebaseDatabase.getInstance().getReference("Profile Creation AsNanny");
            status = "Availability";
        }
        else
        {
            reference = FirebaseDatabase.getInstance().getReference("Profile Creation For Nanny");
            status = "Requirement";
        }
        headi = findViewById(R.id.data_status);
        headi.setText(status);
        Intent intent=getIntent();
        monday = intent.getBooleanExtra("monday",false);
        tuesday = intent.getBooleanExtra("tuesday",false);
        wednesday = intent.getBooleanExtra("wednesday",false);
        thursday = intent.getBooleanExtra("thursday",false);
        friday = intent.getBooleanExtra("friday",false);
        saturday = intent.getBooleanExtra("saturday",false);
        sunday = intent.getBooleanExtra("sunday",false);
        mon.setChecked(monday);
        tue.setChecked(tuesday);
        wed.setChecked(wednesday);
        thurs.setChecked(thursday);
        fri.setChecked(friday);
        sat.setChecked(saturday);
        sun.setChecked(sunday);
        newreference = reference.child(usernamesession);
        subref = newreference.child("availability");
        setav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sun.isChecked())
                    bsun=true;
                else
                    bsun=false;
                subref.child("sunday").setValue(bsun);
                if(mon.isChecked())
                    bmon=true;
                else
                    bmon=false;
                subref.child("monday").setValue(bmon);
                if(tue.isChecked())
                    btue=true;
                else
                    btue=false;
                subref.child("tuesday").setValue(btue);
                if(wed.isChecked())
                    bwed=true;
                else
                    bwed=false;
                subref.child("wednesday").setValue(bwed);
                if(thurs.isChecked())
                    bthurs=true;
                else
                    bthurs=false;
                subref.child("thursday").setValue(bthurs);
                if(fri.isChecked())
                    bfri=true;
                else
                    bfri=false;
                subref.child("friday").setValue(bfri);
                if(sat.isChecked())
                    bsat=true;
                else
                    bsat=false;
                subref.child("saturday").setValue(bsat);

                finish();
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
            }

        });
    }
}