package com.example.noteproject;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class FirebaseAuthAdapter {
    private static FirebaseAuthAdapter instace;
    private Context context;

    boolean correct = false;

    private FirebaseAuthAdapter(Context context) {
        this.context = context;
    }

    public static FirebaseAuthAdapter getInstace (Context context) {
        if(instace == null)
            instace = new FirebaseAuthAdapter(context);

        return instace;
    }

    public boolean checkAuth () {
        if(getAuth().getCurrentUser() != null) {
            return true;
        }
        return false;
     }

    public boolean login(String email, String password) {

        getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    correct = true;
                }else {
                    Toast.makeText(context, "check your data", Toast.LENGTH_LONG).show();
                    correct = false;
                }
            }
        });

        return correct;
    }

    public boolean signUp(String email, String password) {
        getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    correct= true;
                }else {
                    Toast.makeText(context,"check your data", Toast.LENGTH_LONG).show();
                    correct = false;
                }
            }
        });

        return correct;
    }

    public void logout () {
        getAuth().signOut();
    }

    private FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }
}
