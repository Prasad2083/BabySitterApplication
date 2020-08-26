package com.example.finalprojectgroup8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothA2dp;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class Register extends AppCompatActivity {
    EditText signupname,signupphone,signupemail,signuppass;
    Button button,button1;
    FirebaseDatabase rootnode,rootnode2;
    DatabaseReference reference,reference2;
    Spinner spinner;
    int selpos;
    boolean flag=false,fieldcheck=false,usercheck=true;
    boolean flag2 = false;
    String textselect,testemail,username,testphone,testpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        List<String> Regdata = new ArrayList<>();
        Regdata.add(0,"Registr As Nanny");
        Regdata.add(1,"Registr For Nanny");

        signupemail=findViewById(R.id.email);
        signupname=findViewById(R.id.name);
        signupphone=findViewById(R.id.phone);
        signuppass=findViewById(R.id.pass);
        button=findViewById(R.id.signup);
        button1= (Button) findViewById(R.id.asignup);
        spinner= (Spinner) findViewById(R.id.regspin);


        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item,Regdata);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textselect=parent.getItemAtPosition(position).toString();
                selpos = position;
                Toast.makeText(Register.this,"Selected"+textselect,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrationconditions();
                if(fieldcheck==false)
                    CheckforDuplicateValues(username,selpos);

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),Login.class);
                startActivity(intent1);
            }
        });
    }
    private void RigisterasNanny() {
        rootnode=FirebaseDatabase.getInstance();
        reference=rootnode.getReference("Registr As Nanny");
        JavaHelperClass javaHelperClass = new JavaHelperClass(username, testemail, testphone, testpass);
        reference.child(username).setValue(javaHelperClass);
        Intent intent=new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }

    private void Rigisterfornanny() {
        rootnode=FirebaseDatabase.getInstance();
        reference=rootnode.getReference("Registr For Nanny");
        JavaHelperClass javaHelperClass = new JavaHelperClass(username, testemail, testphone, testpass);
        reference.child(username).setValue(javaHelperClass);
        Intent intent=new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }

    private void CheckforDuplicateValues(String username,int selpos){

        if(selpos==0)
        {
            reference = rootnode.getInstance().getReference("Registr As Nanny");
            Query checkuser = reference.orderByChild("username").equalTo(username);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        signupname.setError("Username already exits");
                        Toast.makeText(Register.this, "Username already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        RigisterasNanny();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            reference2 = rootnode2.getInstance().getReference("Registr For Nanny");
            Query checkuser2 = reference2.orderByChild("username").equalTo(username);
            checkuser2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        signupname.setError("Username already exits");
                        Toast.makeText(Register.this, "Username already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Rigisterfornanny();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    private void Registrationconditions(){

        testemail=signupemail.getText().toString();
        username=signupname.getText().toString();
        testphone=signupphone.getText().toString();
        testpass=signuppass.getText().toString();

        if (TextUtils.isEmpty(testemail)){
            signupemail.setError("Email field is empty");
            fieldcheck=true;
        }
        if (TextUtils.isEmpty(username)){
            signupname.setError("Field is empty");
            fieldcheck=true;
        }
        if (TextUtils.isEmpty(testphone)){
            signupphone.setError("Field is empty");
            fieldcheck=true;
        }
        if (TextUtils.isEmpty(testpass)){
            signuppass.setError("Field is Empty");
            fieldcheck=true;
        }

    }

}
