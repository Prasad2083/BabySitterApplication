package com.example.finalprojectgroup8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class Rating extends AppCompatActivity {
    RecyclerView recyclerView;
    Reviewadapter2 adapter;
    ArrayList<RatingHelper> reviewlist;
    FirebaseDatabase rootnode;
    DatabaseReference reference,newreference,subref;
    EditText mFeedback;
    TextView mRatingScale;
    String userdata;
    String rating, review,reviewid,reviewerid,userid, usernamesession;
    int countreviews=0,flag=0,ratingvalue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        final RatingBar mRatingBar = findViewById(R.id.ratingBar);
        mRatingScale = findViewById(R.id.tvRatingScale);
        mFeedback = findViewById(R.id.etFeedback);
        final Button mSendFeedback = findViewById(R.id.btnSubmit);

        userdata = getIntent().getStringExtra("username").toString();
        seedata(userdata);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad : 1");
                        ratingvalue=1;
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement : 2");
                        ratingvalue=2;
                        break;
                    case 3:
                        mRatingScale.setText("Good : 3");
                        ratingvalue=3;
                        break;
                    case 4:
                        mRatingScale.setText("Great :4");
                        ratingvalue=4;
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it : 5");
                        ratingvalue=5;
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });
        final String status = getIntent().getStringExtra("status");
        SharedPreferences preferences = this.getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);
        usernamesession = preferences.getString("username", null);
        review = mFeedback.getText().toString();
        reviewerid = usernamesession;
        userid=userdata;

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    makeText(Rating.this, "Please fill in feedback text box", LENGTH_LONG).show();
                } else{
                    review = mFeedback.getText().toString();
                    reference = FirebaseDatabase.getInstance().getReference("reviews");
                    reference = reference.child(userid);

                    Query rootdata = reference.orderByChild("reviewid");
                    rootdata.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int flag=0;
                            if(dataSnapshot.exists()) {
                                countreviews = (int) dataSnapshot.getChildrenCount();
                                Log.d("exact child *******", String.valueOf(countreviews));
                                flag=1;
                                //countreviews=countreviews-1;
                            }
                            else
                            {
                                countreviews=1;
                            }
                            reviewid="review"+(countreviews);
                            Log.d("Reviews count",countreviews+"   user "+userid);
                            addreview(reviewid);
                            Calculate_Avg cal = new Calculate_Avg(userid,status);
                            Log.d("review",reviewid);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                    makeText(Rating.this, "Thank you for sharing your feedback", LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
    private void  seedata(String usernam){

        reviewlist = new ArrayList<RatingHelper>();
        recyclerView = findViewById(R.id.rating_recy);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Reviewadapter2(Rating.this,reviewlist);

        reference = FirebaseDatabase.getInstance().getReference("reviews").child(usernam);
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

                    }

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Rating.this, "error", LENGTH_SHORT).show();
            }
        });




    }
    private void addreview(String review1id) {
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("reviews");
        ReviewHelper Reviewhelper = new ReviewHelper(review, ratingvalue, reviewerid, userid, review1id);
        Log.d("check id", review1id);
        newreference = reference.child(userid);
        // newreference.removeValue();
        newreference.child("username").setValue(userid);
        subref = newreference.child(review1id);
        subref.setValue(Reviewhelper);
    }
}
