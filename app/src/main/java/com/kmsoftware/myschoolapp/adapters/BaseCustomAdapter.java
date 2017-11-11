package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class BaseCustomAdapter<T> extends BaseAdapter {

    protected Context context;
    protected ArrayList<T> data;

    public BaseCustomAdapter(Context context, ArrayList<T> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void refreshData(ArrayList<T> newData) {
        data.clear();
        data = newData;
        this.notifyDataSetChanged();
    }

    public ArrayList<T> getData() {
        return data;
    }
}
