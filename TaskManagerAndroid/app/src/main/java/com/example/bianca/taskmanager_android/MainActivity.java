package com.example.bianca.taskmanager_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.bianca.taskmanager_android.model.Activity;
import com.example.bianca.taskmanager_android.model.ActivityRepo;
import com.example.bianca.taskmanager_android.model.EmailActivity;
import com.example.bianca.taskmanager_android.model.ListActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private float[] yData = {20,30,50};
    private String[] xData = {"To Do", "In Progress", "Done"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
