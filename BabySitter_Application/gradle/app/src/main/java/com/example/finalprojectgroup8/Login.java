package com.example.finalprojectgroup8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

        int flag=0;
        final String userenteredname=email.getEditableText().toString();
        final String userenteredpass=password.getEditableText().toString();

        if (TextUtils.isEmpty(userenteredname)){
            email.setError("Enter UserName");
        }
        if (TextUtils.isEmpty(userenteredpass)){
            password.setError("Enter Password");
        }

        if(flag == 0)
        {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Registr As Nanny");
            Query checkuser=reference.orderByChild("username").equalTo(userenteredname);
            System.out.println("Print to console"+ checkuser);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String passwordfromdb = dataSnapshot.child(userenteredname).child("password").getValue(String.class);
                        if (passwordfromdb.equals(userenteredpass)) {
                            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            password.setError("Incorrect password");
                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        flag =1;
        }

        if(flag ==0)
        {
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Registr For Nanny");

            Query checkuser2 = reference2.orderByChild("username").equalTo(userenteredname);
            System.out.println("Print to console ref2" + checkuser2);

            checkuser2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String passwordfromdb = dataSnapshot.child(userenteredname).child("password").getValue(String.class);
                        if (passwordfromdb.equals(userenteredpass)) {

                            Intent intent = new Intent(getApplicationContext(), HomeForNanny.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            password.setError("Incorrect password");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            flag=1;
        }

        if(flag==0)
        {
            email.setError("No Such User");
        }
    }

}
