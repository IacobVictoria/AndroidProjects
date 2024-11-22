package eu.ase.ro.quiztema3.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.quiztema3.R;
import eu.ase.ro.quiztema3.domain.Question;
import eu.ase.ro.quiztema3.domain.ViewPagerAdapter;


public class HomeFragment extends Fragment {
    List<Question> questionList = new ArrayList<>();
    private static final String KEY_LIST = "Key_List";
    FloatingActionButton floatingActionButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(List<Question> list) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LIST, (ArrayList<? extends Parcelable>) list);
        homeFragment.setArguments(bundle);
        return homeFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionList = getArguments().getParcelableArrayList(KEY_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.iacob_maria_victoria_view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(questionList,getChildFragmentManager());
        viewPager2.setAdapter(viewPagerAdapter);
        floatingActionButton = getActivity().findViewById(R.id.iacob_maria_victoria_main_fab);
        floatingActionButton.setVisibility(View.GONE);
        return view;
    }
}