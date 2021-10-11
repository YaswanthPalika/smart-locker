package com.example.locker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotActivity extends AppCompatActivity {
    Button recovery;
    FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        recovery=findViewById(R.id.button6);
        mFirebaseAuth = FirebaseAuth.getInstance();
        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                //linear layout
                LinearLayout linearLayout= new LinearLayout(v.getContext());
                final EditText emailET=new EditText(v.getContext());
                emailET.setHint("enter your email here");
                emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                linearLayout.addView(emailET);
                linearLayout.setPadding(10,10,10,10);
                builder.setView(linearLayout);
                //buttons
                builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email= emailET.getText().toString().trim();
                        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(forgotActivity.this,"recovery link sent to your email",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(forgotActivity.this,"link not sent",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {
                                Toast.makeText(forgotActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }

        });
    }


}