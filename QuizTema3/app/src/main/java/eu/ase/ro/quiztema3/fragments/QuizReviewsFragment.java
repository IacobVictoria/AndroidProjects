package eu.ase.ro.quiztema3.fragments;

import android.os.Bundle;

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

import eu.ase.ro.quiztema3.R;
import eu.ase.ro.quiztema3.domain.ListViewAdapter;
import eu.ase.ro.quiztema3.domain.QuizReview;


public class QuizReviewsFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private List<QuizReview> quizReviewList;
    private ListView listView;
    private static final String ARRAY_KEY = "array_key";

    public QuizReviewsFragment() {
        // Required empty public constructor
    }

    public static QuizReviewsFragment getInstance(List<QuizReview> list) {
        QuizReviewsFragment quizReviewsFragment = new QuizReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARRAY_KEY, (ArrayList<? extends Parcelable>) list);
        quizReviewsFragment.setArguments(bundle);
        return quizReviewsFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizReviewList = getArguments().getParcelableArrayList(ARRAY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz_reviews, container, false);
        if (getContext() != null) {
            floatingActionButton = getActivity().findViewById(R.id.iacob_maria_victoria_main_fab);
            floatingActionButton.setVisibility(View.VISIBLE);
            listView = view.findViewById(R.id.iacob_maria_victoria_reviews_lv);
            ListViewAdapter adapter = new ListViewAdapter(getContext().getApplicationContext(), R.layout.item_list_view, quizReviewList, getLayoutInflater());
            listView.setAdapter(adapter);
        }
        return view;
    }

    public void notifyAdapter() {
        ListViewAdapter adapter = (ListViewAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();
    }

}