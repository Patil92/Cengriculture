package com.companies.patil.agri_di;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

public class main_disp extends AppCompatActivity {


    TextView txtintent,textView2;
    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_disp);

       // txtintent = findViewById(R.id.txtintent);
       // textView2 = findViewById(R.id.textView2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_dataservay:
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new DataServayFragment()).commit();*/
                         intent = new Intent(main_disp.this,Data_collect_test.class);
                        startActivity(intent);
                        break;

                    case R.id.rent_materials:
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new GiveRentFragment()).commit();*/
                         intent = new Intent(main_disp.this,Give_Rent.class);
                        startActivity(intent);
                        break;

                    case R.id.show_rent:
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ShowRentFragment()).commit();*/
                        intent = new Intent(main_disp.this,Show_Rent.class);
                        startActivity(intent);
                        break;

                    case R.id.weather:
                       /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ShowWeatherFragment()).commit();*/
                        intent = new Intent(main_disp.this,temp_data.class);
                       startActivity(intent);
                        break;

                    case R.id.nav_plant_amt:
                       /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ShowWeatherFragment()).commit();*/
                    intent = new Intent(main_disp.this,DailyPlantAmount.class);
                    startActivity(intent);
                    break;

                    case R.id.nav_home:
                       /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ShowWeatherFragment()).commit();*/
                        intent = new Intent(main_disp.this,Home.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_storage_place:
                       /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ShowWeatherFragment()).commit();*/
                        intent = new Intent(main_disp.this,selectStoragePlace.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_share:
                        toastTop("Share");
                        break;
                    case R.id.nav_send:
                        toastTop("Send");
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);



                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    protected void onStart() {
        super.onStart();

        String userid=null;
        String db_id=null;
        Bundle intent = getIntent().getExtras();

        if(intent !=null)
        {
            userid= intent.getString("uid");
            db_id=intent.getString("db_id");
            textView2.setText(userid);

            toastTop("User : " +userid);
        }




    }*/

    public void toastTop(String data)
    {
        Toast toast = Toast.makeText(main_disp.this,data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}

  /* mDatabase.equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user= null;
                try {
                    user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(user !=null) {
                    String s = user.getEmail() + "\n" + user.getPhonenum() + "\n" + user.getid();
                    txtintent.setText(s);
                }

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("User ->>> ",firebaseError.getMessage() );
            }
        });*/