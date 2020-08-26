package com.example.finalprojectgroup8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */

public class My_Appointments_Fragment extends Fragment {
    DatabaseReference reference;
    RecyclerView recyclerView;
    Appointments_Adapter myadapter;
    ArrayList<My_Appointments_Helper> list;

    public My_Appointments_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_my__appointments_, container, false);
        list = new ArrayList<My_Appointments_Helper>();
        recyclerView = v.findViewById(R.id.myappo_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myadapter = new Appointments_Adapter(this,list);
        SharedPreferences preferences =this.getActivity().getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);
        String usernamesession=preferences.getString("username",null);
        reference = FirebaseDatabase.getInstance().getReference("appointments");
        final Query data_query = reference.child(usernamesession);
        data_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String appoid, appodate, appotime;
                    appoid = dataSnapshot1.child("appointerid").getValue(String.class);
                    appodate = dataSnapshot1.child("time").getValue(String.class);
                    appotime = dataSnapshot1.child("date").getValue(String.class);
                    My_Appointments_Helper myappoint = new My_Appointments_Helper(appoid,appodate,appotime);
                    list.add(myappoint);
                }
                recyclerView.setAdapter(myadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
}
