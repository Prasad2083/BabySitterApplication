package com.example.finalprojectgroup8;

import android.icu.text.Transliterator;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.finalprojectgroup8.R.id.reviewimage;

public class reviewadapter extends RecyclerView.Adapter<reviewadapter.MyViewHolder> {

    ReviewFragment reviewFragment;
    ArrayList<RatingHelper> ratingHelpers;

    public reviewadapter(ReviewFragment review,ArrayList<RatingHelper> ratingHelpers1){
        this.reviewFragment = review;
        this.ratingHelpers = ratingHelpers1;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rating_list,parent,false);

        return new MyViewHolder(view);
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
