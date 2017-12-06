package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kmsoftware.myschoolapp.enums.SortBy;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.utilities.AdapterDataManipulation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseCustomAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> data;
    private int layoutResId;
    private AdapterDataManipulation<T> dataManipulation;
    public Context getContext() {
        return context;
    }

    public List<T> getData() {
        return data;
    }

    BaseCustomAdapter(Context context, int layoutResId, SortBy sortBy, AdapterDataManipulation<T> manipulation) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.dataManipulation = manipulation;
        setData(sortBy);
        notifyDataSetChanged();
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

    public void setData(SortBy sortBy) {
        this.data = dataManipulation.loadData();
        Collections.sort(data, getComparator(sortBy));
        notifyDataSetChanged();
    }

    public abstract Comparator<T> getComparator(SortBy sortBy);

    int getResourceId() {
        return layoutResId;
    }

}
