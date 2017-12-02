package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kmsoftware.myschoolapp.enums.SortBy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseCustomAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> data;
    private int layoutResId;

    public Context getContext() {
        return context;
    }

    public List<T> getData() {
        return data;
    }

    BaseCustomAdapter(Context context, int layoutResId, List<T> data) {
        this.context = context;
        this.data = data;
        this.layoutResId = layoutResId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutResId, parent, false);
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

    public T getItem(int position) {
        return data.get(position);
    }

    public void sortData(Comparator<T> comparator) {
        Collections.sort(data, comparator);
    }

    public void setData(List<T> data, Comparator<T> comparator) {
        this.data = data;

        sortData(comparator);

        notifyDataSetChanged();
    }

    public abstract Comparator<T> getComparator(SortBy sortBy);

}
