package eu.ase.ro.testcomplet1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import eu.ase.ro.testcomplet1.R;

public class HomeFragment extends Fragment {

    private FloatingActionButton fab;
    private String dataExtra;
    public static final String Key = "key";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(String data) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Key, data);
        homeFragment.setArguments(bundle);
        return  homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataExtra = getArguments().getString(Key);

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fab = requireActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        TextView tv = view.findViewById(R.id.tv_home);
        tv.setText(dataExtra);
        return  view;
    }
}