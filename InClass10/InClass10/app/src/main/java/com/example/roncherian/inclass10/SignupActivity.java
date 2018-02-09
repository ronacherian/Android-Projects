package com.example.roncherian.inclass10;

import android.app.Activity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        final EditText firstName=(EditText)findViewById(R.id.editTextFirstName);
        final EditText lastName=(EditText)findViewById(R.id.editTextLastName);
        final EditText email=(EditText)findViewById(R.id.editTextEmail);
        final EditText password=(EditText)findViewById(R.id.editTextPassword);
        final EditText confirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        Button cancelSignUp = (Button)findViewById(R.id.buttonSignupCancel);
        cancelSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button signup= (Button)findViewById(R.id.buttonSignupSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firstName.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (lastName.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (confirmPassword.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                    if(password.getText().toString().equals(confirmPassword.getText().toString()))
                    {
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(SignupActivity.this,"User Successfully created",Toast.LENGTH_SHORT).show();
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("signupstatus", "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();

                                            Intent resultIntent = new Intent();
                                            setResult(Activity.RESULT_OK, resultIntent);
                                            finish();

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("signupstatus", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignupActivity.this, task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            // updateUI(null);
                                        }

                                        // ...
                                    }
                                });

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Password not matching", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200){
            if (resultCode == RESULT_OK){

            }
        }
    }
}
