package com.example.bianca.taskmanager_android.model;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bianca.taskmanager_android.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by BIANCA on 30.11.2017.
 */

public class AddActivity extends AppCompatActivity {
    private static final String TAG = AddActivity.class.getName();

    DatabaseReference dbRef;
    FirebaseAuth firebaseAuth;
    ActivityRepo repo;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_form);

        Button addButton = (Button) findViewById(R.id.add);

        final EditText title = (EditText) findViewById(R.id.editTitle) ;
        final EditText status = (EditText) findViewById(R.id.editStatus) ;
        final EditText dueDate = (EditText) findViewById(R.id.editDate) ;

        dueDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Date dialog = new Date(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });

        repo = ListActivity.getRepo();
        firebaseAuth = FirebaseAuth.getInstance();


        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String titleE = title.getText().toString();
                String statusE = status.getText().toString();
                String dueDateE = dueDate.getText().toString();
                Activity task = new Activity(titleE,statusE,dueDateE,firebaseAuth.getCurrentUser().getEmail());
                dbRef = FirebaseDatabase.getInstance().getReference("users").child(EncodeString(firebaseAuth.getCurrentUser().getEmail())).child("tasks");
                repo.insertActivity(task);
                dbRef.child(titleE).setValue(task);
                finish();

            }
        });
    }
    public String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
