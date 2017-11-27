package com.kmsoftware.myschoolapp.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.kmsoftware.myschoolapp.adapters.BaseCustomAdapter;

public class CustomListView extends ListView {
    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseCustomAdapter getBaseCustomAdapter() {
        return (BaseCustomAdapter)this.getAdapter();
    }

    public void refreshData() {
        getBaseCustomAdapter().refreshData();
        invalidateViews();
    }
}
