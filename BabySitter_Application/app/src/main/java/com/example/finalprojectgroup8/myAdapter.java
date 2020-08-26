package com.example.finalprojectgroup8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {


    Fragment homeFragment;
    ArrayList<RecyclerViewList> recyclerViewLists;


    public myAdapter(Fragment home, ArrayList<RecyclerViewList> profiles){
        this.homeFragment = home;
        this.recyclerViewLists = profiles;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.my_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        int pic=R.drawable.logo;
        holder.myText1.setText(recyclerViewLists.get(position).getUsername());
        holder.myText2.setText(recyclerViewLists.get(position).getLocation());
        holder.myText3.setText("$ "+recyclerViewLists.get(position).getRate());
        holder.myText4.setText(recyclerViewLists.get(position).getService());
        holder.myImage.setImageResource(pic);

        SharedPreferences preferences = this.homeFragment.getActivity().getSharedPreferences("com.example.finalprojectgroup8",Context.MODE_PRIVATE);

        final Boolean userbool = preferences.getBoolean("asNanny",true);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userbool == false)
                {
                    Intent i = new Intent(homeFragment.getContext(),DetailsActivity.class);
                    i.putExtra("viewname", recyclerViewLists.get(position).getUsername());
                    (homeFragment).startActivity(i);
                }
                else
                {
                    Intent i = new Intent(homeFragment.getContext(),DetailsForNanny.class);
                    i.putExtra("viewname", recyclerViewLists.get(position).getUsername());
                    (homeFragment).startActivity(i);
                }
            }


        });



    }


    @Override
    public int getItemCount() {
        return recyclerViewLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1,myText2,myText3,myText4;
        ImageView myImage;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.username);
            myText2 = itemView.findViewById(R.id.mailid);
            myText3 = itemView.findViewById(R.id.recy_rate);
            myText4 = itemView.findViewById(R.id.recy_status);
            myImage = itemView.findViewById(R.id.profileImage);
            mainLayout = itemView.findViewById(R.id.mainLayout);        }
    }
    public void filterationlist(ArrayList<RecyclerViewList> filteratedlist)
    {
        recyclerViewLists = filteratedlist;
        notifyDataSetChanged();
    }
}
