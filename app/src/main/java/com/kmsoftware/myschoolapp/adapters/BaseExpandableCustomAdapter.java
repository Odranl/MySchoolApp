package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;

public abstract class BaseExpandableCustomAdapter<T, K> extends BaseExpandableListAdapter{

    protected Context context;
    protected List<T>  dataHeaders;
    protected HashMap<T, List<K>> childData;

    BaseExpandableCustomAdapter(Context context, List<T> dataHeaders, HashMap<T, List<K>> childData){
        this.context = context;
        this.dataHeaders = dataHeaders;
        this.childData = childData;
    }

    @Override
    public int getGroupCount() {
        return dataHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(dataHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return childData.get(dataHeaders.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(dataHeaders.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void refreshData(List<T> subjects, HashMap<T, List<K>> objectsMap) {

        dataHeaders = subjects;

        childData = objectsMap;

        this.notifyDataSetChanged();
    }
}
