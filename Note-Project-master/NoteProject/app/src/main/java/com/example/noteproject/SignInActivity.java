package com.example.noteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {
    FirebaseAuthAdapter authAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        authAdapter = FirebaseAuthAdapter.getInstace(getApplicationContext());
        if(authAdapter.checkAuth()) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }

        final EditText emailET = findViewById(R.id.username_et),
                passwordET = findViewById(R.id.password_et);

        findViewById(R.id.signin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString(),
                        password = passwordET.getText().toString();

                if(authAdapter.login(email, password)) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                }
            }
        });

        findViewById(R.id.go_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

    }
}
