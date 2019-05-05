package com.companies.patil.agri_di;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DailyPlantAmount extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Plant_Amt");

    int IMAGES[]={R.drawable.barli,R.drawable.chilli,R.drawable.rice,R.drawable.tomato,R.drawable.wheat};

    final String names[]={"Barley","Chilli","Rice","Tomato","Wheat"};

     String plant_amt[]={"","","","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plant_amount);


        //this.context=context;
        ListView listview=findViewById(R.id.Listview);
       final CustomAdapter ca=new CustomAdapter(this,IMAGES,names,plant_amt);
        // CustomAdapter ca=new CustomAdapter();
        listview.setAdapter(ca);

       myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(int i=0;i<names.length;i++)
                {
                    plant_amt[i]=dataSnapshot.child(names[i]).getValue().toString();
                }

                ca.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(DailyPlantAmount.this,"Failed to read value.",Toast.LENGTH_LONG).show();
            }
        });

        //Toast.makeText(DailyPlantAmount.this,(plant_amt.length-1)+"",Toast.LENGTH_LONG).show();

    }

    class CustomAdapter extends BaseAdapter{

        Context context;
        String[] names,plant_amt;
        int [] IMAGES;
        private LayoutInflater inflater=null;

       // int IMAGES[]={R.drawable.barli,R.drawable.chilli,R.drawable.rice,R.drawable.tomato,R.drawable.wheat};

        //final String names[]={"Barley","Chilli","Rice","Tomato","Wheat"};

        CustomAdapter(Context con,int [] IMAGES,String[] names,String [] plant_amt)
        {
            //super(con,R.layout.daily_plant_amount_layout,R.id.textView2,names);
            this.context=con;
            this.IMAGES=IMAGES;
            this.names=names;
            this.plant_amt=plant_amt;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View vi=convertView;
            if(convertView==null)
                vi = inflater.inflate(R.layout.daily_plant_amount_layout, null);

            //LayoutInflater inflater = getLayoutInflater();
            //convertView = inflater.inflate(R.layout.daily_plant_amount_layout, null);

            //convertView=getLayoutInflater().inflate(R.layout.daily_plant_amount_layout,null);
           // LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //View convert_View=layoutInflater.inflate(R.layout.daily_plant_amount_layout,null);

            ImageView imageView=vi.findViewById(R.id.img);
            TextView plantname=vi.findViewById(R.id.plant_name);
            TextView amt=vi. findViewById(R.id.amt);

            imageView.setImageResource(IMAGES[position]);
            plantname.setText(names[position]);
            amt.setText(plant_amt[position]+" Rs/- only");



            return vi;
        }
    }

}
