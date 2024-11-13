package eu.ase.ro.testcomplet1.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.testcomplet1.AddActivity;
import eu.ase.ro.testcomplet1.R;
import eu.ase.ro.testcomplet1.Subscription;


public class AboutFragment extends Fragment {
    FloatingActionButton fab;
    ListView lv;

    List<Subscription> subscriptionList=new ArrayList<>();
    public static final String list_KEY = "li_KEY";

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subscriptionList = getArguments().getParcelableArrayList(list_KEY);
        }


    }

    public static AboutFragment getInstance(List<Subscription> list) {
        AboutFragment aboutFragment = new AboutFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(list_KEY, (ArrayList<? extends Parcelable>) list);
        aboutFragment.setArguments(bundle);
        return aboutFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

          fab = requireActivity().findViewById(R.id.fab);
          fab.setVisibility(View.VISIBLE);
           lv = view.findViewById(R.id.about_lv);
          ArrayAdapter<Subscription> adapter = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, subscriptionList);
          lv.setAdapter(adapter);

        return view;
    }

    public void notifyAdapter(){
        ArrayAdapter<Subscription> adapter = (ArrayAdapter<Subscription>) lv.getAdapter();
        adapter.notifyDataSetChanged();
    }
}