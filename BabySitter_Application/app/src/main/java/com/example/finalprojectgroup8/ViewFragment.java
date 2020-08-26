package com.example.finalprojectgroup8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {

    SharedPreferences preferences;
    String usernamefromsession,userdata;
    DatabaseReference reference,reference2;
    Boolean userbool,sunday,monday,tuesday,wednesday,thursday,friday,saturday;
    String Storeage,Storedescription,Storeexp,Storeloc,Storefulln,Storerate,Storeservice,Storeneedservice,
            Storechild,Storefornannydescp,Storefornannyloc,Storefornannyfname,Storefornannyrate;
    Button udate;
    View v;
    TextView textView1,textView2,textView3,textView4,textView5,textView6,
            textfornanny1,textfornanny2,textfornanny3,textfornanny4,
            textfornanny5,welcometext;

    public ViewFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences =this.getActivity().getSharedPreferences("com.example.finalprojectgroup8",Context.MODE_PRIVATE);
        usernamefromsession=preferences.getString("username",null);
        userbool = preferences.getBoolean("asNanny",true);

        //Inflate the layout for this fragment
        if (userbool) {
            v = inflater.inflate(R.layout.fragment_view, container, false);
            Log.i("view1", "nanny");
            fragmentdetailsasnanny();

        }
        else {
            v=inflater.inflate(R.layout.fragment_fornanny, container, false);
            Log.i("view2", "nanny");
            fragmentdetailsfornanny();
        }

        return  v;
    }

    public void fragmentdetailsasnanny(){
        setdetailsonfragmentview();

        textView1=v.findViewById(R.id.editfname);
        textView2=v.findViewById(R.id.editloc);
        textView3=v.findViewById(R.id.editdescription);
        textView4=v.findViewById(R.id.editage);
        textView5=v.findViewById(R.id.editexp);
        textView6=v.findViewById(R.id.edithour);
        welcometext=v.findViewById(R.id.datawelcome);
        udate = v.findViewById(R.id.update);
        udate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userbool==true){

                    CountDownTimer done = new CountDownTimer(2000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            reference= FirebaseDatabase.getInstance().
                                    getReference("Profile Creation AsNanny").child(usernamefromsession);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Storeage=dataSnapshot.child("age").getValue(String.class);
                                    Storedescription=dataSnapshot.child("description").getValue(String.class);
                                    Storeexp=dataSnapshot.child("experience").getValue(String.class);
                                    Storefulln=dataSnapshot.child("fullname").getValue(String.class);
                                    Storeloc=dataSnapshot.child("location").getValue(String.class);
                                    Storerate=dataSnapshot.child("rate").getValue(String.class);
                                    Storeservice=String.valueOf(dataSnapshot.child("ServiceProvide").getValue(Integer.class));
                                    monday=dataSnapshot.child("availability").child("monday").getValue(Boolean.class);
                                    tuesday=dataSnapshot.child("availability").child("tuesday").getValue(Boolean.class);
                                    wednesday=dataSnapshot.child("availability").child("wednesday").getValue(Boolean.class);
                                    thursday=dataSnapshot.child("availability").child("thursday").getValue(Boolean.class);
                                    friday=dataSnapshot.child("availability").child("friday").getValue(Boolean.class);
                                    saturday=dataSnapshot.child("availability").child("saturday").getValue(Boolean.class);
                                    sunday=dataSnapshot.child("availability").child("sunday").getValue(Boolean.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onFinish() {
                            Intent intent=new Intent(getActivity(),ProfileCreationAsNanny.class);
                            intent.putExtra("sendage",Storeage);
                            intent.putExtra("senddescp",Storedescription);
                            intent.putExtra("sendlocation",Storeloc);
                            intent.putExtra("sendfname",Storefulln);
                            intent.putExtra("sendrate",Storerate);
                            intent.putExtra("sendexperience",Storeexp);
                            intent.putExtra("Service",Storeservice);
                            intent.putExtra("monday",monday);
                            intent.putExtra("tuesday",tuesday);
                            intent.putExtra("wednesday",wednesday);
                            intent.putExtra("thursday",thursday);
                            intent.putExtra("friday",friday);
                            intent.putExtra("saturday",saturday);
                            intent.putExtra("sunday",sunday);
                            startActivity(intent);
                        }
                    }.start();
                }

            }
        });

    }
    public void fragmentdetailsfornanny(){
        setdetailsonfornannyfragmnet();

        textfornanny1=v.findViewById(R.id.editfname);
        textfornanny2=v.findViewById(R.id.editloc);
        textfornanny3=v.findViewById(R.id.editdescription);
        textfornanny4=v.findViewById(R.id.editkids);
        textfornanny5=v.findViewById(R.id.edithour);
        welcometext=v.findViewById(R.id.datawelcome);
        udate = v.findViewById(R.id.update);
        udate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userbool==false) {
                    Log.i("test update for nanny", "result");
                    CountDownTimer done = new CountDownTimer(2000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            reference2 = FirebaseDatabase.getInstance().
                                    getReference("Profile Creation For Nanny").child(usernamefromsession);
                            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Storechild = dataSnapshot.child("children").getValue(String.class);
                                    Storefornannydescp = dataSnapshot.child("description").getValue(String.class);
                                    Storefornannyrate = dataSnapshot.child("rate").getValue(String.class);
                                    Storefornannyfname = dataSnapshot.child("fullname").getValue(String.class);
                                    Storefornannyloc = dataSnapshot.child("location").getValue(String.class);
                                    Storeneedservice=String.valueOf(dataSnapshot.child("NeedService").getValue(Integer.class));
                                    monday=dataSnapshot.child("availability").child("monday").getValue(Boolean.class);
                                    tuesday=dataSnapshot.child("availability").child("tuesday").getValue(Boolean.class);
                                    wednesday=dataSnapshot.child("availability").child("wednesday").getValue(Boolean.class);
                                    thursday=dataSnapshot.child("availability").child("thursday").getValue(Boolean.class);
                                    friday=dataSnapshot.child("availability").child("friday").getValue(Boolean.class);
                                    saturday=dataSnapshot.child("availability").child("saturday").getValue(Boolean.class);
                                    sunday=dataSnapshot.child("availability").child("sunday").getValue(Boolean.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onFinish() {
                            Intent intent2 = new Intent(getActivity(), ProfileCreationForNanny.class);
                            intent2.putExtra("sendchild", Storechild);
                            intent2.putExtra("sendfnannydescp", Storefornannydescp);
                            intent2.putExtra("sendfnannylocation", Storefornannyloc);
                            intent2.putExtra("sendfnannyname", Storefornannyfname);
                            intent2.putExtra("sendfrate", Storefornannyrate);
                            intent2.putExtra("sendservice",Storeneedservice);
                            intent2.putExtra("monday",monday);
                            intent2.putExtra("tuesday",tuesday);
                            intent2.putExtra("wednesday",wednesday);
                            intent2.putExtra("thursday",thursday);
                            intent2.putExtra("friday",friday);
                            intent2.putExtra("saturday",saturday);
                            intent2.putExtra("sunday",sunday);
                            startActivity(intent2);
                        }
                    }.start();

                }
            }
        });


    }

    public void setdetailsonfragmentview(){
        CountDownTimer done = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                reference= FirebaseDatabase.getInstance().
                        getReference("Profile Creation AsNanny").child(usernamefromsession);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Storeage=dataSnapshot.child("age").getValue(String.class);
                        Storedescription=dataSnapshot.child("description").getValue(String.class);
                        Storeexp=dataSnapshot.child("experience").getValue(String.class);
                        Storefulln=dataSnapshot.child("fullname").getValue(String.class);
                        Storeloc=dataSnapshot.child("location").getValue(String.class);
                        Storerate=dataSnapshot.child("rate").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onFinish() {
                textView1.setText(Storefulln);
                textView2.setText(Storeloc);
                textView3.setText(Storedescription);
                textView4.setText(Storeage);
                textView5.setText(Storeexp);
                textView6.setText(Storerate);
                welcometext.setText(usernamefromsession);
            }
        }.start();

    }
    public void setdetailsonfornannyfragmnet(){
        CountDownTimer done = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                reference2= FirebaseDatabase.getInstance().
                        getReference("Profile Creation For Nanny").child(usernamefromsession);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Storechild=dataSnapshot.child("children").getValue(String.class);
                        Storefornannydescp=dataSnapshot.child("description").getValue(String.class);
                        Storefornannyrate=dataSnapshot.child("rate").getValue(String.class);
                        Storefornannyfname=dataSnapshot.child("fullname").getValue(String.class);
                        Storefornannyloc=dataSnapshot.child("location").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onFinish() {
                textfornanny1.setText(Storefornannyfname);
                textfornanny2.setText(Storefornannyloc);
                textfornanny3.setText(Storedescription);
                textfornanny4.setText(Storechild);
                textfornanny5.setText(Storefornannyrate);
                welcometext.setText(usernamefromsession);
            }
        }.start();

    }


}
