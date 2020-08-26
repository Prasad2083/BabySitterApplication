package com.example.finalprojectgroup8;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Calculate_Avg {
    DatabaseReference reference,sendreference;
    int countreviews;

    public Calculate_Avg(final String userid, final String status){
        reference = FirebaseDatabase.getInstance().getReference("reviews").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    int countreview = (int) dataSnapshot.getChildrenCount();

                    int itr=0;
                    long average;
                    for(int i=1;i<countreview;i++)
                    {
                        String datanode = "review"+i;
                        int rating = Integer.parseInt(String.valueOf(dataSnapshot.child(datanode).child("rating").getValue(Long.class)));
                        itr=itr+rating;
                    }
                    countreview--;
                    average = itr/countreview;
                    Log.d("average", String.valueOf(countreview)+"   : "+average+"  : "+itr);
                    sendreference = FirebaseDatabase.getInstance().getReference("Average").child(status).child(userid);
                    sendreference.child("avg_value").setValue(average);
                    sendreference.child("username").setValue(userid);
                    //reference.child("zaverage").setValue(average);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
