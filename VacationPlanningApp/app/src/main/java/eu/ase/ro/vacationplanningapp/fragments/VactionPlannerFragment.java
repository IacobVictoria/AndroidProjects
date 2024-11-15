package eu.ase.ro.vacationplanningapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.vacationplanningapp.R;
import eu.ase.ro.vacationplanningapp.domain.ListViewAdapter;
import eu.ase.ro.vacationplanningapp.domain.Vacation;


public class VactionPlannerFragment extends Fragment {

List<Vacation> vacationList;
    ListView lv;
private static final String KEY_ARRAY="key-array";

    public VactionPlannerFragment() {
        // Required empty public constructor
    }

public static VactionPlannerFragment getInstance(List<Vacation> vacations){
        VactionPlannerFragment fragment = new VactionPlannerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_ARRAY, (ArrayList<? extends Parcelable>) vacations);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(getArguments()!=null){
           vacationList = getArguments().getParcelableArrayList(KEY_ARRAY);
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vaction_planner, container, false);
        ListViewAdapter adapter = new ListViewAdapter(getContext().getApplicationContext(),R.layout.item_list_view,vacationList,getLayoutInflater());
         lv = view.findViewById(R.id.vac_lv);
        lv.setAdapter(adapter);
        return view;
    }
    public void notifyAdapter(){
        ListViewAdapter adapter = (ListViewAdapter) lv.getAdapter();
        adapter.notifyDataSetChanged();
    }

}