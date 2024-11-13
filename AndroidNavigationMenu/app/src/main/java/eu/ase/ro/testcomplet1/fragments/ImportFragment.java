package eu.ase.ro.testcomplet1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.testcomplet1.ListViewAdapter;
import eu.ase.ro.testcomplet1.R;


public class ImportFragment extends Fragment {

    private List<String> activities;
    private static final String KEY1 = "jey";

    public ImportFragment() {
        // Required empty public constructor
    }

    public static ImportFragment getInstance(List<String> activities) {
        ImportFragment fragment = new ImportFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY1, (ArrayList<String>) activities);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activities = getArguments().getStringArrayList(KEY1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_import, container, false);
        ListView lv = view.findViewById(R.id.import_lv);
        ListViewAdapter adapter = new ListViewAdapter(getContext().getApplicationContext(), R.layout.lv_row, activities, getLayoutInflater());
        lv.setAdapter(adapter);
        return view;
    }
}