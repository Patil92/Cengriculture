package com.companies.patil.agri_di;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class selectStoragePlace extends AppCompatActivity {

    Spinner spinnerPlant;
    Button getData;
    ArrayAdapter<CharSequence> adapter1;
    EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_storage_place);

        final Spinner spinnerCity1 =findViewById(R.id.spinner1);
        getData =findViewById(R.id.getdata);
       // name = findViewById(R.id.citynamestorage);

        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Storage_Places, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity1.setAdapter(adapter1);


        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // final String city=name.getText().toString();
                final String city=spinnerCity1.getSelectedItem().toString();
                Intent intent=new Intent(selectStoragePlace.this,storage_plants.class);
                intent.putExtra("city",city);
                startActivity(intent);
            }
        });


    }
}
