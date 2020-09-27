package com.example.richtech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView mHeader;
    private Button mLogOut;
    private FirebaseAuth mFAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeader = findViewById(R.id.textView_welcome);
        mLogOut = findViewById(R.id.button_logout);

        logOutUser();
    }

    private void logOutUser(){
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        });
    }
}