package com.example.bianca.myapplication.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bianca.myapplication.App;
import com.example.bianca.myapplication.activity.ItemListActivity;
import com.example.bianca.myapplication.model.Item;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.bianca.myapplication.App.buyRequestsNr;

/**
 * Created by BIANCA on 24.01.2018.
 */

public class Manager {
    private ItemService service;
    private Realm realm = Realm.getDefaultInstance();

    private App app;
    //Thread buyReq;

    public Manager(Application application){
        this.app = (App) application;
        this.service = ServiceFactory.createRetrofitService(ItemService.class, ItemService.SERVICE_ENDPOINT);
    }


    public boolean networkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        System.out.println("Connected to:  "+ networkInfo);
        return networkInfo != null && networkInfo.isConnected();
    }

    public void loadItems(final ProgressBar progressBar, final MyCallback callback){
        progressBar.setVisibility(View.VISIBLE);
        service.getAllItems()
                .timeout(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>(){

                    @Override
                    public void onCompleted() {
                        Timber.v("Service completed");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error while loading the events");
                        callback.clear();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Item> result = realm.where(Item.class).findAll();
                                List<Item> items = realm.copyFromRealm(result);
                                for (Item item : items) {
                                    callback.add(item);
                                }
                            }
                        });
                        callback.showError("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Item> items) {
                        callback.clear();
                        for (Item item : items) {
                            callback.add(item);
                        }
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(items);
                                Timber.v("Data persisted");
                            }
                        });
                    }
                });
    }

    public void loadItemsCustomer(final ProgressBar progressBar, final MyCallback callback){
        progressBar.setVisibility(View.VISIBLE);
        service.getItems()
                .timeout(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>(){

                    @Override
                    public void onCompleted() {
                        Timber.v("Service completed");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error while loading the events");
                        callback.clear();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Item> result = realm.where(Item.class).findAll();
                                List<Item> items = realm.copyFromRealm(result);
                                for (Item item : items) {
                                    callback.add(item);
                                }
                            }
                        });
                        callback.showError("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Item> items) {
                        callback.clear();
                        for (Item item : items) {
                            callback.add(item);
                        }
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(items);
                                Timber.v("Data persisted");
                            }
                        });
                    }
                });
    }

    public void save(final ProgressBar progressBar, final Item item, final MyCallback callback){
        progressBar.setVisibility(View.VISIBLE);
        service.addItem(item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        //addDataLocally(item);
                        Timber.v("Service completed");
                        //callback.clear();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error while persisting an item");
                        callback.showError("Not able to connect to the server, will not persist!");
                    }

                    @Override
                    public void onNext(Item item) {
                        Timber.v("Data persisted");
                    }
                });
    }

//    private void addDataLocally(final Item item) {
//        new Thread(new Runnable() {
//            public void run() {
//                app.db.getItemDao().addItem(item);
//            }
//        }).start();
//    }

    public void buy(Item item, final MyCallback callback){
//        if (networkConnectivity(((ItemListActivity) callback).getApplicationContext())) {
            service.buyItem(item)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Item>() {
                        @Override
                        public void onCompleted() {
                            Timber.v("Service completed");
                            callback.clear();
                            //progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (networkConnectivity(((ItemListActivity) callback).getApplicationContext())) {
                                Timber.e(e, "Error while buying an item");
                                callback.showError("Item already purchased!");
                            }else{
                                Timber.e(e, "Error while buying an item");
                                callback.showError("Cannot buy offline!");
                        }
                        }

                        @Override
                        public void onNext(Item item) {
                            Timber.v("Data persisted");
                        }
                    });

    }

//    public void buyAllAndRefresh(final ProgressBar progressBar, final MyCallback callback) {
//        buyReq = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Item> items = app.db.getItemDao().getBuyRequests();
//                for (Item i : items) {
//                    Manager.this.buy(i, callback);
//                    buyRequestsNr--;
//                }
//
//            }
//        });
//        loadItems(progressBar, callback);
//        buyReq.start();
//
//    }


    public void delete(Item item, final MyCallback callback){
//        if (networkConnectivity(((ItemListActivity) callback).getApplicationContext())) {
        service.deleteItem(item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        Timber.v("Service completed");
                        callback.clear();
                        //progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (networkConnectivity(((ItemListActivity) callback).getApplicationContext())) {
                            Timber.e(e, "Error while deleting an item");
                            callback.showError("Item already deleted!");
                        }else{
                            Timber.e(e, "Error while deleting an item");
                            callback.showError("Cannot delete offline!");
                        }
                    }

                    @Override
                    public void onNext(Item item) {
                        Timber.v("Data persisted");
                    }
                });

    }
}
