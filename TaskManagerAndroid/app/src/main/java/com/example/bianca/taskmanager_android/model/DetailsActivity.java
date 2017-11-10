package com.example.bianca.taskmanager_android.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bianca.taskmanager_android.R;

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

        final ArrayList<Activity> tasks;
        Intent intent = getIntent();
        tasks = (ArrayList<Activity>) intent.getSerializableExtra("ActivityList");
        final int position = intent.getIntExtra("PositionInActivityList", 0);

        title.setText(tasks.get(position).getTitle());
        status.setText(tasks.get(position).getStatus());
        dueDate.setText(tasks.get(position).getDueDate());

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(TAG, "agergerga");
                final EditText title = (EditText) findViewById(R.id.editTitle);
                final EditText status = (EditText) findViewById(R.id.editStatus);
                final EditText dueDate = (EditText) findViewById(R.id.editDate);

                tasks.get(position).setTitle(title.getText().toString());
                tasks.get(position).setStatus(status.getText().toString());
                tasks.get(position).setDueDate(dueDate.getText().toString());

                Intent intent1 = new Intent();
                intent1.putExtra("POSITION", position);
                intent1.putExtra("ITEM", tasks.get(position));
                setResult(RESULT_OK, intent1);
                finish();
                }

        });
    }
}
