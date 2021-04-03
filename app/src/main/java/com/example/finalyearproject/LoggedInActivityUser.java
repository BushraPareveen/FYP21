package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LoggedInActivityUser extends AppCompatActivity {

    private Button signoutBtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);
        Button button = (Button) findViewById(R.id.employerredirectbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoggedInActivityUser.this, LoggedInActivityEmployer.class);
                startActivity(intent);
            }
        });
        //signoutBtn = findViewById(R.id.signOut);
        mAuth=FirebaseAuth.getInstance();
       /* signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(LoggedInActivity.this, MainActivity.class));
                finish();
            }
        });*/
    }
}