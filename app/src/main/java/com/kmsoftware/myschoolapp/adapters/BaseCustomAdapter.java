package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class BaseCustomAdapter<T> extends BaseAdapter {

    protected Context context;
    protected ArrayList<T> data;
    private int rowLayoutId;

    protected abstract Comparator<T> getComparator();

    BaseCustomAdapter(Context context, int rowLayoutId) {
        this.context = context;
        this.rowLayoutId = rowLayoutId;

        refreshData();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //noinspection ConstantConditions
            convertView = inflater.inflate(rowLayoutId, parent, false);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public ArrayList<T> getData() {
        return data;
    }

    public T getItem(int position) {
        return data.get(position);
    }

    public abstract ArrayList<T> loadData(ArrayList<T> data);

    private void sortData() {
        Collections.sort(data, getComparator());
    }

    public void refreshData() {
        data = loadData(data);
        sortData();
    }
}
