package com.companies.patil.agri_di;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class servayresponse extends AppCompatActivity {

    TextView res,desc;
    String acre,plant,city;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servayresponse);

        res= findViewById(R.id.res);
        desc = findViewById(R.id.desc);

        Bundle b =getIntent().getExtras();

        if(b!=null)
        {
            acre=b.getString("acre");
            plant=b.getString("plant");
            city=b.getString("city");
        }

        String url = "http://13.251.233.43/predict?acres="+acre.toString()+"&plant="+plant+"&city="+city;
        //String url="http://13.251.233.43/predict?acres=92&plant=tomato&city=Mysore";
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading....");
            dialog.show();

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {
                    parseJsonData(string);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(servayresponse.this);
            rQueue.add(request);


    }

            void parseJsonData(String jsonString) {
                try {
                    JSONObject object = new JSONObject(jsonString);
                    String s1=object.getString("Plant");
                    String s2=object.getString("Sugessions");

                    res.setText("Predicted Crop : "+s1);
                    desc.setText(s2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
    }
}
