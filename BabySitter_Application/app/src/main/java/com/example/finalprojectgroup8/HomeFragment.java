package com.example.finalprojectgroup8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    DatabaseReference reference;
    RecyclerView recyclerView;
    myAdapter myadapter;
    ArrayList<RecyclerViewList> list,searchlist,speclist;
    EditText searcho;
    Button flist;
    int hprice,hstatus;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences preferences =this.getActivity().getSharedPreferences("com.example.finalprojectgroup8", Context.MODE_PRIVATE);

        searcho = view.findViewById(R.id.searchtext);
        searcho.setHint("Search By Location");
        searcho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteration(s.toString());
            }
        });
        flist = view.findViewById(R.id.listoffilter);
        flist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpromptasnanny(view);
            }
        });
        if(preferences.getBoolean("asNanny",true)){
            //get the list forNanny users
            getUsersList(view, "Profile Creation For Nanny","NeedService");
        }else{
            //get the list asNanny users
            getUsersList(view, "Profile Creation AsNanny","ServiceProvide");
        }

        return view;
    }
    private  void  filterationspec()
    {
        String Act_Serv;
        speclist = new ArrayList<RecyclerViewList>();
        if(hstatus==1)
            Act_Serv="Only Children";
        else if(hstatus==2)
            Act_Serv="Only Oldsters";
        else if(hstatus==3)
            Act_Serv="Both";
        else
            Act_Serv="Not available";
        for(RecyclerViewList item : list){
            if((Integer.parseInt(item.getRate()))>=hprice && item.getService().equals(Act_Serv)){
                Log.d("filter", String.valueOf(item.getRate())+"    "+String.valueOf(item.getService()));
                speclist.add(item);
           }

            //Log.d("filter", hprice+"    "+hstatus);
        }
        myadapter.filterationlist(speclist);
    }
    private void filteration(String data){
        searchlist = new ArrayList<RecyclerViewList>();
        for(RecyclerViewList item : list){
            if (item.getLocation().toLowerCase().contains(data.toLowerCase()))
            {
                searchlist.add(item);
            }

        }
        myadapter.filterationlist(searchlist);
    }
    public void openpromptasnanny(View view)
    {
        Promptclass promptclass = new Promptclass(this);
        promptclass.show(getFragmentManager(),"example prompt");
    }
    public void getUsersList(View view, String childParam, final String status_check){
        list = new ArrayList<RecyclerViewList>();

        recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myadapter = new myAdapter(this, list);
        reference = FirebaseDatabase.getInstance().getReference().child(childParam);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name,place,price;
                    String serv_status;
                    String Act_Serv;
                    name = dataSnapshot1.child("username").getValue(String.class);
                    place = dataSnapshot1.child("location").getValue(String.class);
                    price = dataSnapshot1.child("rate").getValue(String.class);
                    serv_status = String.valueOf(dataSnapshot1.child(status_check).getValue(Integer.class));
                    Log.i("check","home"+serv_status);
                    //Log.i("check","home"+status_check);
                    if(Integer.parseInt(serv_status)==1)
                        Act_Serv="Only Children";
                    else if(Integer.parseInt(serv_status)==2)
                        Act_Serv="Only Oldsters";
                    else if(Integer.parseInt(serv_status)==3)
                        Act_Serv="Both";
                    else
                        Act_Serv="Not available";
                    RecyclerViewList r = new RecyclerViewList(name,place,price,Act_Serv);
                    list.add(r);
                }

                recyclerView.setAdapter(myadapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

            }


        });


    }

    public void applyTexts(int price, int status) {
            hprice=price;
            hstatus=status;
            filterationspec();
    }
}
