package com.companies.patil.agri_di;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class data_collect extends AppCompatActivity{

    Spinner spinnerPlant;
    Spinner spinnerCity;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;

    EditText acreEdt;
    Button submitData;
    static String acredata,SpinnerCity,SpinnerPlant;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collect);

        final Spinner spinnerPlant= findViewById(R.id.spinnerPlant);
        final Spinner spinnerCity= findViewById(R.id.spinnerCity);
        acreEdt= findViewById(R.id.acreedt);


       // Toast.makeText(data_collect.this,acredata,Toast.LENGTH_LONG).show();

        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.city_names, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter1);

        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Plants_names, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlant.setAdapter(adapter2);

       // mAuth = FirebaseAuth.getInstance();

        //DatabaseReference usersRef = myRef;


        submitData=findViewById(R.id.submitdata);

        submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acredata=acreEdt.getText().toString();
                SpinnerCity = spinnerCity.getSelectedItem().toString();
                SpinnerPlant = spinnerPlant.getSelectedItem().toString();

                final DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mRef1=myRef2.child("users").child("Abhi");

                DataServay ds=new DataServay(SpinnerPlant,SpinnerCity,acredata);

                mRef1.child("Data").setValue(ds);


                String str="Acre : "+acredata+" "+"City : "+SpinnerCity+" Plant : "+SpinnerPlant;
                Toast.makeText(data_collect.this,str,Toast.LENGTH_LONG).show();
            }
        });
    }


}
