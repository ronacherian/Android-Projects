package com.example.roncherian.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewContact extends AppCompatActivity {

    ImageView imageViewProfile;

    FirebaseAuth mAuth;

    DatabaseReference mRef= FirebaseDatabase.getInstance().getReference("Contacts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        setTitle("Create New Contact");

        imageViewProfile = (ImageView)findViewById(R.id.imageViewProfilePicture);

        Button submitButton = (Button)findViewById(R.id.buttonContactSubmit);

        final EditText nameText = (EditText)findViewById(R.id.editTextContactname);
        final EditText emailText = (EditText)findViewById(R.id.editTextContactEmail);
        final EditText phoneText = (EditText)findViewById(R.id.editTextContactPhone);
        //EditText departmenttext = (EditText)findViewById(R.id.edittext)

        final RadioGroup departmentChosen = (RadioGroup)findViewById(R.id.radioGroup);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameText.getText().toString().length() == 0){
                    Toast.makeText(CreateNewContact.this,"Enter Name",Toast.LENGTH_SHORT).show();
                    return;
                } else if (emailText.getText().toString().length() == 0){
                    Toast.makeText(CreateNewContact.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                } else if (phoneText.getText().toString().length() == 0){
                    Toast.makeText(CreateNewContact.this,"Enter Phone",Toast.LENGTH_SHORT).show();
                    return;
                } else if (imageViewProfile.getTag().toString().equals("0")){
                    Toast.makeText(CreateNewContact.this,"Choose Image",Toast.LENGTH_SHORT).show();
                    return;
                }
                Contacts contacts = new Contacts();

                RadioButton radioButton = (RadioButton) findViewById(departmentChosen.getCheckedRadioButtonId());

                contacts.setEmail(emailText.getText().toString());
                contacts.setName(nameText.getText().toString());
                contacts.setPhone(phoneText.getText().toString());
                contacts.setDepartment(radioButton.getText().toString());

                contacts.setImageId((int)imageViewProfile.getTag());
                String id=mRef.push().getKey();
                contacts.setId(id);
                mAuth = FirebaseAuth.getInstance();

                FirebaseUser user = mAuth.getCurrentUser();
                mRef.child(MainActivity.removeSplChars(user.getEmail())).child(id).setValue(contacts);
                finish();


            }
        });
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(CreateNewContact.this,SelectAvatarActivity.class);
                startActivityForResult(intent,MainActivity.PIC_SELECT_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getExtras()==null)
        {
            Log.d("demo","value is null");
        }

        if(requestCode==MainActivity.PIC_SELECT_REQ_CODE)
        {
            if(resultCode==RESULT_OK)
            {

                String value=data.getExtras().getString(MainActivity.PIC_SELECTOR).toString();
                if(value.equals("1"))
                {
                    imageViewProfile.setImageResource(R.drawable.avatar_f_1);
                    imageViewProfile.setTag(1001);

                }else if(value.equals("2"))
                {
                    imageViewProfile.setImageResource(R.drawable.avatar_m_3);
                    imageViewProfile.setTag(1002);
                }else if(value.equals("3"))
                {
                    imageViewProfile.setImageResource(R.drawable.avatar_f_2);
                    imageViewProfile.setTag(1003);
                }else if(value.equals("4"))
                {
                    imageViewProfile.setImageResource(R.drawable.avatar_m_2);
                    imageViewProfile.setTag(1004);
                }else if(value.equals("5"))
                {
                    imageViewProfile.setImageResource(R.drawable.avatar_f_3);
                    imageViewProfile.setTag(1005);
                }else
                {
                    imageViewProfile.setImageResource(R.drawable.avatar_m_1);
                    imageViewProfile.setTag(1006);
                }

            }
            if(resultCode==0)
            {
                Log.d("Demo","No Avatar Selected");
            }
        }
    }
}
