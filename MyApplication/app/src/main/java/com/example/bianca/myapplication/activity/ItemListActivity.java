package com.example.bianca.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bianca.myapplication.R;
import com.example.bianca.myapplication.adapter.MyAdapter;
import com.example.bianca.myapplication.model.Item;
import com.example.bianca.myapplication.utils.Manager;
import com.example.bianca.myapplication.utils.MyCallback;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.TimeInterval;
import timber.log.Timber;

import static com.example.bianca.myapplication.App.buyRequestsNr;

public class ItemListActivity extends AppCompatActivity implements MyCallback {
    private MyAdapter adapter;

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.add)
    FloatingActionButton add;
//    @BindView(R.id.buyRequests)
//    public
//    TextView buyRequests;

    private View recyclerView;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ButterKnife.bind(this);
        manager = new Manager(getApplication());

        //buyRequests.setVisibility(manager.networkConnectivity(getApplicationContext()) ? View.GONE : View.VISIBLE );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        loadItems();

//        if (manager.networkConnectivity(this)) {
//
//            Observable.interval(10, TimeUnit.SECONDS)
//                    .timeInterval()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<TimeInterval<Long>>() {
//                        @Override
//                        public void onCompleted() {
//                            Toast.makeText(ItemListActivity.this, "Refresh data complete", Toast.LENGTH_SHORT).show();
//                            Timber.v("Refresh data complete");
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Toast.makeText(ItemListActivity.this, "Error refresh data", Toast.LENGTH_SHORT).show();
//                            Timber.e(e, "Error refresh data");
//                            unsubscribe();
//                        }
//
//                        @Override
//                        public void onNext(TimeInterval<Long> longTimeInterval) {
//                            Toast.makeText(ItemListActivity.this, "Refresh data", Toast.LENGTH_SHORT).show();
//                            Timber.v("Refresh data");
//                            if (!loadItems())
//                                unsubscribe();
//                        }
//                    });
//        }
    }

    private boolean loadItems() {
        boolean conn = manager.networkConnectivity(getApplicationContext());
        if(conn){
            add.setVisibility(View.VISIBLE);
        }else{
            add.setVisibility(View.GONE);
            showError("No internet connection!");
        }
        manager.loadItems(progressBar, this);
        return conn;
    }

    @Override
    public void add(Item item) {
        adapter.addData(item);
    }

    @Override
    public void showError(String s) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(recyclerView, s, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadItems();
                    }
                }).show();
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new MyAdapter(this);
//        ((App) getApplication()).db.getItemDao().getItems().observe(this, new Observer<List<Item>>() {
//            @Override
//            public void onChanged(@Nullable List<Item> items) {
//                adapter.setData(items);
//            }
//        });
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.add)
    public void onAddClick(View view) {
        Intent intent = new Intent(getApplication(), ItemNewActivity.class);
        startActivityForResult(intent, 10000);
    }

    @OnClick(R.id.refresh)
    public void onRefreshClick(View view) {

        manager.loadItems(progressBar,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Back in main activity", Toast.LENGTH_SHORT);
        Timber.d("Back in main activity");
        loadItems();
    }
}
