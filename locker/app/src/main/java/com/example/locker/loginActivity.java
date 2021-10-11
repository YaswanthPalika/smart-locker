package com.example.locker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    EditText emailId,password;
    Button btnSignIn,btnforgot;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String b="open";
        String a="close";

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId= findViewById(R.id.editTextTextPersonName);
        password= findViewById(R.id.editTextTextPassword2);
        btnSignIn= findViewById(R.id.button3);
        btnforgot= findViewById(R.id.button5);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("LOCKER 1");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String x=snapshot.getValue().toString();
                            if (x.equals(a)) {
                                Toast.makeText(loginActivity.this, "you have looged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(loginActivity.this, home.class);
                                startActivity(i);
                            }
                            else if(x.equals(b)){
                                Toast.makeText(loginActivity.this, "you have looged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(loginActivity.this, close.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        };
        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(loginActivity.this,forgotActivity.class);
                startActivity(i);
            }
        });
/*        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }

            private void showRecoverPasswordDialog() {
                //alertDialog
                AlertDialog.Builder builder=new AlertDialog.Builder(loginActivity.this);
                //linear layout
                LinearLayout linearLayout= new LinearLayout(loginActivity.this);
                final EditText emailET=new EditText(loginActivity.this);
                emailET.setHint("email");
                emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                linearLayout.addView(emailET);
                linearLayout.setPadding(10,10,10,10);
                builder.setView(linearLayout);
                //buttons
                builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    String email= emailET.getText().toString().trim();
                    beginRecovery(email);
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });*/
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= emailId.getText().toString();
                String pwd  =password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("please enter email id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("please enter password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(loginActivity.this,"fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!(task.isSuccessful())){
                                Toast.makeText(loginActivity.this,"login failed",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String x=snapshot.getValue().toString();
                                        if (x.equals(a)) {
                                            Toast.makeText(loginActivity.this, "you have looged in", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(loginActivity.this, home.class);
                                            startActivity(i);
                                        }
                                        else if(x.equals(b)){
                                            Toast.makeText(loginActivity.this, "you have looged in", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(loginActivity.this, close.class);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(loginActivity.this,"login failed",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

   /* private void beginRecovery(String email) {

        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()){
                  Toast.makeText(loginActivity.this,"recovery link sent to your email",Toast.LENGTH_SHORT).show();
              }
              else {
                  Toast.makeText(loginActivity.this,"link not sent",Toast.LENGTH_SHORT).show();
              }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

}