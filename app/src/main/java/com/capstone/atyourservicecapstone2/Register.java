package com.capstone.atyourservicecapstone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    //firebase Authentication declaration..
    private FirebaseAuth mAuth;

    //Object Declarations..
    private EditText Fullname,Age,Email,Password;
    private Button register;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //firebase database reference initialization
        mAuth = FirebaseAuth.getInstance();


        //Objects Initialization..
        Fullname = (EditText)findViewById(R.id.Fullname_edittxt);
        Age = (EditText)findViewById(R.id.Age_edittxt);
        Email = (EditText)findViewById(R.id.Email_edittxt);
        Password = (EditText)findViewById(R.id.Password_edittxt);
        register = (Button)findViewById(R.id.register_btn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });

    }




    public void SignUp(){

     //fetching data from EditText..
        String fullname_data = Fullname.getText().toString().trim();
        String age_data= Age.getText().toString().trim();
        String email_data= Email.getText().toString().trim();
        String password_data= Password.getText().toString().trim();

     //validation check..
        if (fullname_data.isEmpty()){
            Fullname.setError("Fullname is Required!");
            Fullname.requestFocus();
            return;
        }
        if (age_data.isEmpty()){
            Age.setError("Age is Required!");
            Age.requestFocus();
            return;
        }
        if (email_data.isEmpty()){
            Email.setError("Email is Required!");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_data).matches()){
            Email.setError("Please provide valid email!");
            Email.requestFocus();
            return;
        }
        if (password_data.isEmpty()){
            Password.setError("Password is Required!");
            Password.requestFocus();
            return;
        }
        if (Password.length() < 6){
            Password.setError("Minimum of 6 characters");
            Password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //firebase saving client data...
        mAuth.createUserWithEmailAndPassword(email_data, password_data).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                  ClientData client= new ClientData(fullname_data, age_data, email_data);
                 FirebaseDatabase.getInstance().getReference("Clients")
                         .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                         .setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {

                                 if (task.isSuccessful()){

                                     Toast.makeText(Register.this, "Client Registered", Toast.LENGTH_LONG).show();
                                     startActivity(new Intent(Register.this, ClientLogIn.class));
                                     progressBar.setVisibility(View.GONE);
                                 }
                                 else {
                                     Toast.makeText(Register.this, "Registration Fail, please try again!", Toast.LENGTH_LONG).show();
                                     progressBar.setVisibility(View.GONE);
                                 }
                             }
                         });

                }





            }
        });

    }
}