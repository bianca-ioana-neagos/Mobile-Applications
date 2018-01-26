package com.example.bianca.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.bianca.myapplication.R;
import com.example.bianca.myapplication.adapter.MyAdapter;
import com.example.bianca.myapplication.model.Item;
import com.example.bianca.myapplication.utils.Manager;
import com.example.bianca.myapplication.utils.MyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BIANCA on 24.01.2018.
 */

public class ItemNewActivity extends AppCompatActivity implements MyCallback{
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editType)
    EditText editType;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_new);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.save)
    public void add(View view) {
        Manager manager = new Manager(getApplication());
        manager.save(progressBar, new Item(0,editName.getText().toString(),editType.getText().toString(),"Available",1), this);
        finish();
    }

    @Override
    public void add(Item item) {
        MyAdapter adapter = new MyAdapter(new ItemListActivity());
        adapter.addData(item);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(editName, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void clear() {
        finish();
    }
}
