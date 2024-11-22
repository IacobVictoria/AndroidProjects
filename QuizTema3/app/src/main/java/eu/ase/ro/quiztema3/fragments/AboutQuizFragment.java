package eu.ase.ro.quiztema3.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import eu.ase.ro.quiztema3.R;


public class AboutQuizFragment extends Fragment {

    private TextView quizInfoTextView;
    private ImageView quizImageView;
    FloatingActionButton floatingActionButton;

    public AboutQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_quiz, container, false);
        quizInfoTextView = view.findViewById(R.id.iacob_maria_victoria_quiz_info_text);
        quizImageView = view.findViewById(R.id.iacob_maria_victoria_about_quiz_image);
        quizInfoTextView.setText(R.string.quiz_info_text);
        floatingActionButton = getActivity().findViewById(R.id.iacob_maria_victoria_main_fab);
        floatingActionButton.setVisibility(View.GONE);
        return view;
    }
}