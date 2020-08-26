package com.example.finalprojectgroup8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Appointments_Adapter extends RecyclerView.Adapter<Appointments_Adapter.MyViewHolder> {

    Fragment myap;
    ArrayList<My_Appointments_Helper> list_appo;
    DatabaseReference reference,reference2;

    public Appointments_Adapter(My_Appointments_Fragment my_appointments_fragment, ArrayList<My_Appointments_Helper> list) {
        this.myap = my_appointments_fragment;
        this.list_appo = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.myapp_recycleview,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        int pic=R.drawable.logo;
        int pic2=R.drawable.call;
        holder.myText1.setText(list_appo.get(position).getAppointerid());
        holder.myText2.setText(list_appo.get(position).getDate());
        holder.myText3.setText(list_appo.get(position).getTime());
        holder.myImage1.setImageResource(pic);
        holder.myImage2.setImageResource(pic2);
        holder.myImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context context = v.getContext();
                FindNumber(position);
            }
        });


    }

    private void FindNumber(int position) {
        String data_node_test;
        final String contact1;
        SharedPreferences preferences =myap.getActivity().getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);
        if(preferences.getBoolean("asNanny",true)){

            data_node_test= "Register For Nanny";
        }else{

            data_node_test= "Register As Nanny";
        }

        reference = FirebaseDatabase.getInstance().getReference(data_node_test);
        final String Appoiid=list_appo.get(position).getAppointerid();

        Query dquery = reference.orderByChild("username").equalTo(Appoiid);
       // Log.d("id",dquery.toString());
        dquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String contact = dataSnapshot.child(Appoiid).child("phone").getValue(String.class);
                Log.d("in method",contact);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+contact));
                myap.startActivity(callIntent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list_appo.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1,myText2,myText3;
        ImageView myImage1,myImage2;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.app_rec_id);
            myText2 = itemView.findViewById(R.id.app_rec_date);
            myText3 = itemView.findViewById(R.id.app_rec_time);
            myImage1 = itemView.findViewById(R.id.app_rec_img);
            myImage2 = itemView.findViewById(R.id.app_rec_call);
            mainLayout = itemView.findViewById(R.id.mainLayout);        }
    }
}
