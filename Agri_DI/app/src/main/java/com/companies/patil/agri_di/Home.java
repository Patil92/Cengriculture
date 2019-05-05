package com.companies.patil.agri_di;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    @Override
    protected void onStart() {
        super.onStart();

        t=findViewById(R.id.Mission);
        t.setText("1)To increase the yield of crops and thereby to increase the income of the farmers. \n" +
                "2)To increase the living standards of the farmers.\n" +
                "3)To increase the Economy of the nation by maintaining a balanced demand and supply ratio.");
    }
}
