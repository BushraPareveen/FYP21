package com.example.finalyearproject;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button signInBtn;
    private CheckBox ShowPassword;
    private FirebaseAuth mAuth;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //  WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        mTextView=findViewById(R.id.sign_up_text);
        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.UserID_text);
        mPass = (EditText) findViewById(R.id.Password_text);
        signInBtn = (Button) findViewById(R.id.Login_button);
        ShowPassword = (CheckBox) findViewById(R.id.checkBox);
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
        ShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    //show password
                    mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    //hide password
                    mPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        signInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


        Button Cancel = (Button) findViewById(R.id.Cancel_button);
        Cancel.setOnClickListener(new OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          finish();
                                          System.exit(0);
                                      }
                                  }
        );
    }
    private void loginUser(){
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(MainActivity.this, "Logged In Successfully!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, LoggedinActivityofAdmin.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                mPass.setError("Empty Fields are not Allowed");
            }
        }
        else if(email.isEmpty()){
            mEmail.setError("Empty Fields are not Allowed");
        }
        else{
            mEmail.setError("Please Enter correct email");
        }
    }

}