package com.example.finalprojectgroup8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {
    ImageView profilepicture;
    TextView profilename,profilelocation,profiledescription,profilewage,profileexperience,profileage,profilestatus;
    CheckBox checkbox1,checkbox2,checkbox3,checkbox4,checkbox5,checkbox6,checkbox7;

    //TextView profileemail;
    Button rate,makeappo;

    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        int pic = R.drawable.logo;
        profilepicture = findViewById(R.id.asprofilepic);
        profilename = findViewById(R.id.asprofilename);
        profilelocation = findViewById(R.id.asprofilelocation);
        profileage = findViewById(R.id.asprofileage);
        profiledescription = findViewById(R.id.asprofiledescription);
        //profileemail = findViewById(R.id.asprofileemail);
        profileexperience = findViewById(R.id.asprofileexperience);
        profilewage = findViewById(R.id.asprofilewage);
        profilestatus = findViewById(R.id.asstatus);
        checkbox1 = findViewById(R.id.sun);
        checkbox2 = findViewById(R.id.mon);
        checkbox3 = findViewById(R.id.tue);
        checkbox4 = findViewById(R.id.wed);
        checkbox5 = findViewById(R.id.thu);
        checkbox6 = findViewById(R.id.fri);
        checkbox7 = findViewById(R.id.sat);


        rate = findViewById(R.id.asrate);
        makeappo = findViewById(R.id.asmake);
        //storeusername = getIntent().getStringExtra("takeusername");
        check();
        setData();
        availabilitydata();
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DetailsActivity.this,Rating.class);
                i.putExtra("username",name);
                i.putExtra("status","As Nanny");
                startActivity(i);
            }
        });
        makeappo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,Make_Appointment.class);
                intent.putExtra("personname",name);
                startActivity(intent);
            }
        });
    }
    private void check(){
        if(getIntent().hasExtra("viewname")){
            name=getIntent().getStringExtra("viewname").toString();
            //Log.d("name test",name);

        }
        else {
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();

        }
    }
    private void setData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profile Creation AsNanny");
        Query checkuser = reference.orderByChild("username").equalTo(name);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String Act_Serv;
                    Log.d("name test",name);
                    String dbage= (String) dataSnapshot.child(name).child("age").getValue(String.class);
                    String dbwage= (String) dataSnapshot.child(name).child("rate").getValue(String.class);
                    String dblocation=dataSnapshot.child(name).child("location").getValue(String.class);
                    String dbexperience= (String) dataSnapshot.child(name).child("experience").getValue(String.class);
                    String dbdescription=dataSnapshot.child(name).child("description").getValue(String.class);
                    String serv_status = String.valueOf(dataSnapshot.child(name).child("ServiceProvide").getValue(Integer.class));
                    if (serv_status==null){
                        serv_status="1000";
                    }
                    if(Integer.parseInt(serv_status)==1)
                        Act_Serv="Only Children";
                    else if(Integer.parseInt(serv_status)==2)
                        Act_Serv="Only Oldsters";
                    else if(Integer.parseInt(serv_status)==3)
                        Act_Serv="Both";
                    else
                        Act_Serv="Not available";
                    int pic = R.drawable.logo;
                    profilepicture.setImageResource(pic);
                    profilename.setText(name);
                    profilelocation.setText("Location: "+ dblocation);
                    profileage.setText("Age: "+dbage.toString()+" Years");
                    profiledescription.setText("Description: \n"+dbdescription);
                    profileexperience.setText("Experience "+dbexperience.toString()+" Years");
                    profilewage.setText("Expected Wage: $"+dbwage.toString());
                    profilestatus.setText("Service data: "+Act_Serv);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void availabilitydata(){
        DatabaseReference availablereference = FirebaseDatabase.getInstance().getReference().child("Profile Creation AsNanny");
        availablereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean sunday = (boolean)dataSnapshot.child(name).child("availability").child("sunday").getValue(boolean.class);
                boolean monday = (boolean)dataSnapshot.child(name).child("availability").child("monday").getValue(boolean.class);
                boolean tuesday = (boolean)dataSnapshot.child(name).child("availability").child("tuesday").getValue(boolean.class);
                boolean wednesday = (boolean)dataSnapshot.child(name).child("availability").child("wednesday").getValue(boolean.class);
                boolean thursday = (boolean)dataSnapshot.child(name).child("availability").child("thursday").getValue(boolean.class);
                boolean friday = (boolean)dataSnapshot.child(name).child("availability").child("friday").getValue(boolean.class);
                boolean saturday = (boolean)dataSnapshot.child(name).child("availability").child("saturday").getValue(boolean.class);
                if(sunday){
                    checkbox1.setChecked(true);
                }
                if(monday){
                    checkbox2.setChecked(true);
                }
                if(tuesday){
                    checkbox3.setChecked(true);
                }
                if(wednesday){
                    checkbox4.setChecked(true);
                }
                if(thursday){
                    checkbox5.setChecked(true);
                }
                if(friday){
                    checkbox6.setChecked(true);
                }
                if(saturday){
                    checkbox7.setChecked(true);
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}