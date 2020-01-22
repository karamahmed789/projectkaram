package com.example.noteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuthAdapter authAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        authAdapter = FirebaseAuthAdapter.getInstace(getApplicationContext());
        if(authAdapter.checkAuth()) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }

        final EditText emailET = findViewById(R.id.useremail_et),
                passwordET = findViewById(R.id.passworduser_et);

        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString(),
                        password = passwordET.getText().toString();

                if(authAdapter.signUp(email, password)) {
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                }
            }
        });

        findViewById(R.id.go_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }
}
