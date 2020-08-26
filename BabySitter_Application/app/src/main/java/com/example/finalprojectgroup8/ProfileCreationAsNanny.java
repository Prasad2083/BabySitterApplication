package com.example.finalprojectgroup8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ProfileCreationAsNanny extends AppCompatActivity {

    EditText editage,editdescrip,editlocation,editfullname,editexperience,editperhour;
    TextView textView;

    String saveage,savedescrip,saveloc,savefname,saveexp,savehour,Saveradio;
    Button save,updatebutton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference2;
    RadioButton forkids,forold,forboth;
    RadioGroup radioGroup;
    SharedPreferences preferences;
    String userfromsession;
    JavaDetailsCreationClass Creationdetails;
    Boolean userbool, flagcheck=false,sunday,monday,tuesday,wednesday,thursday,friday,saturday;
    String showage,showdescp,showloc,showrate,showfname,showexp;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation_as_nanny);
        savedetailsmethod();
        // radiocheck();
        editage = findViewById(R.id.editage);
        editexperience = findViewById(R.id.editexp);
        editperhour = findViewById(R.id.edithour);
        editfullname = findViewById(R.id.editfname);
        editlocation = findViewById(R.id.editloc);
        editdescrip = findViewById(R.id.editdescription);
        save = findViewById(R.id.savebtn);
        forkids=findViewById(R.id.child);
        forold=findViewById(R.id.old);
        forboth=findViewById(R.id.both);
        updatebutton=findViewById(R.id.update);
        textView=findViewById(R.id.textwelcome);
        radioGroup=findViewById(R.id.servicegrp);

        preferences = this.getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);
        userfromsession = preferences.getString("username", null);
        Log.i("check","welcome"+userfromsession);
        userbool=preferences.getBoolean("asNanny",true);

        //textView.setText("Welcome"+" "+userfromsession);


        //updatedetailsmethod();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Profile","asnanny");
                Conditionsforfields();
                if (flagcheck == false);
                if (senddatatodatabase()){
                    Toast.makeText(ProfileCreationAsNanny.this,"Profile Created Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileCreationAsNanny.this, Availability.class);
                    intent.putExtra("monday",monday);
                    intent.putExtra("tuesday",tuesday);
                    intent.putExtra("wednesday",wednesday);
                    intent.putExtra("thursday",thursday);
                    intent.putExtra("friday",friday);
                    intent.putExtra("saturday",saturday);
                    intent.putExtra("sunday",sunday);
                    startActivity(intent);
                }
            }
        });


    }

    public boolean senddatatodatabase(){
        Boolean flagid=false;
        Log.i("Check", "ENTRY LOOP");
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Profile Creation AsNanny");
        Creationdetails = new JavaDetailsCreationClass();
        Creationdetails.setLocation(saveloc);
        Creationdetails.setRate(savehour);
        Creationdetails.setAge(saveage);
        Creationdetails.setExperience(saveexp);
        Creationdetails.setDescription(savedescrip);
        Creationdetails.setFullname(savefname);
        Creationdetails.setUsername(userfromsession);
        reference.child(userfromsession).setValue(Creationdetails);
        reference2=reference.child(userfromsession);
        int checkedid=radioGroup.getCheckedRadioButtonId();
        if (checkedid==-1){
            Toast.makeText(ProfileCreationAsNanny.this,"Null Value Selected",Toast.LENGTH_SHORT).show();
        }
        else {
            flagid=true;
            findserviceoption(checkedid);
        }

        return flagid;

    }

    private void findserviceoption(int checkedid) {
        switch (checkedid){
            case R.id.child:
                status=1;
                break;
            case R.id.old:
                status=2;
                break;
            case R.id.both:
                status=3;
                break;
        }
        reference2.child("ServiceProvide").setValue(status);
    }


    private void Conditionsforfields(){
        flagcheck=false;
        saveage=editage.getText().toString();
        Log.i("Check","agelabel"+saveage);
        savedescrip=editdescrip.getText().toString();
        saveloc=editlocation.getText().toString();
        savefname=editfullname.getText().toString();
        saveexp=editexperience.getText().toString();
        savehour=editperhour.getText().toString();

        if (TextUtils.isEmpty(saveage)){
            editage.setError("Empty Field");
            flagcheck=true;
        }
        if (TextUtils.isEmpty(savedescrip)){
            editdescrip.setError("Empty Field");
            flagcheck=true;
        }
        if (TextUtils.isEmpty(saveloc)){
            editlocation.setError("Empty Field");
            flagcheck=true;
        }
        if (TextUtils.isEmpty(savefname)){
            editfullname.setError("Empty Field");
            flagcheck=true;
        }
        if (TextUtils.isEmpty(saveexp)){
            editexperience.setError("Empty Field");
            flagcheck=true;
        }
        if (TextUtils.isEmpty(savehour)){
            editperhour.setError("Empty Field");
            flagcheck=true;
        }

    }

    public void savedetailsmethod(){
        Intent intent=getIntent();
        Log.i("intent serrvice","display"+intent.getStringExtra("Service"));
        if (intent.getStringExtra("Service") !=null)
        {
            showage=intent.getStringExtra("sendage");
            Log.i("age","Activity"+showage);
            showdescp=intent.getStringExtra("senddescp");
            showexp=intent.getStringExtra("sendexperience");
            showloc=intent.getStringExtra("sendlocation");
            showfname=intent.getStringExtra("sendfname");
            showrate=intent.getStringExtra("sendrate");
            Saveradio=intent.getStringExtra("Service");
            monday = intent.getBooleanExtra("monday",false);
            tuesday = intent.getBooleanExtra("tuesday",false);
            wednesday = intent.getBooleanExtra("wednesday",false);
            thursday = intent.getBooleanExtra("thursday",false);
            friday = intent.getBooleanExtra("friday",false);
            saturday = intent.getBooleanExtra("saturday",false);
            sunday = intent.getBooleanExtra("sunday",false);
            editage = findViewById(R.id.editage);
            editage.setText(showage);
            editexperience = findViewById(R.id.editexp);
            editexperience.setText(showexp);
            editperhour = findViewById(R.id.edithour);
            editperhour.setText(showrate);
            editfullname = findViewById(R.id.editfname);
            editfullname.setText(showfname);
            editlocation = findViewById(R.id.editloc);
            editlocation.setText(showloc);
            editdescrip = findViewById(R.id.editdescription);
            editdescrip.setText(showdescp);
            forkids=findViewById(R.id.child);
            forold=findViewById(R.id.old);
            forboth=findViewById(R.id.both);
            if (Integer.parseInt(Saveradio)==1){
                forkids.setChecked(true);
            }
            if (Integer.parseInt(Saveradio)==2){
                forold.setChecked(true);
            }
            if (Integer.parseInt(Saveradio)==3){
                forboth.setChecked(true);
            }
        }
    }

}