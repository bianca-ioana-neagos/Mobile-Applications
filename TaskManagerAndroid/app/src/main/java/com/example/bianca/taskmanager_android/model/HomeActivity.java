package com.example.bianca.taskmanager_android.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.bianca.taskmanager_android.MainActivity;
import com.example.bianca.taskmanager_android.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BIANCA on 10.01.2018.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getName();

    private float[] yData = {20,30,50};
    private String[] xData = {"To Do", "In Progress", "Done"};

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();

        try {
            setChart();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        Button send = (Button) findViewById(R.id.sendEmail);
        send.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View parent) {
                Intent newEmailActivity = new Intent(HomeActivity.this, EmailActivity.class);
                startActivity(newEmailActivity);
            }
        });
        Button show = (Button) findViewById(R.id.showTasks);
        show.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View parent) {
                Intent newListActivity = new Intent(HomeActivity.this, ListActivity.class);
                startActivity(newListActivity);
            }
        });

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
            }
        });


    }
    private void setChart() throws IllegalAccessException, InstantiationException {
        //ListActivity listActivityClass = ListActivity.class.newInstance();
        ActivityRepo repo = new ActivityRepo(getApplicationContext());

        float toDo = repo.getAllByStatus("To Do").size();
        float inProgr = repo.getAllByStatus("In Progress").size();
        float done = repo.getAllByStatus("Done").size();

        float toDoP = (toDo *100)/3;
        float inProgrP =(inProgr *100)/3;
        float doneP=(done *100)/3;

        float[] yData = {toDo,inProgr,done};
        String[] xData = {"To Do", "In Progress", "Done"};

        List<PieEntry> yVals1 = new ArrayList<>();
        for(int i=0;i<yData.length;i++){
            yVals1.add(new PieEntry(yData[i],xData[i]));
        }

        PieDataSet dataSet = new PieDataSet(yVals1,"");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.showChart);
        chart.notifyDataSetChanged();
        chart.setData(data);
        chart.animateY(1000);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.invalidate();

        Legend l =chart.getLegend();
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


    }
}