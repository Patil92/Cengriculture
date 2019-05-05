package com.companies.patil.agri_di;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class rent_show_details extends AppCompatActivity {

    String itemName,Place;

    String item_name="",item_place="",item_owner="",item_phone="",item_add="",item_url="";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Rent");

    TextView ItemName,Owner,place,add,phone;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_show_details);


        ItemName = findViewById(R.id.item_name);
        place = findViewById(R.id.Place);
        Owner = findViewById(R.id.Owner_name);
        phone =findViewById(R.id.phone);
        add= findViewById(R.id.contact);
        imgview = findViewById(R.id.item_img);

    }

    public void toastTop(String data)
    {
        Toast toast = Toast.makeText(rent_show_details.this,data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();

        if( bundle!= null)
        {
            itemName=bundle.getString("ItemName");
            Place=bundle.getString("ItemPlace");
        }

        //toastTop(itemName + " "+Place);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            String str;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    String s1=postSnapshot.child("item_name").getValue().toString();
                    String s2=postSnapshot.child("Place").getValue().toString();

                    if((s1.equals(itemName)) && (s2.equals(Place)))
                    {
                        item_name = postSnapshot.child("item_name").getValue().toString();
                        item_add = postSnapshot.child("address").getValue().toString();
                        item_owner = postSnapshot.child("name").getValue().toString();
                        item_phone = postSnapshot.child("mobile").getValue().toString();
                        item_place = postSnapshot.child("Place").getValue().toString();
                        item_url = postSnapshot.child("ImageUrl").getValue().toString();

                        //toastTop(item_name + " "+item_phone + " "+ item_place);

                        ItemName.setText("Item Name : "+item_name);
                        place.setText("Place : "+item_place);
                        Owner.setText("Owner : "+item_owner);
                        phone.setText("Mobile : " +item_phone);
                        add.setText("Address : "+item_add);

                        Picasso.get().load(item_url).into(imgview);
                               // .load(item_url).into();


                        break;
                    }
                    //str= postSnapshot.child("item_name").getValue().toString() + "\n ";

                   // toastTop(s1==itemName +" "+s2.equals(Place));
                }
                    //toastTop(str);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do something about the error
            }});

       /* myRef.addValueEventListener(new ValueEventListener() {
            String str="";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    String s1=dataSnapshot.child("item_name").getValue().toString();
                    String s2=dataSnapshot.child("Place").getValue().toString();

                   if((s1 == itemName) && (s2==Place) )
                    {
                        item_name = postSnapshot.child("item_name").getValue().toString();
                        item_add = postSnapshot.child("address").getValue().toString();
                        item_owner = postSnapshot.child("name").getValue().toString();
                        item_phone = postSnapshot.child("mobile").getValue().toString();
                        item_place = postSnapshot.child("Place").getValue().toString();
                    }
                    //str= postSnapshot.child("item_name").getValue().toString() + "\n ";

                   // toastTop(s1==itemName +" "+s2.equals(Place));
                }

                //toastTop(str);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                toastTop("Failed To Retrieve Data...");
            }
        });*/



    }

}
