package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEmail, mPass;
    private Button signUpBtn;
    private FirebaseAuth mAuth;
    private CheckBox ShowPassword;
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        mEmail = findViewById(R.id.UserID_text);
        mPass = findViewById(R.id.Password_text);
        signUpBtn = findViewById(R.id.Signup_button);
        mTextView=findViewById(R.id.sign_in_text);
        ShowPassword = findViewById(R.id.checkBox);
        mAuth = FirebaseAuth.getInstance();
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
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
    }
    private void createUser(){
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(pass.length()<8 && !isValidPassword(pass)){

                                    Toast.makeText(SignUp.this, "Password is too weak! It must be more than 8 letters and also should contain a special character", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, MainActivity.class));
                                    finish();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Registration Error!", Toast.LENGTH_SHORT).show();
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