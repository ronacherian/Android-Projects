package com.example.roncherian.inclass10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;


    final static  String PIC_SELECTOR="PIC SELECTOR";

    final static  int PIC_SELECT_REQ_CODE = 500;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("users");

        mAuth = FirebaseAuth.getInstance();
        final EditText email=(EditText)findViewById(R.id.editTextEmail);
        final EditText password=(EditText)findViewById(R.id.editTextPassword) ;

        Button login=(Button)findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String emailText = email.getText().toString();
                String passText = password.getText().toString();
                if (emailText.length()==0){
                    Toast.makeText(MainActivity.this, "Enter Email",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (passText.length() == 0){
                    Toast.makeText(MainActivity.this, "Enter password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailText, passText)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Login", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUsers(user.getUid());

                                    Intent intent = new Intent(MainActivity.this, ContactsList.class);
                                    startActivityForResult(intent,100);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("login", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
        Button signup=(Button)findViewById(R.id.buttonSignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(MainActivity.this,SignupActivity.class);
                startActivityForResult(intent,333);
            }
        });



    }

    private void updateUsers(String email){


        mDatabase.child(removeSplChars(email)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
                    Log.d("Demo", "Email is: " + user.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Demo", "Failed to read value.", error.toException());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            if (resultCode == RESULT_OK){

            }
        } else if (requestCode == 333){
            if (resultCode == RESULT_OK){
                Intent intent = new Intent(MainActivity.this, ContactsList.class);
                startActivityForResult(intent,200);
            }
        }
    }

    public static String removeSplChars(String email){

        //FirebaseUser user = mAuth.getCurrentUser();

        return email.replace("@","").replace(".","").replace("_","");
    }
}
