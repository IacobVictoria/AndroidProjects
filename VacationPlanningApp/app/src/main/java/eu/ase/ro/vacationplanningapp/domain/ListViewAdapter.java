package eu.ase.ro.vacationplanningapp.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.vacationplanningapp.R;

public class ListViewAdapter extends ArrayAdapter<Vacation> {
    Context context;
    LayoutInflater layoutInflater;
    List<Vacation> vacationList;
    int resource;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Vacation> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.vacationList = objects;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      View view = layoutInflater.inflate(resource,parent,false);
      Vacation vacation = vacationList.get(position);
        TextView tvDate = view.findViewById(R.id.date);
        TextView tvLocation = view.findViewById(R.id.location);
        TextView tvActivities = view.findViewById(R.id.activities);
        TextView tvType = view.findViewById(R.id.type);
        tvType.setText(vacation.getType().toString());
        tvDate.setText(DateConverter.toString(vacation.getDate()));
        tvActivities.setText(vacation.getActivities().toString());
        tvLocation.setText(vacation.getLocation());
      return view;
    }
}
