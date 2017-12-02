package main.java.com.kmsoftware.myschoolapp;

import com.sun.javafx.tk.Toolkit.Task;
import com.sun.webkit.PopupMenu;

import javafx.scene.control.ListView;

public class ListGenerator<T> {
    private CustomListView listView;
    private BaseCustomAdapter adapter;
    private PopupMenu popupMenu;
    private AlertDialog dialog;

    public PopupMenu getPopupMenu() {
        return popupMenu;
    }

    public AlertDialog getAlertDialog() {
        return dialog;
    }

    public BaseCustomAdapter getAdapter() {
        return adapter;
    }

    public ListGenerator(Class objectClass, List<T> list, Dialog.OnClickListener onDialogClickListener ) {
        if (objectClass == Subject.class) {
            adapter = new SubjectCustomAdapter();
        } else if (objectClass == Mark.class) {
            adapter = new MarkCustomAdapter();
        } else if (objectClass == Task.class) {
            adapter = new TaskCustomAdapter();
        }
    }
}