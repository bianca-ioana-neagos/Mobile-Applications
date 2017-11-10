package com.example.bianca.taskmanager_android.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bianca.taskmanager_android.R;

/**
 * Created by BIANCA on 07.11.2017.
 */

public class EmailActivity extends AppCompatActivity {
    private static final String Tag = EmailActivity.class.getName();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_form);

        Button sendButton = (Button) findViewById(R.id.send);
        final EditText addressEmail = (EditText) findViewById(R.id.editTo) ;
        final EditText subjectEmail = (EditText) findViewById(R.id.editSubject) ;
        final EditText textEmail = (EditText) findViewById(R.id.editText) ;

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String subjectEmailToSend = subjectEmail.getText().toString();
                String addressMailToSend = addressEmail.getText().toString();
                String textEmailToSend = textEmail.getText().toString();

                sendEmail(subjectEmailToSend,addressMailToSend,textEmailToSend);
            }
        });
    }

    public void sendEmail(String subject, String address, String text){
        String[] TO = {address};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose app"));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EmailActivity.this, "There is no email app installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
