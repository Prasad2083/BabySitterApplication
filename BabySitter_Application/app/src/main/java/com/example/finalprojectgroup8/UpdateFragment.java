package com.example.finalprojectgroup8;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {

  private     EditText pass,phone,mail;
  private    Button updatebutton;

  String storepass,storemail,storephone;

  boolean fieldcheck=false;

    SharedPreferences preferences;
    String usernamesession;

    public UpdateFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_update, container, false);

        pass=v.findViewById(R.id.editPassword);
        phone=v.findViewById(R.id.editphone);
        mail=v.findViewById(R.id.editemail);
       updatebutton=v.findViewById(R.id.updatebutton);




         preferences = this.getActivity().getSharedPreferences("com.example.finalprojectgroup8",Context.MODE_PRIVATE);


              usernamesession=preferences.getString("username",null);
              Log.i("username","session"+usernamesession);

        // Inflate the layout for this fragment

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fieldconditionsforupdate();

                Log.i("check","key" + preferences.getBoolean("Nannykey",true));
                if (preferences.getBoolean("Nannykey",true)) {

                    if (fieldcheck==false) {

                        UpdateuserdetailsinRegisterasnanny();

                        Toast.makeText(UpdateFragment.this.getActivity(), "Your User details Updated", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    if (fieldcheck==false) {

                        UpdateuserdetailsinRegisterforNanny();

                        Toast.makeText(UpdateFragment.this.getActivity(), "Your User details Updated", Toast.LENGTH_SHORT).show();
                    }
               }

            }
        });



        return v;
    }



private void UpdateuserdetailsinRegisterasnanny(){
        Log.i("check","AsNanny");
        final String storepass= pass.getText().toString();
        final String storemail= mail.getText().toString();
        final String storephone= phone.getText().toString();
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Registr As Nanny");
         Query check= reference.orderByChild("username").equalTo(usernamesession);
         check.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists()){
                     Log.i("data","snapshot" + usernamesession);
                     String numberdatabase= dataSnapshot.child(usernamesession).child("phone").getValue(String.class);
                     String passfromdatabase=dataSnapshot.child(usernamesession).child("password").getValue(String.class);
                     String mailfromdatabase=dataSnapshot.child(usernamesession).child("email").getValue(String.class);
                     if(!numberdatabase.equals(storephone)){

                         reference.child(usernamesession).child("phone").setValue(storephone);
                     }
                     else {
                         phone.setError("No user name is updated");
                     }
                     if (!passfromdatabase.equals(storepass)){

                         reference.child(usernamesession).child("password").setValue(storepass);
                     }
                     else {
                         pass.setError("No password is updated");
                     }

                        if (!mailfromdatabase.equals(storemail)){
                            reference.child(usernamesession).child("email").setValue(storemail);
                        }
                        else {
                            mail.setError("No Email is updated");
                        }
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
}
public void UpdateuserdetailsinRegisterforNanny(){

        Log.i("check","ForNanny");

// Getting details from user on update page

    /*final String storenumber= phone.getText().toString();
    final String storepass= pass.getText().toString();
    final String storemail= mail.getText().toString();*/

    final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Registr For Nanny");
    Query check= reference.orderByChild("username").equalTo(usernamesession);
    check.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                Log.i("data","snapshotfor"+usernamesession);

                // Getting userdetails from database and storing in strings

                String numberdatabase2= dataSnapshot.child(usernamesession).child("username").getValue(String.class);
                String passfromdatabase2=dataSnapshot.child(usernamesession).child("password").getValue(String.class);
                String mailfromdatabase2=dataSnapshot.child(usernamesession).child("email").getValue(String.class);
                if(!numberdatabase2.equals(storephone)){

                    reference.child(usernamesession).child("phone").setValue(storephone);

                }
                else {
                    phone.setError("No user name is updated");
                }
                if (!passfromdatabase2.equals(storepass)){

                    reference.child(usernamesession).child("password").setValue(storepass);
                }
                else {
                    pass.setError("No password is updated");
                }

                if(!mailfromdatabase2.equals(storemail)){
                    reference.child(usernamesession).child("email").setValue(storemail);
                }
                else {
                    mail.setError("No Email is updated");
                }

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}



public void Fieldconditionsforupdate(){

    Log.i("data","fieldcheck1"+fieldcheck);

        fieldcheck=false;

        storepass= pass.getText().toString();
        storemail= mail.getText().toString();
        storephone= phone.getText().toString();

        if (TextUtils.isEmpty(storepass)){
            pass.setError("Field is Empty");
            fieldcheck=true;
        }
        if (TextUtils.isEmpty(storemail)){
            mail.setError("Enter Mail id");
            fieldcheck=true;
        }
        if (TextUtils.isEmpty(storephone)){
            phone.setError("Field is Empty");
            fieldcheck=true;
        }
    Log.i("data","fieldcheck2"+fieldcheck);
}

}
