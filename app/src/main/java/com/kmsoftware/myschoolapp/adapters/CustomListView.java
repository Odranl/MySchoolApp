package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.widget.ListView;

public class CustomListView extends ListView {
    public CustomListView(Context context) {
        super(context);
    }

    public BaseCustomAdapter getBaseCustomAdapter() {
        return (BaseCustomAdapter)this.getAdapter();
    }

    public void refreshData() {
        getBaseCustomAdapter().refreshData();
        invalidateViews();
    }
}
