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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BIANCA on 07.11.2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_display);

        Button saveButton = (Button) findViewById(R.id.saveChanges);

        final EditText title = (EditText) findViewById(R.id.editTitle);
        final EditText status = (EditText) findViewById(R.id.editStatus);
        final EditText dueDate = (EditText) findViewById(R.id.editDate);


        final Activity task;
        final Intent intent = getIntent();
        task = (Activity) intent.getSerializableExtra("ActivityOnPosition");


        title.setText(task.getTitle());
        status.setText(task.getStatus());
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
       dueDate.setText(task.getDueDate());

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(TAG, "save");
                final Context context = view.getRootView().getContext();

                final EditText title = (EditText) findViewById(R.id.editTitle);
                final EditText status = (EditText) findViewById(R.id.editStatus);
                final EditText dueDate = (EditText) findViewById(R.id.editDate);


                task.setId(task.getId());
                task.setTitle(title.getText().toString());
                task.setStatus(status.getText().toString());
                task.setDueDate(dueDate.getText().toString());

                Intent intent1 = new Intent();
                intent1.putExtra("ActivityOnPositionReturn", (Serializable) task);
                setResult(RESULT_OK, intent1);
                finish();
                }

        });


    }
}
