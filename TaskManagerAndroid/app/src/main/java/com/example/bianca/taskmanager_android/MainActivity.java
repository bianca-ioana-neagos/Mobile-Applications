package com.example.bianca.taskmanager_android;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bianca.taskmanager_android.model.EmailActivity;
import com.example.bianca.taskmanager_android.model.ListActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = (Button) findViewById(R.id.sendEmail);
        send.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View parent) {
                Intent newEmailActivity = new Intent(MainActivity.this, EmailActivity.class);
                startActivity(newEmailActivity);
            }
        });
        Button show = (Button) findViewById(R.id.showTasks);
        show.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View parent) {
                Intent newListActivity = new Intent(MainActivity.this, ListActivity.class);
                startActivity(newListActivity);
            }
        });


    }
}
