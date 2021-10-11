package com.example.locker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText emailId,password,cpassword;
    Button btnSignUp,btnSignIn;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId= findViewById(R.id.editTextTextPersonName2);
        password= findViewById(R.id.editTextTextPassword);
        cpassword=findViewById(R.id.editTextTextPassword3);
        btnSignUp= findViewById(R.id.button);
        btnSignIn= findViewById(R.id.button2);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pwd =password.getText().toString();
                String cpwd=cpassword.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("please enter email id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("please enter password");
                    password.requestFocus();
                }
                else if(cpwd.isEmpty()){
                    cpassword.setError("please confirm password");
                    cpassword.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty() && cpwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty() && cpwd.isEmpty())){

                    if(pwd.equals(cpwd)){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!(task.isSuccessful())){
                                        Toast.makeText(MainActivity.this,"signUp failed",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        startActivity(new Intent(MainActivity.this,
                                                home.class));
                                    }
                                }
                            });
                    }
                    else  {
                        Toast.makeText(MainActivity.this,"password mismatch",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this,"Error occured",Toast.LENGTH_SHORT).show();
                }

                }


        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l= new Intent(MainActivity.this,loginActivity.class);
                startActivity(l);
            }
        });
        //tvSignIn.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
              //  Intent i = new Intent(MainActivity.this,loginActivity.class);
            //    startActivity(i);
          //  }
        //});
    }
}