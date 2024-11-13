package eu.ase.ro.testcomplet1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String> {
    Context context;
    int resource;
    List<String> activities;
    LayoutInflater layoutInflater;


    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.activities = objects;
        this.layoutInflater = inflater;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String activity = activities.get(position);
        View view = layoutInflater.inflate(resource, parent, false);
        TextView textView = view.findViewById(R.id.tv_lv2);
        textView.setText(activity);
        return view;

    }
}
