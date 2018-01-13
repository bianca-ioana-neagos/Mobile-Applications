package com.example.bianca.taskmanager_android.model;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bianca.taskmanager_android.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by BIANCA on 10.01.2018.
 */

public class AddToOthersActivity extends AppCompatActivity {
    private static final String TAG = AddToOthersActivity.class.getName();

    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    ActivityRepo repo;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_form);

        firebaseAuth = FirebaseAuth.getInstance();

        Button addButton = (Button) findViewById(R.id.add);

        final EditText title = (EditText) findViewById(R.id.editTitle) ;
        final EditText status = (EditText) findViewById(R.id.editStatus) ;
        final EditText dueDate = (EditText) findViewById(R.id.editDate) ;
        final EditText to = (EditText) findViewById(R.id.editTo) ;

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

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                String titleE = title.getText().toString();
                String statusE = status.getText().toString();
                String dueDateE = dueDate.getText().toString();
                String toE = to.getText().toString();
                System.out.println("to      "+EncodeString(toE));
                System.out.println("me   "+ EncodeString(firebaseAuth.getCurrentUser().getEmail()));
                if(toE.isEmpty()){
                    Activity task = new Activity(titleE,statusE,dueDateE,firebaseAuth.getCurrentUser().getEmail());
                    dbReference = FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(EncodeString(firebaseAuth.getCurrentUser().getEmail()))
                            .child("tasks");
                    repo.insertActivity(task);
                    dbReference.child(titleE).setValue(task);
                    finish();

                    //toE=firebaseAuth.getCurrentUser().getEmail();

                }
                else {
                    Activity task = new Activity(titleE, statusE, dueDateE, toE);

                    dbReference = FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(EncodeString(toE))
                            .child("tasks");
                    repo.insertActivity(task);
                    dbReference.child(titleE).setValue(task);
                    finish();


                }

            }
        });
    }
    public String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
