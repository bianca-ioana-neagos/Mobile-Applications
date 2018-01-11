package com.example.bianca.taskmanager_android.model;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bianca.taskmanager_android.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BIANCA on 07.11.2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getName();
    ActivityRepo repo;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_display);

        Button saveButton = (Button) findViewById(R.id.saveChanges);

        final EditText title = (EditText) findViewById(R.id.editTitle);
        final EditText status = (EditText) findViewById(R.id.editStatus);
        final EditText dueDate = (EditText) findViewById(R.id.editDate);



        final Intent intent = getIntent();
        final int id = intent.getIntExtra("EXTRA_ID",0);
        title.setText(intent.getStringExtra("EXTRA_TITLE"));
        status.setText(intent.getStringExtra("EXTRA_STATUS"));
        dueDate.setText(this.getIntent().getStringExtra("EXTRA_DUEDATE"));
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
        //dbRef = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid()).child("tasks");


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(TAG, "save");
                String titleE = title.getText().toString();
                String statusE = status.getText().toString();
                String dueDateE = dueDate.getText().toString();
                Activity task = new Activity(id,titleE,statusE,dueDateE,firebaseAuth.getCurrentUser().getEmail());
                dbRef = FirebaseDatabase.getInstance().getReference("users").child(EncodeString(firebaseAuth.getCurrentUser().getEmail())).child("tasks");

                repo.updateActivity(task);

                dbRef.child(titleE).setValue(task);
                finish();

            }

        });


    }
    public String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
