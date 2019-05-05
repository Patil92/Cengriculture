package com.companies.patil.agri_di;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class storage_plants extends AppCompatActivity {

    ArrayList<String> city = new ArrayList<>();
    ArrayList<String> Contact=new ArrayList<>();
    ArrayList<String> address=new ArrayList<>();
    ArrayList<String> url1=new ArrayList<>();

    ListView ls;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Storage");

    String str="";
    String place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_plants);

        ls= findViewById(R.id.storageList);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
            place=bundle.getString("city");

        toastTop(place);
        final CustomAdapterStorage customAdapter =new CustomAdapterStorage(this,city,Contact,address,url1);
        ls.setAdapter(customAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                   String s1= postSnapshot.child("City").getValue().toString();

                   if(s1.equals(place)) {
                       city.add(postSnapshot.child("City").getValue().toString());
                       Contact.add(postSnapshot.child("Contact").getValue().toString());
                       address.add(postSnapshot.child("Address").getValue().toString());
                       url1.add(postSnapshot.child("url").getValue().toString());

                       String s2 =postSnapshot.child("City").getValue().toString();
                       String s3=postSnapshot.child("Contact").getValue().toString();
                       String s4=postSnapshot.child("Address").getValue().toString();
                       String s5=postSnapshot.child("url").getValue().toString();

                       str +=s5+"\n";

                   }
                }
                //toastTop(str.toString());
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                toastTop("Failed To Retrieve Data...");
            }
        });


    }
    public void toastTop(String data)
    {
        Toast toast = Toast.makeText(storage_plants.this,data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }


    class CustomAdapterStorage extends BaseAdapter
    {
        Context context;
        ArrayList<String> city,Contact,url1,address;
        private LayoutInflater inflater=null;

        CustomAdapterStorage(Context con,ArrayList<String> city, ArrayList<String> Contact,ArrayList<String> address,ArrayList<String> url)
        {
            this.context=con;
            this.address=address;
            this.city=city;
            this.Contact=Contact;
            this.url1=url;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return city.size();
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
                vi = inflater.inflate(R.layout.storage_listview, null);

            //ImageView img1= findViewById(R.id.imgStorage);
            TextView cityname=vi.findViewById(R.id.name);
            TextView contact=vi. findViewById(R.id.contact);
            TextView add=vi. findViewById(R.id.address);

            cityname.setText(city.get(position));
            contact.setText(Contact.get(position));
            add.setText(address.get(position));

            String s1=url1.get(position);
            //Picasso.get().load(s1).into(img1);

            return vi;
        }
    }
}
