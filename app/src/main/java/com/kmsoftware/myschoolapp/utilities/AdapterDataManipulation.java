package com.kmsoftware.myschoolapp.utilities;

import java.util.List;

// This interface contains the function used to load data into the adapters,
// this way the view can be reset and the same code will be called to reload
// the data.
public interface AdapterDataManipulation<T> {
    List<T> loadData();
}
