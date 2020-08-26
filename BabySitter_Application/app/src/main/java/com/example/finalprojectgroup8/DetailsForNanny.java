package com.example.finalprojectgroup8;

import android.content.Intent;
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

public class DetailsForNanny extends AppCompatActivity {
    ImageView profilepicture;
    TextView profilename,profilelocation,profiledescription,profilewage,profilechildren,profilestatus;
    CheckBox checkbox1,checkbox2,checkbox3,checkbox4,checkbox5,checkbox6,checkbox7;
    Button rate,makeappo;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_for_nanny);
        int pic = R.drawable.logo;
        profilepicture = findViewById(R.id.forprofilepic);
        profilename = findViewById(R.id.forprofilename);
        profilelocation = findViewById(R.id.forprofilelocation);
        profilechildren = findViewById(R.id.forprofilechildren);
        profiledescription = findViewById(R.id.forprofiledescription);
        profilewage = findViewById(R.id.forprofilewage);
        profilestatus = findViewById(R.id.forstatus);
        checkbox1 = findViewById(R.id.fsun);
        checkbox2 = findViewById(R.id.fmon);
        checkbox3 = findViewById(R.id.ftue);
        checkbox4 = findViewById(R.id.fwed);
        checkbox5 = findViewById(R.id.fthu);
        checkbox6 = findViewById(R.id.ffri);
        checkbox7 = findViewById(R.id.fsat);
        rate = findViewById(R.id.forrate);
        makeappo = findViewById(R.id.formake);
        check();
        setData();
        availabilitydata();
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DetailsForNanny.this,Rating.class);
                i.putExtra("username",name);
                i.putExtra("status","For Nanny");
                startActivity(i);
            }
        });
        makeappo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsForNanny.this,Make_Appointment.class);
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profile Creation For Nanny");
        Query checkuser = reference.orderByChild("username").equalTo(name);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String Act_Serv;
                    Log.d("name test",name);
                    String dbchildren= (String) dataSnapshot.child(name).child("children").getValue(String.class);
                    String dbwage= (String) dataSnapshot.child(name).child("rate").getValue(String.class);
                    String dblocation=dataSnapshot.child(name).child("location").getValue(String.class);
                    String dbdescription=dataSnapshot.child(name).child("description").getValue(String.class);
                    String serv_status = String.valueOf(dataSnapshot.child(name).child("NeedService").getValue(Integer.class));
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
                    profilechildren.setText("No of Children: "+dbchildren.toString());
                    profiledescription.setText("Description: \n"+dbdescription);
                    profilewage.setText("Expected Wage: $"+dbwage.toString());
                    profilestatus.setText("Service data:"+Act_Serv);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void availabilitydata(){
        DatabaseReference availablereference = FirebaseDatabase.getInstance().getReference().child("Profile Creation For Nanny");
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