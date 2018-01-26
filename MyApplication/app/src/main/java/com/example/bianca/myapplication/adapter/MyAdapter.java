package com.example.bianca.myapplication.adapter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bianca.myapplication.activity.ItemListActivity;
import com.example.bianca.myapplication.activity.ItemListCustomerActivity;
import com.example.bianca.myapplication.model.Item;
import com.example.bianca.myapplication.R;
import com.example.bianca.myapplication.utils.Manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BIANCA on 24.01.2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Item> items;
    ItemListActivity caller;
    Application application;
    ItemListCustomerActivity callerC;


    public MyAdapter(ItemListActivity caller) {
        items = new ArrayList<>();
        this.application = caller.getApplication();
        this.caller = caller;
    }

    public MyAdapter(ItemListCustomerActivity caller) {
        items = new ArrayList<>();
        this.application = caller.getApplication();
        this.callerC = caller;
    }

    public void setData(List<Item> i) {
        items = i;
        notifyDataSetChanged();
    }

    public void addData(Item item){
        items.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = items.get(position);
        //holder.mIdView.setText(String.valueOf(items.get(position).getId()));
        holder.mNameView.setText(items.get(position).getName());
        holder.mQView.setText(String.valueOf(items.get(position).getQuantity()));
        holder.mTypeView.setText(items.get(position).getType());
        holder.mStatusView.setText(items.get(position).getStatus());
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager manager = new Manager(application);
                manager.buy(items.get(position), caller);


            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager manager = new Manager(application);
                manager.delete(items.get(position), caller);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mQView;
        public final TextView mTypeView;
        public final TextView mStatusView;
        public final Button buy;
        public final Button delete;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id_text);
            mNameView = (TextView) view.findViewById(R.id.name);
            mQView = (TextView) view.findViewById(R.id.q);
            mTypeView = (TextView) view.findViewById(R.id.type);
            mStatusView = (TextView) view.findViewById(R.id.status);
            buy = (Button) view.findViewById(R.id.buy);
            delete = (Button) view.findViewById(R.id.delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
