package com.example.finalprojectgroup8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class ReviewFragment extends Fragment {
    DatabaseReference reference;
    RecyclerView recyclerView;
    reviewadapter adapter;
    ArrayList<RatingHelper> reviewlist;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);


        reviewlist = new ArrayList<RatingHelper>();
        recyclerView = view.findViewById(R.id.reviewrecyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new reviewadapter(this,reviewlist);

        reference = FirebaseDatabase.getInstance().getReference("reviews").child(preferences.getString("username",null));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    int countreview = (int) dataSnapshot.getChildrenCount();
                    Log.d("ciount", String.valueOf(countreview));
                    for(int i=1;i<countreview;i++)
                    {
                        String datanode = "review"+i;
                        String desc = dataSnapshot.child(datanode).child("description").getValue(String.class);
                        int rating = Integer.parseInt(String.valueOf(dataSnapshot.child(datanode).child("rating").getValue(Long.class)));
                        String reviwer = dataSnapshot.child(datanode).child("reviewerid").getValue(String.class);
                        RatingHelper rh = new RatingHelper(reviwer,desc,rating);
                        Log.d("class",rh.getRating()+rh.getReviewerid()+rh.getDescription());
                        reviewlist.add(rh);

                     /*   DataSnapshot dataSnapshot1;
                        dataSnapshot1 = (DataSnapshot) dataSnapshot.getChildren();
                        RatingHelper rh = dataSnapshot1.getValue(RatingHelper.class);

                        reviewlist.add(rh);
                        recyclerView.setAdapter(adapter);*/
                    }

                    recyclerView.setAdapter(adapter);
                }/*for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        RatingHelper rh = dataSnapshot1.getValue(RatingHelper.class);
                        reviewlist.add(rh);
                    }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }



}
