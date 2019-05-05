package com.companies.patil.agri_di;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener{


    ProgressBar progressBar;
    EditText editTextEmail, editTextPassword,editTextPhonenum;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhonenum = (EditText) findViewById(R.id.editTextPhonenum);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();



        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phonenum = editTextPhonenum.getText().toString().trim();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if (phonenum.isEmpty()) {
            editTextPhonenum.setError("Phone Number is required");
            editTextPhonenum.requestFocus();
            return;
        }

        if (phonenum.length() !=10) {
            editTextPhonenum.setError("Please enter valid Phone number");
            editTextPhonenum.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);



        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Creating new user node, which returns the unique key value
        // new user node would be /users/$userid/
        final String userId = mDatabase.push().getKey();


        // creating user object
       final User user= new User(userId,email,password,phonenum);


        // pushing user to 'users' node using the userId
       // mDatabase.child(userId).setValue(user);

        //DatabaseReference dataRef = mDatabase.child("data");




        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    mDatabase.child(split(email)).setValue(user);

                    Intent intent = new Intent(signup.this, main_disp.class);
                    // intent.putExtra("userId",userId); // getText() SHOULD NOT be static!!!
                    intent.putExtra("db_id",split(email));

                    startActivity(intent);

                    startActivity(new Intent(signup.this, main_disp.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    public String split(String em)
    {
        String sp[]=em.split("@");
        return sp[0];
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(signup.this, main_disp.class);
           // intent.putExtra("userId",userId); // getText() SHOULD NOT be static!!!
            intent.putExtra("uid",mAuth.getCurrentUser().getEmail());
            startActivity(intent);

            finish();
            startActivity(new Intent(this, main_disp.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                //finish();
                //startActivity(new Intent(this,main_disp.class));
                break;

           /* case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;*/
        }
    }
}

