package com.example.finalprojectgroup8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    EditText email,password;
    Button loginbtn,regisbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }*/

        setContentView(R.layout.activity_login);
        email=findViewById(R.id.loginemail);
        password=findViewById(R.id.loginpass);
        loginbtn=findViewById(R.id.loginbtn);
        regisbtn = findViewById(R.id.signbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });
        regisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }


    private void isUser() {

        final String userenteredname = email.getEditableText().toString();
        final String userenteredpass = password.getEditableText().toString();

        if (TextUtils.isEmpty(userenteredname)) {
            email.setError("Enter UserName");
        }
        if (TextUtils.isEmpty(userenteredpass)) {
            password.setError("Enter Password");
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Register As Nanny");
        Query checkuser = reference.orderByChild("username").equalTo(userenteredname);
        System.out.println("Print to console" + checkuser+ " : "+userenteredname);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String passwordfromdb = dataSnapshot.child(userenteredname).child("password").getValue(String.class);
                    if (passwordfromdb.equals(userenteredpass)) {

                        String email = dataSnapshot.child(userenteredname).child("email").getValue(String.class);
                        setSession(userenteredname,true,email);
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        password.setError("Incorrect password");
                    }
                } else {
                    validateinRegisterfornanny(userenteredname,userenteredpass);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void validateinRegisterfornanny(String userenteredURFA,String userenteredPRFA){

        final String userentrednameRFA=userenteredURFA;
        final String userentredpassRFA=userenteredPRFA;


        DatabaseReference referenceRFA = FirebaseDatabase.getInstance().getReference("Register For Nanny");

        Query checkuser2 = referenceRFA.orderByChild("username").equalTo(userentrednameRFA);
        System.out.println("Print to console ref2" + checkuser2);

        checkuser2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String passwordfromdb = dataSnapshot.child(userentrednameRFA).child("password").getValue(String.class);
                    if (passwordfromdb.equals(userentredpassRFA)) {
                        String email = dataSnapshot.child(userentrednameRFA).child("email").getValue(String.class);
                        setSession(userentrednameRFA,false,email);
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        password.setError("Incorrect password");
                    }
                }
                else
                {
                    email.setError("No Such User");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setSession(String username, Boolean asNanny,String email){
        SharedPreferences preferences =
                getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("asNanny", asNanny); // Storing asNanny boolean - true/false
        editor.putString("username", username); // Storing username
        editor.putString("email", email);
        editor.commit(); // commit changes

    }


}