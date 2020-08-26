package com.example.finalprojectgroup8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Reviewadapter2 extends RecyclerView.Adapter<Reviewadapter2.MyViewHolder> {

    Rating rat;
    ArrayList<RatingHelper> ratingHelpers;

    public Reviewadapter2(Rating rating, ArrayList<RatingHelper> reviewlist) {
        this.rat=rating;
        this.ratingHelpers=reviewlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rating_list,parent,false);

        return new Reviewadapter2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pic=R.drawable.logo;
        holder.name.setText(ratingHelpers.get(position).getReviewerid());
        holder.review.setText(ratingHelpers.get(position).getDescription());
        holder.rating.setText(String.valueOf(ratingHelpers.get(position).getRating() ));
        holder.revImage.setImageResource(pic);

    }

    @Override
    public int getItemCount() {
        return ratingHelpers.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,review,rating;
        ImageView revImage;
        public MyViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.reviewerid);
            review = itemView.findViewById(R.id.reviewcontent);
            rating = itemView.findViewById(R.id.starrate);
            revImage = itemView.findViewById(R.id.reviewimage);
        }
    }
}
