package com.example.finalprojectgroup8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Boolean userbool;
    SharedPreferences preferences;
    String usernamesession;
    DatabaseReference reference,reference2;
    Query checkuser,checkuser2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        preferences = this.getSharedPreferences("com.example.finalprojectgroup8",Context.MODE_PRIVATE);
        userbool = preferences.getBoolean("asNanny",true);
        String status;
        if(userbool == true)
            status="Nanny";
        else
            status="Employer";

        usernamesession=preferences.getString("username",null);
        String useremailsession = preferences.getString("email",null);
        View headerView = navigationView.getHeaderView(0);
        ImageView headimage = headerView.findViewById(R.id.photo);
        TextView headname = headerView.findViewById(R.id.hedname);
        TextView heademail = headerView.findViewById(R.id.hedemail);
        TextView headstatus = headerView.findViewById(R.id.hedstatus);
        int pic=R.drawable.logo;
        headimage.setImageResource(pic);
        headname.setText(usernamesession);
        heademail.setText(useremailsession);
        headstatus.setText("Status"+status);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.home) {
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home").addToBackStack("Home");
            fragmentTransaction.commit();
        }
        if (id == R.id.updt) {
            UpdateFragment fragment = new UpdateFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "z").addToBackStack("Home");
            fragmentTransaction.commit();

        }
        if(id == R.id.appointment)
        {
            My_Appointments_Fragment fragment = new My_Appointments_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"Appointment").addToBackStack("Home");
            fragmentTransaction.commit();
        }
        if (id == R.id.vp) {
            ViewFragment fragment = new ViewFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "View Profile").addToBackStack("Home");
            CreatingProfiles(usernamesession);
            fragmentTransaction.commit();
        }
        else if (id == R.id.review) {
            ReviewFragment fragment = new ReviewFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "My Reviews").addToBackStack("Home");
            fragmentTransaction.commit();
        }
        else if (id == R.id.bstnanny) {
            BnannyFragment fragment = new BnannyFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Best Nanny").addToBackStack("Home");
            fragmentTransaction.commit();
        } else if (id == R.id.bstemp) {
            BempFragment fragment = new BempFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Best Employer").addToBackStack("Home");
            fragmentTransaction.commit();
        } else if (id == R.id.lgout) {
            LogoutFragment fragment = new LogoutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Logout fragment").addToBackStack("Home");
            fragmentTransaction.commit();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.i("back butten","Pressed");
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
    public void CreatingProfiles(String getname){
        final String storename=getname;
        Log.i("name","Fragment"+storename);
        if (userbool==true){
            reference= FirebaseDatabase.getInstance().getReference("Profile Creation AsNanny");
            checkuser=reference.orderByChild("username").equalTo(storename);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String usernamefromdb = dataSnapshot.child(storename).child("username").getValue(String.class);
                        if (usernamefromdb.equals(storename)){

                            Toast.makeText(Main2Activity.this,"Profile Created",Toast.LENGTH_LONG);
                        }
                        else {
                            Toast.makeText(Main2Activity.this,"Create Your Profile",Toast.LENGTH_LONG);
                        }
                    }
                    else {
                        Toast.makeText(Main2Activity.this,"Create Your Profile AsNanny",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),ProfileCreationAsNanny.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else {

            reference2=FirebaseDatabase.getInstance().getReference("Profile Creation For Nanny");
            checkuser2=reference2.orderByChild("username").equalTo(storename);
            checkuser2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String usernamedb2= dataSnapshot.child(storename).child("username").getValue(String.class);

                        if (usernamedb2.equals(storename)){
                            Toast.makeText(Main2Activity.this,"Profile Created",Toast.LENGTH_LONG);

                        }
                        else {
                            Toast.makeText(Main2Activity.this,"Create Your Profile",Toast.LENGTH_LONG);

                        }

                    }
                    else {
                        Toast.makeText(Main2Activity.this,"Create Your Profile As For Nanny",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),ProfileCreationForNanny.class);
                        startActivity(intent);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }
}
