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
import android.widget.Toast;

import com.example.bianca.taskmanager_android.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by BIANCA on 07.11.2017.
 */

public class ListActivity extends AppCompatActivity implements ValueEventListener {
    private static final String TAG = ListActivity.class.getName();
    private static final int UPDATE_CODE = 2;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    Boolean isManager;

    private static ActivityRepo repo;
    private ArrayAdapter<String> adapter;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_display);
        firebaseAuth = FirebaseAuth.getInstance();
        repo = new ActivityRepo(getApplicationContext());

        repo.clearAll();

        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference dbReference2 = firebaseDatabase.getReference("users");
        final DatabaseReference dbReference = dbReference2.child(EncodeString(firebaseAuth.getCurrentUser().getEmail())).child("tasks");
        dbReference.addValueEventListener(this);



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
                        System.out.println("i " + i);
                        newDetailActivity.putExtra("EXTRA_ID", repo.getAll().get(position).getId());
                        System.out.println("id " + repo.getAll().get(position).getId());
                        newDetailActivity.putExtra("EXTRA_TITLE", repo.getAll().get(position).getTitle());
                        newDetailActivity.putExtra("EXTRA_STATUS", repo.getAll().get(position).getStatus());
                        newDetailActivity.putExtra("EXTRA_DUEDATE", repo.getAll().get(position).getDueDate());
                        startActivity(newDetailActivity);

                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //adapter.remove(taskNames.get(position));

                        Activity task = new Activity(
                                repo.getAll().get(position).getTitle(),
                                repo.getAll().get(position).getStatus(),
                                repo.getAll().get(position).getDueDate(),
                                firebaseAuth.getCurrentUser().getEmail());
                        System.out.println("title " + task.getTitle());
                        repo.deleteActivity(task);
                        dbReference.child(task.getTitle()).removeValue();
                        finish();
                        //adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();

                return false;
            }
        });

        Button addButton = (Button) findViewById((R.id.add));

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dbReference2.child(EncodeString(firebaseAuth.getCurrentUser().getEmail())).child("manager").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        isManager = dataSnapshot.getValue(Boolean.class);
                        if(isManager){
                            startActivity(new Intent(getApplicationContext(), AddToOthersActivity.class));
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });



    }

    public static ActivityRepo getRepo() {
        return repo;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot taskSnapshot : dataSnapshot.getChildren()){
            Activity task = taskSnapshot.getValue(Activity.class);
            if(repo.getActivity(task.getId()) == null) {
                repo.insertActivity(task);
            }
            else{
                repo.updateActivity(task);
            }
        }

        adapter.clear();
        List<String> taskNames = new ArrayList<>();
        for(Activity t : this.repo.getAll()){
            taskNames.add(t.getTitle());
        }
        adapter.addAll(taskNames);
        adapter.notifyDataSetChanged();



    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
