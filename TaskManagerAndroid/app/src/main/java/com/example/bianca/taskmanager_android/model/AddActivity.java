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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BIANCA on 30.11.2017.
 */

public class AddActivity extends AppCompatActivity {
    private static final String TAG = AddActivity.class.getName();


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

        final Activity task;
        final Intent intent = getIntent();
        task = (Activity) intent.getSerializableExtra("ActivityOnPosition");
       // final int position = tasks.size() ;



        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Context context = view.getRootView().getContext();

//                final EditText title = (EditText) findViewById(R.id.editTitle) ;
//                final EditText status = (EditText) findViewById(R.id.editStatus) ;
//                final EditText dueDate = (EditText) findViewById(R.id.editDate) ;
//
//                dueDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View view, boolean b) {
//                        if(b){
//                            Date dialog = new Date(view);
//                            FragmentTransaction ft = getFragmentManager().beginTransaction();
//                            dialog.show(ft, "DatePicker");
//                        }
//                    }
//                });

                String titleE = title.getText().toString();
                String statusE = status.getText().toString();
                String dueDateE = dueDate.getText().toString();
               // Activity task = new Activity();
                //task.setId(task.getId());
                //System.out.println(task.getId());
                task.setTitle(titleE);
                task.setStatus(statusE);
                task.setDueDate(dueDateE);



                Intent intent1 = new Intent();
                intent1.putExtra("ActivityOnPositionReturn", (Serializable) task);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }
}
