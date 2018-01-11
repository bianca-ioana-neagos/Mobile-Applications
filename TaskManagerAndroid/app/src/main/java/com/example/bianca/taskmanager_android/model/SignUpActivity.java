package com.example.bianca.taskmanager_android.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bianca.taskmanager_android.MainActivity;
import com.example.bianca.taskmanager_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by BIANCA on 10.01.2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getName();
    Button signup;
    EditText email, pass;
    CheckBox isManager;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);

        email = (EditText) findViewById(R.id.editEmail);
        pass = (EditText) findViewById(R.id.editPassword);
        signup = (Button) findViewById(R.id.signUp);
        isManager = (CheckBox) findViewById(R.id.checkBox);
        firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    dbRef.child(EncodeString(firebaseAuth.getCurrentUser().getEmail())).setValue(new User(
                                            email.getText().toString(),
                                            pass.getText().toString(),
                                            isManager.isChecked()
                                    ));
                                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(SignUpActivity.this, "Registration failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    public String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
