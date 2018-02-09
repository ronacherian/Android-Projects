package com.example.roncherian.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactsList extends AppCompatActivity implements RecyclerAdapter.OnClick{

    ArrayList<Contacts> contactList= new ArrayList<Contacts>();
    DatabaseReference mRef;

    FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        setTitle("Contacts");

        mRef = FirebaseDatabase.getInstance().getReference("Contacts");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference mChildRef=mRef.child(user.getUid());

        Button createNewContactButton = (Button)findViewById(R.id.buttonCreateNewContact);
        Button logoutButton = (Button)findViewById(R.id.buttonLogout);

        createNewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsList.this, CreateNewContact.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        mChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contactList.clear();

                for (DataSnapshot artistSnapShot:dataSnapshot.getChildren()) {

                    Contacts contacts=artistSnapShot.getValue(Contacts.class);
                    contactList.add(contacts);

                }

                mRecyclerView=(RecyclerView)findViewById(R.id.myRecyclerView);

                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(ContactsList.this, LinearLayoutManager.VERTICAL,false);
                mRecyclerView.setLayoutManager(mLayoutManager);


                // specify an adapter (see also next example)
                mAdapter = new RecyclerAdapter(contactList,ContactsList.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100){//From Create New Contact
            if (resultCode==RESULT_OK){

                FirebaseUser user = mAuth.getCurrentUser();
                DatabaseReference mChildRef=mRef.child(user.getUid());

                mChildRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        contactList.clear();

                        for (DataSnapshot artistSnapShot:dataSnapshot.getChildren()) {

                            Contacts contacts=artistSnapShot.getValue(Contacts.class);
                            contactList.add(contacts);

                        }

                        mRecyclerView=(RecyclerView)findViewById(R.id.myRecyclerView);

                        mRecyclerView.setHasFixedSize(true);

                        // use a linear layout manager
                        mLayoutManager = new LinearLayoutManager(ContactsList.this, LinearLayoutManager.VERTICAL,false);
                        mRecyclerView.setLayoutManager(mLayoutManager);


                        // specify an adapter (see also next example)
                        mAdapter = new RecyclerAdapter(contactList,ContactsList.this);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }
    }

    @Override
    public void onItemClicked(int position, final Contacts contacts) {

        FirebaseUser user = mAuth.getCurrentUser();
        mRef.child(MainActivity.removeSplChars(user.getEmail())).child(contacts.getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                contactList.remove(contacts);
                mAdapter = new RecyclerAdapter(contactList,ContactsList.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

    }
}
