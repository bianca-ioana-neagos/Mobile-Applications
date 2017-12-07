package com.example.bianca.taskmanager_android.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bianca.taskmanager_android.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by BIANCA on 07.11.2017.
 */

public class ListActivity extends AppCompatActivity {
    private static final String TAG = ListActivity.class.getName();
    private static final int UPDATE_CODE = 2;

    private ActivityRepo repo;
    private ArrayAdapter<String> adapter;

    List<Activity> tasks =new ArrayList<>();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_display);
        repo = new ActivityRepo(getApplicationContext());

        tasks = repo.getAll();

        final ListView taskList = (ListView) findViewById(R.id.listView);

        final List<String> taskNames = new ArrayList<>();
        for(Activity t : repo.getAll()){
            taskNames.add(t.getTitle());
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, taskNames);

        taskList.setAdapter(adapter);


        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
                dialog.setTitle("Delete&Edit");
                dialog.setMessage("What do you want to do?");
                dialog.setNegativeButton("Edit", new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent newDetailActivity = new Intent(ListActivity.this, DetailsActivity.class);
                        System.out.println("position " + position);
                        int idPos=repo.getActivityByPosition(position);
                        System.out.println("id from position " + idPos);
                        newDetailActivity.putExtra("ActivityOnPosition", repo.getActivity(idPos));
                        startActivityForResult(newDetailActivity,UPDATE_CODE);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.remove(taskNames.get(position));
                        repo.deleteActivity(repo.getActivityByPosition(position));
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();

                return false;
            }
        });

        Button addButton = (Button) findViewById((R.id.add));

        addButton.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View view) {
                //final Context context = view.getRootView().getContext();
                Activity activity = new Activity("","","");
                Intent newAddActivity = new Intent(ListActivity.this, AddActivity.class);
                newAddActivity.putExtra("ActivityOnPosition", activity);
                startActivityForResult(newAddActivity,UPDATE_CODE);
                //repo.insertActivity(activity);
                //tasks.add(activity);
            }
        });

//        setContentView(R.layout.activity_main);
////
//        float toDo = repo.getAllByStatus("To Do").size();
//        float inProgr = repo.getAllByStatus("In Progress").size();
//        float done = repo.getAllByStatus("Done").size();
//
//        float toDoP = (toDo *100)/3;
//        float inProgrP =(inProgr *100)/3;
//        float doneP=(done *100)/3;
//
//        float[] yData = {toDoP,inProgrP,doneP};
//        String[] xData = {"To Do", "In Progress", "Done"};
//
//        List<PieEntry> yVals1 = new ArrayList<>();
//        for(int i=0;i<yData.length;i++){
//            yVals1.add(new PieEntry(yData[i],xData[i]));
//        }
//
//        PieDataSet dataSet = new PieDataSet(yVals1,"");
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        PieData data = new PieData(dataSet);
//
//        PieChart chart = (PieChart) findViewById(R.id.showChart);
//        chart.setData(data);
//        chart.animateY(1000);
//        chart.setUsePercentValues(true);
//        chart.getDescription().setEnabled(false);
//        chart.invalidate();
//
//        Legend l =chart.getLegend();
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG,"agergererhaeheheheaga");
        Log.i(TAG, String.valueOf(tasks.size()));
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE && resultCode == RESULT_OK) {
            Activity activity = (Activity) data.getSerializableExtra("ActivityOnPositionReturn");
            System.out.println(activity.getTitle());
            if(repo.getActivity(activity.getId()) != null)
                repo.updateActivity(activity);
            else
                repo.insertActivity(activity);

            adapter.clear();
            List<String> taskNames = new ArrayList<>();
            for(Activity t : this.repo.getAll()){
                taskNames.add(t.getTitle());
            }
            adapter.addAll(taskNames);
            adapter.notifyDataSetChanged();

        }
    }

    public ActivityRepo getRepo() {
        return repo;
    }
}
