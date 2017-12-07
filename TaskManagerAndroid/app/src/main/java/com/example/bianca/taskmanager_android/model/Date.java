package com.example.bianca.taskmanager_android.model;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.List;

/**
 * Created by BIANCA on 01.12.2017.
 */

@SuppressLint("ValidFragment")
public class Date extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText dueDate;

    public Date(View view){
        dueDate=(EditText) view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year,month,day);
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = day+"-"+month+"-"+year;
        dueDate.setText(date);
    }
}
