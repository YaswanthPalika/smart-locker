package com.example.locker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home extends AppCompatActivity {
    Button btnLogOut,btnopen;
    FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("LOCKER 1");
        btnLogOut=findViewById(R.id.log_out);
        btnopen  =findViewById(R.id.open);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intoMain = new Intent(home.this,MainActivity.class);
                startActivity(intoMain);
            }
        });
        btnopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(home.this,close.class);
                reference.setValue("open");
                startActivity(i);

            }
        });

    }
}