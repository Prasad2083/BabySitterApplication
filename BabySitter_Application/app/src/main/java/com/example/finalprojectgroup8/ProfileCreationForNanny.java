package com.example.finalprojectgroup8;

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

public class ProfileCreationForNanny extends AppCompatActivity {
    EditText editdescrip,editlocation,editfullname,editperhour,editchildren;
    String savedescrip,saveloc,savefname,savehour,savechild,saveradioservice;
    TextView textView;
    Button save,updatebutton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference2;
    SharedPreferences preferences;
    String userfromsession;
    JavaDetailsCreationClass Creationdetails;
    Boolean flagcheck=false,sunday,monday,tuesday,wednesday,thursday,friday,saturday;
    RadioGroup radioGroup;
    RadioButton forkids,forold,forboth;
    String showdescp,showloc,showfname,showrate,showchild;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation_for_nanny);
        savedetailsmethod();
        editperhour = findViewById(R.id.edithour);
        editfullname = findViewById(R.id.editfname);
        editlocation = findViewById(R.id.editloc);
        editdescrip = findViewById(R.id.editdescription);
        editchildren= findViewById(R.id.editkids);
        updatebutton= findViewById(R.id.update);
        save = findViewById(R.id.savebtn);
        forkids=findViewById(R.id.child);
        forold=findViewById(R.id.old);
        forboth=findViewById(R.id.both);
        textView=findViewById(R.id.textwelcome);
        radioGroup=findViewById(R.id.servicegrp);
        preferences = this.getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);
        userfromsession = preferences.getString("username", null);

        //textView.setText("Welcome"+" "+userfromsession);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conditionsforfields();
                if (flagcheck == false);
                if (senddatatodatabase()) {
                    Toast.makeText(ProfileCreationForNanny.this, "Profile Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileCreationForNanny.this, Availability.class);
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
        boolean flagid=false;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Profile Creation For Nanny");
        Creationdetails = new JavaDetailsCreationClass();
        Creationdetails.setLocation(saveloc);
        Creationdetails.setRate(savehour);
        Creationdetails.setDescription(savedescrip);
        Creationdetails.setFullname(savefname);
        Creationdetails.setChildren(savechild);
        Creationdetails.setUsername(userfromsession);
        reference.child(userfromsession).setValue(Creationdetails);
        reference2=reference.child(userfromsession);

        int checkedid=radioGroup.getCheckedRadioButtonId();
        if (checkedid==-1){

            Toast.makeText(ProfileCreationForNanny.this,"Null Value Selected",Toast.LENGTH_SHORT).show();
        }
        else {
            flagid=true;
            findneedserviceoption(checkedid);
        }

        return flagid;
    }

    private void findneedserviceoption(int checkedid) {

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
        reference2.child("NeedService").setValue(status);
    }


    public void Conditionsforfields(){

        flagcheck=false;
        savedescrip=editdescrip.getText().toString();
        saveloc=editlocation.getText().toString();
        savefname=editfullname.getText().toString();
        savehour=editperhour.getText().toString();
        savechild=editchildren.getText().toString();

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
        if (TextUtils.isEmpty(savehour)){
            editperhour.setError("Empty Field");
            flagcheck=true;
        }
        if (TextUtils.isEmpty(savechild)){
            editchildren.setError("Empty Field");
            flagcheck=true;
        }

    }

    public void savedetailsmethod() {
        Intent intent = getIntent();
        if (intent.getStringExtra("sendservice") !=null) {
            showdescp = intent.getStringExtra("sendfnannydescp");
            showloc = intent.getStringExtra("sendfnannylocation");
            showfname = intent.getStringExtra("sendfnannyname");
            showrate = intent.getStringExtra("sendfrate");
            showchild = intent.getStringExtra("sendchild");
            saveradioservice = intent.getStringExtra("sendservice").toString();
            monday = intent.getBooleanExtra("monday", false);
            tuesday = intent.getBooleanExtra("tuesday", false);
            wednesday = intent.getBooleanExtra("wednesday", false);
            thursday = intent.getBooleanExtra("thursday", false);
            friday = intent.getBooleanExtra("friday", false);
            saturday = intent.getBooleanExtra("saturday", false);
            sunday = intent.getBooleanExtra("sunday", false);
            editperhour = findViewById(R.id.edithour);
            editperhour.setText(showrate);
            editfullname = findViewById(R.id.editfname);
            editfullname.setText(showfname);
            editlocation = findViewById(R.id.editloc);
            editlocation.setText(showloc);
            editdescrip = findViewById(R.id.editdescription);
            editdescrip.setText(showdescp);
            editchildren = findViewById(R.id.editkids);
            editchildren.setText(showchild);
            forkids = findViewById(R.id.child);
            forold = findViewById(R.id.old);
            forboth = findViewById(R.id.both);
            if (Integer.parseInt(saveradioservice) == 1) {
                forkids.setChecked(true);
            }
            if (Integer.parseInt(saveradioservice) == 2) {
                forold.setChecked(true);
            }
            if (Integer.parseInt(saveradioservice) == 3) {
                forboth.setChecked(true);
            }
        }
    }
}