package com.example.finalprojectgroup8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class BnannyFragment extends Fragment {
    DatabaseReference reference, reference2;

    public BnannyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_bnanny, container, false);

        reference = FirebaseDatabase.getInstance().getReference("Average").child("As Nanny");
        final Query dataquery = reference.orderByChild("avg_value").limitToLast(1);
        dataquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String key = null;
                    String maxvalue = null;
                    for (DataSnapshot childdata : dataSnapshot.getChildren()) {
                        key = childdata.getKey();
                        maxvalue = String.valueOf(dataSnapshot.child(key).child("avg_value").getValue(Long.class));
                        max_list(maxvalue, v);
                        // Toast.makeText(getActivity(), "max user : " + key + " with average :" + maxvalue, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }


    private void max_list(final String maxvalue, View view) {
        final TextView datalist, rate;
        datalist = view.findViewById(R.id.bnanny_list);
        rate = view.findViewById(R.id.bnanny_best);
        reference = FirebaseDatabase.getInstance().getReference("Average").child("As Nanny");
        reference.orderByChild("avg_value").
                equalTo(Long.parseLong(maxvalue)).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int count = 0;
                            String user = "";
                            for (DataSnapshot childdata : dataSnapshot.getChildren()) {
                                count++;
                                user = user + "\n" + childdata.getKey();
                            }
                            datalist.setText(user);
                            rate.setText("Best Nannies ");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
