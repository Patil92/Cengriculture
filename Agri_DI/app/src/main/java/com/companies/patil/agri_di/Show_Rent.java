package com.companies.patil.agri_di;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class Show_Rent extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Rent");

    ArrayList<String> arrayListItem = new ArrayList<>();
    ArrayList<String> arrayListPlace = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__rent);

        listView=findViewById(R.id.rentView);

        final CustomAdapter customAdapter =new CustomAdapter(this,arrayListItem,arrayListPlace);
        listView.setAdapter(customAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    arrayListItem.add(postSnapshot.child("item_name").getValue().toString());
                    arrayListPlace.add(postSnapshot.child("Place").getValue().toString());
                }

                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                toastTop("Failed To Retrieve Data...");
            }
        });

        /*ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                arrayListItem.add(dataSnapshot.child("item_name").getValue().toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                toastTop("Failed to load comments.");
            }
        };

        myRef.addChildEventListener(childEventListener);*/





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(Show_Rent.this,rent_show_details.class);
                intent.putExtra("ItemName",arrayListItem.get(position));
                intent.putExtra("ItemPlace",arrayListPlace.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    public void toastTop(String data)
    {
        Toast toast = Toast.makeText(Show_Rent.this,data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    class CustomAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<String> arItem,arPlace;
        private LayoutInflater inflater=null;

        CustomAdapter(Context con,ArrayList<String> s1, ArrayList<String> s2)
        {
            this.context=con;
            this.arItem=s1;
            this.arPlace=s2;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arItem.size();
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
                vi = inflater.inflate(R.layout.list_view_show_items, null);


            TextView item=vi.findViewById(R.id.item);
            TextView place=vi. findViewById(R.id.place);

            item.setText(arItem.get(position));
            place.setText(arPlace.get(position));

            return vi;
        }
    }
}
