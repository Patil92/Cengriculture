package com.companies.patil.agri_di;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class temp_data extends AppCompatActivity {

    TextView tempdata;
    TextView humydata,moisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_data);




    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    public void loadData()
    {
        final TextView tempdata=findViewById(R.id.tempdata);
        final TextView humydata=findViewById(R.id.humydata);
        final TextView moisture=findViewById(R.id.moisturedata);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Weather");

        myRef.child("Mysore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Weather weather =new Weather();
                String temp = dataSnapshot.child("temp").getValue().toString();
                String humy = dataSnapshot.child("humy").getValue().toString();
                String moist = dataSnapshot.child("moisture").getValue().toString();

                tempdata.setText(temp+" oC");
                humydata.setText(humy);
                moisture.setText(moist);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(temp_data.this,"Failed to read value.",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void onBackPressed(){
        Intent a = new Intent(temp_data.this,main_disp.class);
        startActivity(a);
    }

}


