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

public class LoginActivity extends AppCompatActivity {

    private TextView mheader;
    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private TextView signUp;
    private ProgressBar progressBar;
    private FirebaseAuth mfAuth = FirebaseAuth.getInstance();
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mheader = findViewById(R.id.textView_header);
        mEmail = findViewById(R.id.editText_email);
        mPassword = findViewById(R.id.editText_password);
        mLogin = findViewById(R.id.button_login);
        signUp = findViewById(R.id.textView_signUp);
        progressBar = findViewById(R.id.progressBar);

        checkIfUserIsSignedIn();

        loginUSer();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              goToRegistrationPage();
            }
        });
    }

    private  void goToRegistrationPage(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    private void loginUSer(){
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();

                if(email.isEmpty()){
                    mEmail.setError("Email is required");
                    return;
                }
                if(password.isEmpty()){
                    mPassword.setError("password is required");
                    return;
                }

                mfAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        showProgressBar();
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: login Succesful" +task.isSuccessful());
                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                            goToMainPage();

                        }else{
                            Log.d(TAG, "onComplete: login not successful" +task.getResult());
                            Toast.makeText(LoginActivity.this, "Unable to Login", Toast.LENGTH_SHORT).show();
                            hideProgressBar();

                        }
                    }
                });
            }
        });
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void checkIfUserIsSignedIn(){
        if(mfAuth.getCurrentUser()!= null){
            goToMainPage();

        }

    }

    private void goToMainPage(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}