package com.companies.patil.agri_di;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Data_collect_test extends AppCompatActivity {

    EditText acreData;
    Button submitData;
    String str;
    Spinner spinnerPlant;
    Spinner spinnerCity;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collect_test);

     // final TextView txt=findViewById(R.id.textV);

        final EditText acreData = findViewById(R.id.acreEdt);
        final Spinner spinnerCity =findViewById(R.id.spinnerCity);
        final Spinner spinnerPlant=findViewById(R.id.spinnerPlant);
        Button submitData =findViewById(R.id.submitdata);

        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.city_names, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter1);

        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Plants_names, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlant.setAdapter(adapter2);


        submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txt.setText(edt.getText().toString());
                String acre=acreData.getText().toString();
                String city=spinnerCity.getSelectedItem().toString();
                String plant=spinnerPlant.getSelectedItem().toString();

                final DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mRef1=myRef2.child("users").child("Patil");

                DataServay ds=new DataServay(plant,city,acre);

                mRef1.child("Data").setValue(ds);

                str="Acre : "+acre+"\nCity : "+city+"\nPlant : "+plant;
                //Toast.makeText(Data_collect_test.this,str,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Data_collect_test.this,servayresponse.class);
                intent.putExtra("acre",acre);
                intent.putExtra("city",city);
                intent.putExtra("plant",plant);
                startActivity(intent);

            }
        });
    }
}
