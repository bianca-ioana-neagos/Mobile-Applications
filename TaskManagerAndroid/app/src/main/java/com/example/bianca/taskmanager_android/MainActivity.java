package com.example.bianca.taskmanager_android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bianca.taskmanager_android.model.ActivityRepo;
import com.example.bianca.taskmanager_android.model.EmailActivity;
import com.example.bianca.taskmanager_android.model.HomeActivity;
import com.example.bianca.taskmanager_android.model.ListActivity;
import com.example.bianca.taskmanager_android.model.SignUpActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button login, signup;
    EditText email, password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.email = (EditText) findViewById(R.id.editEmail);
        this.password = (EditText) findViewById(R.id.editPassword);
        this.login = (Button)findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signUp);

        this.login.setOnClickListener(this);
        this.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser crtUser = firebaseAuth.getCurrentUser();
        if(crtUser != null){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }



    }

    @Override
    public void onClick(View view) {
        firebaseAuth.signInWithEmailAndPassword(this.email.getText().toString(), this.password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
