package com.example.richtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextView mHeader;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mRegister;
    private TextView mLoginHere;
    private ProgressBar progressBar;
    private FirebaseAuth mfAuth = FirebaseAuth.getInstance();
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        mHeader = findViewById(R.id.textView_header_reg);
        mEmail = findViewById(R.id.editText_email_reg);
        mPassword = findViewById(R.id.editText_password_reg);
        mConfirmPassword = findViewById(R.id.editText_confirm_password);
        mRegister = findViewById(R.id.button_register);
        mLoginHere = findViewById(R.id.textView_login);
        progressBar = findViewById(R.id.progressBar_reg);


        registerUser();

        mLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage();
            }
        });
    }

    private void goToLoginPage() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void registerUser(){
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                if(email.isEmpty()){
                    mEmail.setError("Please provide an email address");
                    return;
                }
                if(password.isEmpty()){
                    mPassword.setError("Password is required");
                    return;
                }
                if(confirmPassword.isEmpty()){
                    mConfirmPassword.setError("please put in same password");
                    return;
                }

                if(!password.equals(confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mfAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showProgressBar();
                            Log.d(TAG, "onComplete: Succesful" +task.isSuccessful());
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            mfAuth.signOut();
                            goToLoginPage();

                        }else{
                            Log.d(TAG, "onComplete: Not Successful" +task.getResult());
                            Toast.makeText(RegisterActivity.this, "Unable to Register", Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }
                    }
                });

            }
        });
    }


}

