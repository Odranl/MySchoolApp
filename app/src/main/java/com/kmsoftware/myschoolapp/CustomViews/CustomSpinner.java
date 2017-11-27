package com.kmsoftware.myschoolapp.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.adapters.BaseCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.LessonsCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.MarksCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.SubjectsCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.TasksCustomAdapter;

public class CustomSpinner extends AppCompatSpinner {

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSpinner,0, 0);

        int typeAdapterName = a.getInteger(R.styleable.CustomSpinner_dataType, 0);
        BaseCustomAdapter adapter;
        switch (typeAdapterName) {
            case 0:
                adapter = new SubjectsCustomAdapter(context);
                break;
            case 2:
                adapter = new MarksCustomAdapter(context);
                break;
            case 3:
                adapter = new TasksCustomAdapter(context);
                break;
            default:
                adapter = new SubjectsCustomAdapter(context);
                break;
        }

        setAdapter(adapter);

        a.recycle();
    }

    public void refreshData() {
        ((BaseCustomAdapter)this.getAdapter()).refreshData();
    }
}
