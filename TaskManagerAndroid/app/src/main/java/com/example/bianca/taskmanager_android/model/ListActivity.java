package com.example.bianca.taskmanager_android.model;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bianca.taskmanager_android.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BIANCA on 07.11.2017.
 */

public class ListActivity extends AppCompatActivity {
    private static final String TAG = ListActivity.class.getName();
    private static final int UPDATE_CODE = 2;

    final ArrayList<Activity> tasks = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public void tasksData(){
        Activity task1 = new Activity("Meeting", "To Do", "11.12.2017");
        tasks.add(task1);

        Activity task2 = new Activity("Get grandma from airport", "To Do",  "11.12.2017");
        tasks.add(task2);

        Activity task3 = new Activity("Walk the dog", "To Do",  "11.12.2017");
        tasks.add(task3);
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_display);

        ListView taskList = (ListView) findViewById(R.id.listView);
        tasksData();

        List<String> taskNames = new ArrayList<>();
        for(Activity t : tasks){
            taskNames.add(t.getTitle());
        }


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, taskNames);

        taskList.setAdapter(adapter);

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent newDetailActivity = new Intent(ListActivity.this, DetailsActivity.class);
                newDetailActivity.putExtra("ActivityList", (Serializable) tasks);
                newDetailActivity.putExtra("PositionInActivityList", position);
                startActivityForResult(newDetailActivity, UPDATE_CODE);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG,"agergererhaeheheheaga");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE && resultCode == RESULT_OK) {
            int position = data.getIntExtra("POSITION", -1);
            Activity activity = (Activity) data.getSerializableExtra("ITEM");
            tasks.set(position, activity);
            adapter.clear();
            List<String> taskNames = new ArrayList<>();
            for(Activity t : tasks){
                taskNames.add(t.getTitle());
            }
            adapter.addAll(taskNames);
            adapter.notifyDataSetChanged();
        }
    }

}
